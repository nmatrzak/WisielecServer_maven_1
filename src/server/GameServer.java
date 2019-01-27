package server;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import game.Game;
import game.Player;
import game.PlayerStatus;
import utils.WordCodeDecode;

/**
 * Klasa do obslugi listy gier/Game list support class.
 *
 * @author Piotr Podgórski
 * @version 1.0
 * @since 2019-01-01
 */
@ApplicationScoped
public class GameServer implements IGameServer {

	/** lista graczy/The players. */
	private List<Player> players = new ArrayList<>();

	/** obiekt klasy random/The randomclass object */
	private Random random = new Random();

	/**
	 * stala czasu bez aktywnosci do usuniecia gry/ The Constant
	 * MINUTES_WITHOUT_ACTIVITY_TO_REMOVE_GAME.
	 */
	private final static long MINUTES_WITHOUT_ACTIVITY_TO_REMOVE_GAME = 10;

	/** serwer/The server. */
	@Inject
	private IAppServer server;

	/** kolejka gier/The games. */
	private ConcurrentLinkedQueue<Game> games = new ConcurrentLinkedQueue<>();

	/**
	 * utworz gre z przeciwnikiem/Creates the game with opponent.
	 *
	 * @param player   - graczthe player
	 * @param opponent - przeciwnik/the opponent
	 * @return gra/the game
	 */
	public Game createGame(Player player, Player opponent) {
		System.out.print("GameServer.createGame: ");
		Game game = makeGame(player, opponent, true);
		return game;
	}

	/**
	 * Tworzy gre dla gracz z przypisaniem wirtualnego gracza (komputer)/Creates the
	 * game with computer
	 *
	 * @param player - gracz/player
	 * @return Game - gra/the game
	 */
	public Game createGame(Player player) {
		System.out.print("GameServer.createGame");
		Game game = makeGame(player, Player.createComputerPlayer(), false);
		return game;
	}

	/**
	 * Tworzy gre dla pary graczy/Creates a game for a pair of players
	 *
	 * @param player     -gracz/player
	 * @param opponent   - przeciwnik/opponent
	 * @param randomRole - czy ma byc losowana rola zgadujacy lub wprowadzajacy
	 *                   slowo/should the role of guessing or introducing the word
	 *                   be drawn
	 * @return Game - gra/game
	 */
	private Game makeGame(Player player, Player opponent, boolean randomRole) {
		System.out.print("GameServer.makeGame: ");
		removeGame(player);
		removeGame(opponent);
		Game game = new Game();
		removeGame(player);
		int r = randomRole ? random.nextInt(1000) : 0;
		if (r > 500) {
			game.setWordPlayer(player);
			game.setGuessPlayer(opponent);
		} else {
			game.setWordPlayer(opponent);
			game.setGuessPlayer(player);
		}
		game.init();
		refreshGames();
		games.add(game);
		String opponentPage = game.getWordPlayer() == opponent ? "word" : "guess";
		if (server != null) {
			server.sendGoToPage(opponent, opponentPage);
		}
		listPlayers();
		return game;
	}

	/**
	 * Dodaje gracza i wysyla komunikat odswiez liste graczy/Adds a player and sends
	 * a message to refresh the list of players .
	 *
	 * @param player - gracz/player
	 */
	public void addPlayer(Player player) {
		System.out.print("GameServer.createPlayer " + player.getName());
		players.add(player);
		listPlayers();
	}

	/**
	 * Usuwa gracza i wysyla komunikat odswiez liste graczy/Removes the player and
	 * sends a message to refresh the list of players
	 *
	 * @param player - gracz/player
	 */
	public void removePlayer(Player player) {
		System.out.print("GameServer.removePlayer " + player.getName());
		players.remove(player);
		removeGame(player);
		listPlayers();
	}

	/**
	 * Realizuje akcje rozlaczenia-zakonczenia gry/He implements game disconnection
	 * and ending actions .
	 *
	 * @param player - gracz/player @return @exception
	 */
	public void playerDisconnected(Player player) {
		System.out.print("GameServer.playerDisconnected");
		Game game = findGameByPlayer(player);
		Player opponent = game.getOpponent(player);
		if (server != null) {
			server.sendMessagePlayerDisconnected(opponent);
		}
		games.remove(game);
	}

	/**
	 * Znajduje gre dla danego gracza/Finds a game for a given player.
	 *
	 * @param player - gracz (obiekt klasy Player)/player (object of the Player
	 *               class)
	 * @return znaleziony obiekt klasy Game/found Game class object
	 */
	public Game findGameByPlayer(Player player) {
		System.out.print("GameServer.findGameByPlayer - in: " + player);
		for (Game g : games) {
			if (g.playerIn(player)) {
				System.out.print("GameServer.findGameByPlayer - out: " + g);
				return g;
			}
		}
		System.out.print("GameServer.findGameByPlayer - out: GAME NOT FOUND!");
		return null;
	}

	/**
	 * Wyszukuje gracza o podanej nazwie/Find a player with the given name
	 *
	 * @param playerName - nazwa gracza/ player name
	 * @return znaleziony obiekt klasy Player/found Player class object
	 */
	public Player findPlayerByName(String playerName) {
		System.out.print("GameServer.findPlayerByName: " + playerName);
		Optional<Player> player = players.stream().filter(it -> it.getName().equalsIgnoreCase(playerName)).findFirst();
		if (player.isPresent()) {
			return player.get();
		} else {
			return null;
		}
	}

	/**
	 * Wyszukuje gracza o podanym id/Find a player with the given id
	 *
	 * @param id - id gracza/player id
	 * @return znaleziony obiekt klasy Player/found Player class object
	 */
	public Player findPlayerById(long id) {
		System.out.print("GameServer.findPlayerById: " + id);
		Optional<Player> player = players.stream().filter(it -> it.getPlayerId() == id).findFirst();
		if (player.isPresent()) {
			return player.get();
		} else {
			return null;
		}
	}

	/**
	 * Oddswieza liste gier, i wysyla sygnal do klienta (przegladarki) - wymuszenie
	 * oddswiezenia listy graczy/ Refreshes the game list, and sends a signal to the
	 * client (browser) - forcing links to the list of players
	 */
	private void listPlayers() {
		System.out.print("GameServer.listPlayers");
		refreshGames();
		if (server != null) {
			server.sendRefreshListPlayersToAll();
		}
	}

	/**
	 * Zwraca liste graczy, na potrzeby javascript, zawsze dodawani sa dwaj gracze
	 * niewidoczni, tak aby JavaScrit zawsza widzial obiekt jako liste obiektow
	 * gracz/ Returns the list of players, for the purposes of javascript, there are
	 * always added two players invisible, so that JavaScrit will always see the
	 * object as a list of player objects.
	 **
	 * @return lista graczy/list of players
	 */
	public List<Player> getPlayers() {
		System.out.print("GameServer.getPlayers");
		List<Player> pl = new ArrayList<>();
		pl.addAll(Arrays.asList(createInvisiblePlayer("test1"), createInvisiblePlayer("test2")));
		players.stream().filter(p -> !p.isComputer()).forEach(p -> pl.add(p));
		return pl;
	}

	/**
	 * Tworzy i zwraca niewidocznego gracza/Creates and returns an invisible player.
	 *
	 * @param playerName - nazwa gracza/ player name
	 * @return gracz/player
	 */
	private Player createInvisiblePlayer(String playerName) {
//		System.out.print("GameServer.createInvisiblePlayer: " + playerName);
		Player player = new Player(playerName);
		player.setStatus(PlayerStatus.INVISIBLE);
		return player;
	}

	/**
	 * Aktualizuje niepelne slowo o podana / wysylana litere. / Updates the gapped
	 * word with given / sent a letter
	 *
	 * @param player - gracz/player
	 * @param letter - wyslana litera (moze byc zakodowana w przypadku polskich
	 *               diaktrycznych liter)/sent letter (can be encoded in the case of
	 *               Polish diactric letters)
	 * @return gra - przypisana do gracza/ game-assigned to the player
	 */
	public Game updateGappedWordLetter(Player player, String letter) {
		Game game = findGameByPlayer(player);
		String decodedLetter = WordCodeDecode.decodeWordWithSpecsToPolishWord(letter);
		game.guessLetter(decodedLetter);
		if (!game.getWordPlayer().isComputer() && server != null) {
			server.sendLetter(game.getWordPlayer(), decodedLetter);
		}
		refreshGames();
		return game;
	}

	/**
	 * Wyszukuje zapisana gre dla gracza o podanej nazwie/Searches the saved game
	 * for the player with the given name
	 *
	 * @param playerName - nazwa gracza/player name
	 * @return znaleziony obiekt klasy Game/found Game class object
	 */
	public Game getGameByPlayerName(String playerName) {
		return findGameByPlayer(findPlayerByName(playerName));
	}

	/**
	 * Aktualizuje slowo do zgadnincia/Updates the word to guess .
	 *
	 * @param player - gracz/player
	 * @param word   - slowo/the word
	 * @return gra - przypisana do gracza/game-assigned to the player
	 */
	public Game updateWord(Player player, String word) {
		Game game = findGameByPlayer(player);
		game.updateWord(WordCodeDecode.decodeWordWithSpecsToPolishWord(word));
		if (server != null) {
			server.wordUpdated(game.getGuessPlayer());
		}
		refreshGames();
		return game;
	}

	/**
	 * Konczy gre dla danego gracza i wykonuje dodatkowe czynnosci - komunikat o
	 * zakonczeniu gry/End the game for a given player and performs additional
	 * actions - message about the end of the game
	 *
	 * @param player - gracz/player
	 */
	public void playerEndGame(Player player) {
		player.endGame();
		if (server != null) {
			server.sendMessageOpponentEndGame(player);
		}
	}

	/**
	 * Konczy gre i wykonuje dodatkowe czynnonci - np. wyslanie komunikatow/
	 * Finishes the game and performs additional actions - e.g. sending messages
	 *
	 * @param game - gra/the game
	 */
	public void removeGame(Game game) {
		playerEndGame(game.getGuessPlayer());
		playerEndGame(game.getWordPlayer());
		games.remove(game);
	}

	/**
	 * Usuwa gre/ Remove game
	 *
	 * @param player -gracz/player
	 */
	private void removeGame(Player player) {
		Game game = findGameByPlayer(player);
		if (game != null) {
			removeGame(game);
		}
	}

	/**
	 * Aktualizuje liste gier i zwraca ja/Updates the game list and returns it .
	 *
	 * @return - lista gier/list of games
	 */
	public List<Game> getListOfGames() {
		refreshGames();
		return games.stream().collect(Collectors.toList());
	}

	/**
	 * Aktualizuje liste gier - usuwa gry o czasie bezczynnosci dluzszym niz X
	 * sekund /. Updates the game list - deletes games with idle time longer than X
	 * seconds
	 *
	 */
	private void refreshGames() {
		try {
			List<Game> gameToRemove = games.stream().filter(this::noActivityForLongTime).collect(Collectors.toList());
			for (Game game : gameToRemove) {
				removeGame(game);
			}
		} catch (Exception e) {

		}
	}

	/**
	 * Sprawdza stan bezczynnosci gry/Checks the idle state of the game
	 *
	 * @param game - badana gra/tested game
	 * @return true-jezeli zostal przekroczony stan bezczynnosci/if idle state
	 *         exceeded return true
	 */
	private boolean noActivityForLongTime(Game game) {
		return (LocalDateTime.now().minusMinutes(MINUTES_WITHOUT_ACTIVITY_TO_REMOVE_GAME)
				.isAfter(game.getLastActivity()));
	}

}
