package server;

import java.util.List;

import game.Game;
import game.Player;

/**
 * Interfejs obslugi listy gier/Game list support interface
 * 
 * @author Piotr Podgórski
 * @version 1.0
 * @since 2019-01-01
 */

public interface IGameServer {

	/**
	 * gracz rozlaczony/ Player disconnected.
	 *
	 * @param player -gracz/the player
	 */
	void playerDisconnected(Player player);

	/**
	 * dodaj gracza/ Adds the player.
	 *
	 * @param player - gracz/the player
	 */
	void addPlayer(Player player);

	/**
	 * usun gracza/Removes the player.
	 *
	 * @param player - gracz/the player
	 */
	void removePlayer(Player player);

	/**
	 * Pobierz graczy/Gets the players.
	 *
	 * @return gracze/the players
	 */
	List<Player> getPlayers();

	/**
	 * znajdz gracza po nazwie/Find player by name.
	 *
	 * @param playerName nazwa gracza/the player name
	 * @return gracz/the player
	 */
	Player findPlayerByName(String playerName);

	/**
	 * znajdz gracza po id/Find player by id.
	 *
	 * @param palyerId id gracza/the palyer id
	 * @return gracz/the player
	 */
	Player findPlayerById(long palyerId);

	/**
	 * utworz gre z przeciwnikiem/Creates the game with opponent.
	 *
	 * @param player   - graczthe player
	 * @param opponent - przeciwnik/the opponent
	 * @return gra/the game
	 */
	Game createGame(Player player, Player opponent);

	/**
	 * utworz gre dla gracz z przypisaniem wirtualnego gracza (komputer)/Creates the game with computer
	 *
	 * @param player - gracz/the player
	 * @return gra/the game
	 */
	Game createGame(Player player);

	/**
	 * zaktualizuj niepelne slowo/Update gapped word letter.
	 *
	 * @param player - gracz/the player
	 * @param letter - litera/the letter
	 * @return gra/the game
	 */
	Game updateGappedWordLetter(Player player, String letter);

	/**
	 * pobiera gre po nazwie gracza/Gets the game by player name.
	 *
	 * @param playerName - nazwa gracza/the player name
	 * @return gra po nazwie gracza/the game by player name
	 */
	Game getGameByPlayerName(String playerName);

	/**
	 * znajdz gre wedlug gracza/Find game by player.
	 *
	 * @param player gracz/the player
	 * @return gra/the game
	 */
	Game findGameByPlayer(Player player);

	/**
	 * uaktualnij slowo/Update word.
	 *
	 * @param player - gracz/the player
	 * @param word   - slowo/the word
	 * @return gra/the game
	 */
	Game updateWord(Player player, String word);

	/**
	 * pobierz liste gier/Gets the list of games.
	 *
	 * @return lista gier/the list of games
	 */
	List<Game> getListOfGames();

}
