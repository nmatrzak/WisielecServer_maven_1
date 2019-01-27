package server;

/**
 * klasa wyliczeniowa komend wiersza polecen/The Enum Command.
 * 
 * @author Piotr Podgórski
 * @version 1.0
 * @since 2019-01-01
 */

public enum Command {

	/** komenda hello/The cmd hello. */
	CMD_HELLO("hello"),
	/** komenda litera/The cmd letter. */
	CMD_LETTER("letter"),
	/** The cmd disconnected. */
	CMD_DISCONNECTED("disconnected"),
	/** komenda zkonczenia gry przez przeciwnika/The cmd opponent end game. */
	CMD_OPPONENT_END_GAME("opponnent_end_game"),
	/** komenda odswiez graczy/The cmd refersh players. */
	CMD_REFERSH_PLAYERS("refresh_player_list"),
	/** komenda odswiez slowo/The cmd word updated. */
	CMD_WORD_UPDATED("word_updated"),
	/** komenda idz do strony zgadywania/The cmd goto page guess. */
	CMD_GOTO_PAGE_GUESS("goto_guess"),
	/** komenda idz do strony/The cmd goto page. */
	CMD_GOTO_PAGE("goto"),
	/** komenda echo/The cmd echo. */
	CMD_ECHO("echo"),
	/** komenda nieznana/The cmd unknown. */
	CMD_UNKNOWN("");

	/** komenda wiersza polecen/The cmd. */
	String cmd;

	/**
	 * konstruktor/Instantiates a new command.
	 *
	 * @param cmd - komenda/the cmd
	 */
	Command(String cmd) {
		this.cmd = cmd;
	}

	/*
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return cmd;
	}

	/**
	 * decyzja/resolve.
	 *
	 * @param cmd -komenda/the cmd
	 * @return komenda/the command
	 */
	public static Command resolve(String cmd) {
		for (Command c : Command.values()) {
			if (c.cmd.equals(cmd)) {
				return c;
			}
		}
		return CMD_UNKNOWN;
	}

}
