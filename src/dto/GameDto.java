package dto;

import java.io.Serializable;

import game.Game;
import utils.WordCodeDecode;

/**
 * Obiekt klasy GameDto sluży do przenoszenie danych gry pomiedzy serwisami
 * (client server) Klasa implementuje interface Serializable (nie zawiera on
 * żadnych metod)./ The object of GameDto class carries game data between
 * processes (client and server) The class implements a tag interface
 * Serializable.This interface dosn't contain any method.
 * 
 * @author Norbert Matrzak
 * @version 1.0
 * @since 2019-01-01
 */
public class GameDto implements Serializable {

	/** stala/The constant - serialVersionUID. */
	private static final long serialVersionUID = 75264711556227767L;

	/** slowo/The theWord */
	private String theWord;

	/** niepelne slowo/The gapped word. */
	private String gappedWord;

	/** liczba blednych odpowiedzi/The count missed. */
	private int countMissed = 0;

	/** uzyte litery/The used letters. */
	private String usedLetters;

	/** status gry/The game status. */
	private String gameStatus;

	/** zwyciezca/The winner. */
	private String winner;

	/** nazwa gracza tworzacego slowo/The player word name. */
	private String playerWordName;

	/** nazwa gracza zgadujacego/The player guess Name. */
	private String playerGuessName;

	/**
	 * Konstruktor klasy GameDto/ Instantiates a new GameDto.
	 *
	 * @param theWord         - slowo/the word
	 * @param gappedWord      - niepelne slowo/the gap in word
	 * @param countMissed     - liczba blednych odpowiedzi /the count missed
	 * @param usedLetters     - uzyte litery/the used letters
	 * @param gameStatus      - status gry/the game status
	 * @param winner          - zwyciezca/the winner
	 * @param playerWordName  - nazwa gracza tworzacego slowo/the player word name
	 * @param playerGuessName - nazwa gracza zgadujaceg/the player guess name
	 */
	public GameDto(String theWord, String gappedWord, int countMissed, String usedLetters, String gameStatus,
			String winner, String playerWordName, String playerGuessName) {
		this.theWord = theWord;
		this.gappedWord = gappedWord;
		this.countMissed = countMissed;
		this.usedLetters = usedLetters;
		this.gameStatus = gameStatus;
		this.winner = winner;
		this.playerWordName = playerWordName;
		this.playerGuessName = playerGuessName;
	}

	/**
	 * Pobiera slowo/Gets the word.
	 *
	 * @return slowo/the word
	 */
	public String getTheWord() {
		return theWord;
	}

	/**
	 * Ustawia slowo/Sets the word.
	 *
	 * @param theWord - nowe slowo/the new word
	 */
	public void setTheWord(String theWord) {
		this.theWord = theWord;
	}

	/**
	 * Pobiera roznice w slowie zgadywanym/Gets the gap in word.
	 *
	 * @return niepelne slowo/gapped word
	 */
	public String getGappedWord() {
		return gappedWord;
	}

	/**
	 * Ustawia roznice w stosunku do slowa zgadywanego/Sets the gap in word.
	 *
	 * @param gappedWord - nowa roznica w slowie zgadywanym/the new gap in guess
	 *                   word
	 */
	public void setGappedWord(String gappedWord) {
		this.gappedWord = gappedWord;
	}

	/**
	 * Pobiera ilosc blednych prob/Gets the count missed.
	 *
	 * @return liczba bledow/countMissed
	 */
	public int getCountMissed() {
		return countMissed;
	}

	/**
	 * Ustawia ilosc blednych prob/Sets the count missed.
	 *
	 * @param countMissed - nowa liczba prob/the new count missed
	 */
	public void setCountMissed(int countMissed) {
		this.countMissed = countMissed;
	}

	/**
	 * Pobiera uzyte litery/ Gets the used letters.
	 *
	 * @return uzyte litery/the used letters
	 */
	public String getUsedLetters() {
		return usedLetters;
	}

	/**
	 * Ustawia uzyte litery/Sets the used letters.
	 *
	 * @param usedLetters - nowy ciag liter/the new used letters
	 */
	public void setUsedLetters(String usedLetters) {
		this.usedLetters = usedLetters;
	}

	/**
	 * Pobiera stautus gry/Gets the game status.
	 *
	 * @return status gry/game status
	 */
	public String getGameStatus() {
		return gameStatus;
	}

	/**
	 * Ustawia status gry/Sets the game status.
	 *
	 * @param gameStatus - nowy status gry/the new game status
	 */
	public void setGameStatus(String gameStatus) {
		this.gameStatus = gameStatus;
	}

	/**
	 * Pobiera nazwe zwyciezcy/Gets the winner.
	 *
	 * @return zwyciezca/winner
	 */
	public String getWinner() {
		return winner;
	}

	/**
	 * Ustawia imie zwyciezcy/Sets the winner.
	 *
	 * @param winner - nowa nazwa zwyciezcy/the new winner
	 */
	public void setWinner(String winner) {
		this.winner = winner;
	}

	/**
	 * Pobiera imie gracza wprowadzajacego wyraz do zgadniecia/Gets the player name
	 * who write the word
	 *
	 * @return grcz wprowadzajacy slowo/player insert word name
	 */
	public String getPlayer1Name() {
		return playerWordName;
	}

	/**
	 * Ustawia imie gracza wprowadzajacego wyraz do zgadniecia/ Sets the player name
	 * who write the word
	 *
	 * @param player1Name - imie gracza/the new player one name
	 */
	public void setPlayer1Name(String player1Name) {
		this.playerWordName = player1Name;
	}

	/**
	 * Pobiera imie gracza zgadujacego/Gets the player name who guess the word
	 *
	 * @return nazwa gracza zgadujacego slowo/name of player guess word
	 */
	public String getPlayer2Name() {
		return playerGuessName;
	}

	/**
	 * Ustawia imie gracza zgadujacego/Sets the player name who guess the word
	 *
	 * @param player2Name - imie gracza zgadujacego/the new player two name
	 */
	public void setPlayer2Name(String player2Name) {
		this.playerGuessName = player2Name;
	}

	/**
	 * Metoda statyczna odpowiedzialny za tworzenie instancji klasy GameDto po
	 * sprawdzeniu braku wartosci null. This static method is used to check status
	 * of parameter game and get instantiates of GameDto or null.
	 * 
	 * @param game - gra/the game
	 * @return GameDto
	 */
	public static GameDto of(Game game) {
		if (game != null) {
			return new GameDto(WordCodeDecode.codePolishWordToWordWithSpecs(game.getTheWord()),
					WordCodeDecode.codePolishWordToWordWithSpecs(game.getGappedWord()), game.getCountMissed(),
					game.getUsedLetters(), game.getGameStatus().name(),
					WordCodeDecode.codePolishWordToWordWithSpecs(game.getWinnerName()),
					WordCodeDecode.codePolishWordToWordWithSpecs(game.getWordPlayer().getName()),
					WordCodeDecode.codePolishWordToWordWithSpecs(game.getGuessPlayer().getName()));
		} else
			return null;

	}

	/*
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("player word: %s, player guess, %s, state: %s, word: %s", playerWordName, playerGuessName,
				gameStatus, theWord);
	}

}
