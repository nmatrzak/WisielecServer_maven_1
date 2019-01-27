package server;

import game.Player;

/**
 * Interfejs Servera aplikacji/The Interface App Server.
 * 
 * @author Norbert Matrzak
 * @version 1.0
 * @since 2019-01-01
 */
public interface IAppServer {

	/**
	 * usuwa gracza po nr Id/Removes the player by id.
	 *
	 * @param playerId - nr id gracza/the player id
	 */
	void removePlayerById(long playerId);

	/**
	 * wysyla przeciwnikowi informacje o rozlaczeniu/Send message player
	 * disconnected.
	 *
	 * @param opponent - przeciwnik/the opponent
	 */
	void sendMessagePlayerDisconnected(Player opponent);

	/**
	 * wysyla przeciwnikowi informacje o koncu gry/Send message opponent end game.
	 *
	 * @param opponent - przeciwnik/the opponent
	 */
	void sendMessageOpponentEndGame(Player opponent);

	/**
	 * wysli info przejdz do strony/Send go to page.
	 *
	 * @param opponent - przeciwnik/the opponent
	 * @param page     - strona/the page
	 */
	void sendGoToPage(Player opponent, String page);

	/**
	 * uaktualnienie slowa/Word updated.
	 *
	 * @param playerName - nazwa gracza/the player name
	 */
	void wordUpdated(Player playerName);

	/**
	 * przeslij litere/Send letter.
	 *
	 * @param playerNamer - nazwa gracza/the player namer
	 * @param letter      - litera/the letter
	 */
	void sendLetter(Player playerNamer, String letter);

	/**
	 * Wyslij odswiezona liste graczy do wszystkich/Send refresh list players to
	 * all.
	 */
	void sendRefreshListPlayersToAll();

}
