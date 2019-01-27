package exception;

/**
 * Klasa wyjatku braku nazwy gracza/The Class empty name For player Exception.
 * 
 * @author Norbert Matrzak
 * @version 1.0
 * @since 2019-01-01
 */
public class EmptyNameForPlayerException extends RuntimeException {

	/** stala/The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Konstruktor klasy EmptyNameForPlayerException/Instantiates a new empty name
	 * for player exception.
	 */
	public EmptyNameForPlayerException() {
		super("Empty name for player!");
	}

}
