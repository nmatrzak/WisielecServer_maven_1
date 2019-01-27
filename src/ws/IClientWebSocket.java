package ws;

/**
 * Interfejs ClientWebsocket/The Interface IClientWebSocket.
 * 
 * @author Norbert Matrzak
 * @version 1.0
 * @since 2019-01-01
 */
public interface IClientWebSocket {

	/**
	 * wyslij do wszystkich/Send to all.
	 *
	 * @param msg - wiadomosc/the msg
	 */
	void sendToAll(String msg);

	/**
	 * wyslij do jednego gracz/aSend to player.
	 *
	 * @param playerId - id gracza/the player id
	 * @param msg      - wiadomosc/the msg
	 */
	void sendToPlayer(long playerId, String msg);

}
