package server;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import game.Player;
import ws.IClientWebSocket;

/**
 * Klasa serwera aplikacji/The Class AppServer.
 * 
 * @author Norbert Matrzak
 * @version 1.0
 * @since 2019-01-01
 */
@ApplicationScoped
public class AppServer implements IAppServer {

	/** stala separatora wiersza polecen/The Constant CMD_SEP. */
	private static final String CMD_SEP = "#";

	/** klient ws./The client ws. */
	@Inject
	private IClientWebSocket clientWs;

	/** server gry/The game server. */
	@Inject
	private IGameServer gameServer;

	/**
	 * wyslij wiadomosc do klienta/Send message to client.
	 *
	 * @param player    - gracz/the player
	 * @param operation - operacja/the operation
	 * @param data      - data/the data
	 */
	public void sendMessageToClient(Player player, String operation, String data) {
		if (player == null) {
			System.out.println("sendMessageToClient - Player NULL!");
		}
		System.out.println("sendMessageToClient " + player.getName() + " > " + operation + " # " + data);
		if (player.isComputer()) {
			System.out.println("Players is the computer (virtual player), messege has not been sent");
			return;
		}
		clientWs.sendToPlayer(player.getPlayerId(), operation + CMD_SEP + data);
	}

	/**
	 * wyslij wiadomosc do klienta/Send message to client.
	 *
	 * @param player    - gracz/the player
	 * @param operation - operacja/the operation
	 */
	public void sendMessageToClient(Player player, String operation) {
		if (player == null) {
			System.out.println("sendMessageToClient - Player NULL!");
		}
		System.out.println("sendMessageToClient " + player.getName() + " > " + operation);
		if (player.isComputer()) {
			System.out.println("Players is the computer (virtual player), messege has not been sent");
			return;
		}
		clientWs.sendToPlayer(player.getPlayerId(), operation);
	}

	/*
	 * @see server.IAppServer#sendLetter(game.Player, java.lang.String)
	 */
	public void sendLetter(Player toPlayer, String letter) {
		sendMessageToClient(toPlayer, Command.CMD_LETTER.toString());
	}

	/*
	 * @see server.IAppServer#sendMessagePlayerDisconnected(game.Player)
	 */
	public void sendMessagePlayerDisconnected(Player toPlayer) {
		sendMessageToClient(toPlayer, Command.CMD_DISCONNECTED.toString(), "");
	}

	/*
	 * @see server.IAppServer#sendMessageOpponentEndGame(game.Player)
	 */
	public void sendMessageOpponentEndGame(Player toPlayer) {
		sendMessageToClient(toPlayer, Command.CMD_OPPONENT_END_GAME.toString());
	}

	/*
	 * @see server.IAppServer#sendGoToPage(game.Player, java.lang.String)
	 */
	public void sendGoToPage(Player toPlayer, String page) {
		sendMessageToClient(toPlayer, Command.CMD_GOTO_PAGE.toString() + "_" + page);
	}

	/*
	 * @see server.IAppServer#wordUpdated(game.Player)
	 */
	public void wordUpdated(Player toPlayer) {
		sendMessageToClient(toPlayer, Command.CMD_WORD_UPDATED.toString());
	}

	/*
	 * @see server.IAppServer#sendRefreshListPlayersToAll()
	 */
	public void sendRefreshListPlayersToAll() {
		clientWs.sendToAll(Command.CMD_REFERSH_PLAYERS.toString());
	}

	/*
	 * @see server.IAppServer#removePlayerById(long)
	 */
	public void removePlayerById(long playerId) {
		gameServer.removePlayer(gameServer.findPlayerById(playerId));
		sendRefreshListPlayersToAll();
	}

}
