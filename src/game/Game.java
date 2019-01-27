package game;

import java.time.LocalDateTime;
import java.util.Optional;

import utils.WordGenerator;

/**
 * Klasa gry/The Class Game.
 * 
 * @author Piotr Podgórski
 * @version 1.0
 * @since 2019-01-01
 */

public class Game {

	/** stala ilosci bledow/The Constant MAX_MISSED_LETTERS. */
	private static final int MAX_MISSED_LETTERS = 8;

	/** stala generator slowa/The Constant wordGenerator. */
	private static final WordGenerator wordGenerator = new WordGenerator();

	/** id gry/The game id. */
	private long gameId = 0;

	/** nr kolejnej rundy gry/The seq game id. */
	private static long seqGameId = 0;

	/** gracze/The players. */
	private Player[] players = new Player[2];

	/** zwyciezca/The winner. */
	private Player theWinner = null;

	/** gracz podajacy slowo/The word player. */
	private int wordPlayer = 0;

	/** zgadywane slowo/The word. */
	private String theWord;

	/** liczba unikalnych liter/The count unique letters. */
	private long countUniqueLetters = 0;

	/** liczba blednych prob/ The count missed. */
	private int countMissed = 0;

	/** uzyte litery/The used letters. */
	private String usedLetters;

	/** maksymalna liczba blednych liter/ The max missed letters. */
	private int maxMissedLetters;

	/** status gry/The game status. */
	private GameStatus gameStatus = GameStatus.CREATED;

	/** Data ostatniej aktywnosci/The last activity. */
	private LocalDateTime lastActivity;

	/**
	 * Konstruktor klasy Game/Instantiates a new game.
	 */
	public Game() {
		gameId = ++seqGameId;
		updateLastActivity();
	}

	/**
	 * uaktualnienie daty aktywnosci/Update last activity.
	 */
	private void updateLastActivity() {
		lastActivity = LocalDateTime.now();
		if (players[0] != null) {
			players[0].updateLastActivity();
		}
		if (players[1] != null) {
			players[1].updateLastActivity();
		}
	}

	/**
	 * Pobranie slowa/Gets the theword.
	 *
	 * @return slowo/the theword
	 */
	public String getTheWord() {
		return theWord;
	}

	/**
	 * Pobranie liczby blednych prob/Gets the count missed.
	 *
	 * @return liczba blednych prob/the count missed
	 */
	public int getCountMissed() {
		return countMissed;
	}

	/**
	 * Pobranie uzytych liter/Gets the used letters.
	 *
	 * @return uzyte litery/the used letters
	 */
	public String getUsedLetters() {
		return usedLetters;
	}

	/**
	 * Pobranie statusu gry/Gets the game status.
	 *
	 * @return status gry/the game status
	 */
	public GameStatus getGameStatus() {
		return gameStatus;
	}

	/**
	 * Ustawienie gracza podajacego slowo/Sets the word player.
	 *
	 * @param gracz wpisujacy slowo/player set new word
	 */
	public void setWordPlayer(Player player) {
		players[wordPlayer] = player;
	}

	/**
	 * ustawienie gracza zgadujacego/Sets the guess player.
	 *
	 * @param gracz zgadujacy slowo/ new guess player
	 */
	public void setGuessPlayer(Player player) {
		players[1 - wordPlayer] = player;
	}

	/**
	 * Pobranie gracza wpisujacego slowo/Gets the word player.
	 *
	 * @return gracz wpisujacy slowo/the word player
	 */
	public Player getWordPlayer() {
		return players[wordPlayer];
	}

	/**
	 * Pobranie gracza zgadujacego/Gets the guess player.
	 *
	 * @return gracz zgadujacy/the guess player
	 */
	public Player getGuessPlayer() {
		return players[1 - wordPlayer];
	}

	/**
	 * Pobranie zwyciezcy/Gets the winner.
	 *
	 * @return zwyciezca/the winner
	 */
	public Player getWinner() {
		return theWinner;
	}

	/**
	 * Pobranie nazwy zwyciezcy/Gets the winner name.
	 *
	 * @return nazwa zwyciezcy/the winner name
	 */
	public String getWinnerName() {
		return Optional.ofNullable(theWinner).orElse(new Player("")).getName();
	}

	/**
	 * Pobranie nr id grt/Gets the game id.
	 *
	 * @return numer id gry/the game id
	 */
	public long getGameId() {
		return gameId;
	}

	/**
	 * Pobranie daty ostatniej aktywnosci/Gets the last activity.
	 *
	 * @return ostatnia aktywnosc/the last activity
	 */
	public LocalDateTime getLastActivity() {
		return lastActivity;
	}

	/**
	 * Zainicjowanie gry/Inits the game.
	 */
	public void init() {
		System.out.println("Server.Game.init: ");
		theWord = "";
		theWinner = null;
		countMissed = 0;
		usedLetters = "";
		if (!getWordPlayer().isComputer()) {
			gameStatus = GameStatus.WAIT_FOR_WORD;
		} else {
			updateWord(wordGenerator.getNewWord());
		}

		getWordPlayer().setStatus(PlayerStatus.PLAYING);
		getGuessPlayer().setStatus(PlayerStatus.PLAYING);
		updateLastActivity();
	}

	/**
	 * uaktualnienie wyrazu/Update word.
	 *
	 * @param theWord slowo/the theword
	 */
	public void updateWord(String theWord) {
		System.out.println("Server.Game.updateWord: " + theWord);
		this.theWord = theWord.toUpperCase();
		this.gameStatus = GameStatus.PLAY;
		initCounters();
		updateLastActivity();
	}

	/**
	 * zainicjowanie licznika bledow/Inits the counters of missed.
	 */
	private void initCounters() {
		maxMissedLetters = MAX_MISSED_LETTERS;
		countUniqueLetters = countUniqueCharacters(theWord);
	}

	/**
	 * policz unikalne znaki/Count unique characters.
	 *
	 * @param input wpisane slowo/the input
	 * @return liczba/number
	 */
	public long countUniqueCharacters(String input) {
		return input.chars().distinct().count();
	}

	/**
	 * odgadnieta litera/Letter hit.
	 *
	 * @param letter litera/the letter
	 * @return jesli odgadnieta wartosc true/true, if successful
	 */
	public boolean letterHit(char letter) {
		return theWord.chars().anyMatch(c -> letter == c);
	}

	/**
	 * Pobiera niepelne slowo/Gets the gapped word.
	 *
	 * @return niepelne slowo/the gapped word
	 */
	public String getGappedWord() {
		if (theWord == null || theWord.isEmpty()) {
			return "";
		}
		StringBuilder gappedWord = new StringBuilder();
		for (int i = 0; i < theWord.length(); i++) {
			char wordChar = theWord.charAt(i);
			if (letterHasBeenUsed(wordChar)) {
				gappedWord.append(String.valueOf(wordChar));
			} else {
				gappedWord.append('_');
			}
		}
		return gappedWord.toString();
	}

	/**
	 * odgadniete slowo/Word guessed.
	 *
	 * @return jesli odgadniete, wartosc true/true, if successful
	 */
	public boolean wordGuessed() {
		return getGappedWord().equals(theWord);
	}

	/**
	 * osiagniecie maksymalnej liczby bledow/Misses reach maximum.
	 *
	 * @return jesli osiagnieta maksymalna warosc, zwraca true/true, if successful
	 */
	public boolean missesReachMaximum() {
		return countMissed == maxMissedLetters;
	}

	/**
	 * uzyto litery/Letter has been used.
	 *
	 * @param letter - litera/the letter
	 * @return jesli uzyta, zwraca true/true, if successful
	 */
	public boolean letterHasBeenUsed(char letter) {
		return usedLetters.chars().anyMatch(c -> c == letter);
	}

	/**
	 * uzyj litery/Try letter.
	 *
	 * @param letter - litera/ the letter
	 */
	public void tryLetter(char letter) {
		if (!letterHasBeenUsed(letter)) {
			usedLetters = new StringBuilder().append(usedLetters).append(letter).toString();
			if (letterHit(letter)) {
				if (wordGuessed()) {
					Player wordPlayer = getWordPlayer();
					Player guessPlayer = getGuessPlayer();
					guessPlayer.addPoints(countUniqueLetters);
					guessPlayer.incWin();
					getWordPlayer().incLost();
					theWinner = guessPlayer;
					guessPlayer.endGame();
					wordPlayer.endGame();
					gameStatus = GameStatus.END;
				}
			} else {
				countMissed++;
				if (missesReachMaximum()) {
					Player wordPlayer = getWordPlayer();
					Player guessPlayer = getGuessPlayer();
					wordPlayer.addPoints(countUniqueLetters);
					getGuessPlayer().incLost();
					wordPlayer.incWin();
					theWinner = wordPlayer;
					wordPlayer.endGame();
					guessPlayer.endGame();
					gameStatus = GameStatus.END;
				}
			}
		}
	}

	/**
	 * zakon gre przed czasem przez gracza/End game before because of player.
	 *
	 * @param player -gracz/the player
	 */
	public void endGameBeforeBecouseOfPlayer(Player player) {
		Player guessPlayer = getGuessPlayer();
		Player wordPlayer = getWordPlayer();
		if (gameStatus == GameStatus.PLAY) {
			if (player == guessPlayer) {
				wordPlayer.addPoints(1);
			} else {
				guessPlayer.addPoints(countUniqueLetters);
			}
		}
		guessPlayer.endGame();
		wordPlayer.endGame();
		gameStatus = GameStatus.END;
	}

	/**
	 * zgaduj litere/Guess letter.
	 *
	 * @param letter - litera/the letter
	 * @return gre/the game
	 */
	public Game guessLetter(String letter) {
		updateLastActivity();
		if (!letter.isEmpty()) {
			tryLetter(letter.charAt(0));
		}
		return this;
	}

	/**
	 * czy jest gracz/is player in.
	 *
	 * @param player - gracz/the player
	 * @return jesli jest zwraca wartosc true/true, if successful
	 */
	public boolean playerIn(Player player) {
		return players[0] == player || players[1] == player;
	}

	/**
	 * Pobierz przeciwnika/Gets the opponent.
	 *
	 * @param player -gracz/the player
	 * @return przeciwnika/the opponent
	 */
	public Player getOpponent(Player player) {
		if (players[0] == player) {
			return players[1];
		} else if (players[1] == player) {
			return players[0];
		} else {
			return null;
		}
	}

	/**
	 * czy grac z komputerem/is game with computer.
	 *
	 * @return jesli tak zwraca wartosc true/true, if successful
	 */
	public boolean withComputer() {
		return getWordPlayer().isComputer();
	}

	/*
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("Game: wordPlayerName=%s, guessPlayerName=%s", getWordPlayer(), getGuessPlayer());
	}
}
