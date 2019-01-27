package exception;

/**
 * Klasa wyjatku nieznanej nazwy gracza/The Class unknown name player Exception.
 * 
 * @author Norbert Matrzak
 * @version 1.0
 * @since 2019-01-01
 */
public class UnknownPlayerException extends RuntimeException {

	/** stala/The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Konstruktor klasy UnknownPlayerException/ Instantiates a new
	 * UnknownPlayerException.
	 *
	 * @param name - nazwa/the name
	 */
	public UnknownPlayerException(String name) {
		super("Unknown player with name: " + name);
	}

}
