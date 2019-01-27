package ws;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import server.IAppServer;

/**
 * klasa zapewniajaca komunikacje servera z klientem/this class provide
 * communication between the server and the clien
 * 
 * @author Norbert Matrzak
 * @version 1.0
 * @since 2019-01-01
 */
//zasięg aplikacji/application range
@ApplicationScoped
//zapewnia dostępność klasy jako serwera WebSocket, słuchającego określonej URI/provide that the class is available as a WebSocket server listening to a specific URI
@ServerEndpoint("/play")
public class ClientWebSocket implements IClientWebSocket {

	/** stala/The Constant OPERATION_HELLO. */
	private static final String OPERATION_HELLO = "hello";

	/** stala/The Constant OPERATION_BYEBYE. */
	private static final String OPERATION_BYEBYE = "byebye";

	/** server/The server. */
	// wstrzykniecie interfejsu okreslajacego dostep do servera aplikacji/inject
	// interface for app server access
	@Inject
	private IAppServer server;

	/**
	 * Konstruktor klasy ClientWebSocket/Instantiates a new ClientWebSocket.
	 */
	public ClientWebSocket() {
		System.out.println("ClientWebSocket created");
	}

	/** kolejka sesji/ Queue of sessions. */
	private static ConcurrentLinkedQueue<Session> peers = new ConcurrentLinkedQueue<>();

	/** kolekcja sesji graczy/ The collection of players sessions. */
	private static ConcurrentHashMap<String, Session> playersSessions = new ConcurrentHashMap<>();

	/**
	 * dodaje nowe polaczenie/ add new connection
	 *
	 * @param session -sesja/the session
	 */
	// Metoda Java z @OnOpen jest wywoływana przez kontener po zainicjowaniu nowego
	// połączenia WebSocket/
	// A Java method with @OnOpen is invoked by the container when a new WebSocket
	// connection is initiated
	@OnOpen
	public void onOpen(Session session) {
		System.out.println("WS:onOpen::" + session.getId());
		peers.add(session);
	}

	/**
	 * odbiera informacje o wysylaniu wiadomosci do endpoint'u/receives information
	 * about sending messages to endpoint
	 *
	 * @param message - wiadomosc/the message
	 * @param session - sesja/the session
	 */
	// Metoda Java, z adnotacją @OnMessage, odbiera informacje z kontenera
	// WebSocket, gdy wiadomość jest wysyłana do punktu końcowego//
	// A Java method, annotated with @OnMessage, receives the information from the
	// WebSocket container when a message is sent to the endpoint
	@OnMessage
	public void onMessage(String message, Session session) {
		System.out.println("WS:onMessage:" + message);
		if (server == null) {
			System.out.println("Server is NULL");
		} else {
			System.out.println("WS:Forwarding message to app server");

			if (isMessageByeBye(message)) {
				removePlayerSession(message, session);
			} else if (isMessageHello(message)) {
				updatePlayerSession(message, session);
			}
//			else {
//				String playerName = getPlayerIdBySession(session);
//				if (!playerName.isEmpty() ) {
//				   server.messageReceived(playerName, message);
//				}
//			}
		}
	}

	/**
	 * informuje o problemie z komunikacja/inform about problem with communictaion
	 *
	 * @param t - the t
	 */
	// Metoda z @OnError jest wywoływana, gdy występuje problem z komunikacją
	// A method with @OnError is invoked when there is a problem with the
	// communication
	@OnError
	public void onError(Throwable t) {
		System.out.println("WS:onError::" + t.getMessage());
	}

	/**
	 * sprawdzenie tekstu wiadomosci/Checks text message
	 *
	 * @param message - wiadomosc/the message
	 * @return jesli tekst wiadomosci to hello, zwraca true/true, if is message
	 *         hello
	 */
	private boolean isMessageHello(String message) {
		return OPERATION_HELLO.equals(getOperationFromMessage(message));
	}

	/**
	 * sprawdzenie tekstu wiadomosci/Checks text message
	 *
	 * @param message the message
	 * @return jesli tekst wiadomosci to byebye, zwraca true/true, if is message
	 *         byebye
	 */
	private boolean isMessageByeBye(String message) {
		return OPERATION_BYEBYE.equals(getOperationFromMessage(message));
	}

	/**
	 * Zaktualizuj sesję gracza/Update player session.
	 *
	 * @param message - wiadomosc/the message
	 * @param session - sesja/the session
	 */
	private void updatePlayerSession(String message, Session session) {
		System.out.println("WS:updatePlayerSession " + message + " > session: " + session.getId());
		String playerId = getDataFromMessage(message);
		playersSessions.put(playerId, session);
		synchronizeSessionPlayers();
	}

	/**
	 * informuje o zamknieciu polaczenia/inform about closes connection
	 *
	 * @param session - sesja/the session
	 */
	// adnotacja przed metoda, która jest wywoływana po zamknięciu połączenia
	// WebSocket
	// annotation used before method that is called when the WebSocket connection
	// closes
	@OnClose

	public void onClose(Session session) {
		System.out.println("WS:onClose::" + session.getId());
		String playerId = getPlayerIdBySession(session);
		playersSessions.remove(playerId);
		peers.remove(session);
	}

	/**
	 * Usuwa sesję gracza/Removes the player session.
	 *
	 * @param message - wiadomosc/the message
	 * @param session - sesja/the session
	 */
	private void removePlayerSession(String message, Session session) {
		System.out.println("removePlayerSession " + message + " > session: " + session.getId());
		String playerId = getDataFromMessage(message);
		playersSessions.remove(playerId);
		peers.remove(session);
		synchronizeSessionPlayers();
		server.removePlayerById(Long.valueOf(playerId));
	}

	/**
	 * Pobiera tresc operację z wiadomości/ Gets the operation from message.
	 *
	 * @param message - wiadomosc/the message
	 * @return tresc operacji z wiadomosci/the operation from message
	 */
	private String getOperationFromMessage(String message) {
		String[] msgItems = message.split("#");
		if (msgItems.length > 0) {
			return msgItems[0];
		} else {
			return "";
		}
	}

	/**
	 * Pobiera dane z tresci wiadomosci/ Gets the data from message.
	 *
	 * @param message - wiadomosc/the message
	 * @return dane z tresci wiadomosci/the data from message
	 */
	private String getDataFromMessage(String message) {
		String[] msgItems = message.split("#");
		if (msgItems.length > 1) {
			return msgItems[1];
		} else {
			return "";
		}
	}

	/**
	 * Synchronizuj sesje graczy/Synchronize session players.
	 */
	private void synchronizeSessionPlayers() {
		System.out.println("WS:synchronizeSessionPlayers ");
		List<String> playerSessionsToRemove = new ArrayList<>();
		for (Map.Entry<String, Session> entry : playersSessions.entrySet()) {
			if (peerNotExists(entry.getValue())) {
				playerSessionsToRemove.add(entry.getKey());
			}
		}
		playerSessionsToRemove.forEach(playerId -> playersSessions.remove(playerId));
	}

	/**
	 * sprawdza czy kolejka sesji nie istnieje/ check is queue session not exists.
	 *
	 * @param session -sesja/the session
	 * @return jesli nie istniej zwraca true/true, if successful
	 */
	private boolean peerNotExists(Session session) {
		return !peers.stream().anyMatch(peer -> peer.getId().equals(session.getId()));
	}

	/**
	 * wysylanie wiadomosci/Send.
	 *
	 * @param session - sesja/the session
	 * @param msg     - wiadomosc/the msg
	 */
	private void send(Session session, String msg) {
		System.out.println("WS:send (toSession)::" + session.getId() + " > " + msg);
		try {
			session.getBasicRemote().sendText(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * @see ws.IClientWebSocket#sendToPlayer(long, java.lang.String)
	 */
	public void sendToPlayer(long playerId, String msg) {
		System.out.println("WS:sendToPlayer::" + playerId + " > " + msg);
		Session session = getSessionByPlayerId(String.valueOf(playerId));
		if (session != null) {
			send(session, msg);
		}
	}

	/*
	 * @see ws.IClientWebSocket#sendToAll(java.lang.String)
	 */
	public void sendToAll(String msg) {
		System.out.println("WS:sendToAll::" + msg);
		peers.forEach(session -> {
			send(session, msg);
		});
	}

	/**
	 * Pobiera sesje według identyfikatora gracza/Gets the session by player id.
	 *
	 * @param playerId - id gracza/the player id
	 * @return sesje po nr Id gracza/the session by player id
	 */
	private Session getSessionByPlayerId(String playerId) {
		return playersSessions.get(playerId);
	}

	/**
	 * Pobiera id gracza wedug sesji/Gets the player id by session.
	 *
	 * @param session - sesjathe session
	 * @return id gracza wedug sesji/the player id by session
	 */
	private String getPlayerIdBySession(Session session) {
		System.out.println("WS:getPlayerNameBySession::" + session.getId());
		if (session != null) {
			for (Map.Entry<String, Session> entry : playersSessions.entrySet()) {
				if (entry.getValue().getId().equals(session.getId())) {
					return entry.getKey();
				}
			}
		}
		return new String("");
	}

}
