package game;

/**
 * klasa wyliczeniowa stanow gry/ The Enum of game status.
 * 
 * @author Piotr Podgórski
 * @version 1.0
 * @since 2019-01-01
 */
public enum GameStatus {

	/** stworzona/The created. */
	CREATED,
	/** oczekuje na podanie wyrazu/The wait for word. */
	WAIT_FOR_WORD,
	/** gra/ The play. */
	PLAY,
	/** koniec/The end. */
	END;

}
