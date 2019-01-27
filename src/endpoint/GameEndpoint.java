
package endpoint;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import dto.GameDto;
import game.Game;
import server.IGameServer;
import service.IPlayerService;

/**
 * Punkt dostępowy do webservice /Access point to webservice
 * 
 * @author Piotr Podgórski
 * @version 1.0
 * @since 2019-01-01
 */

//zasięg aplikacji/application range
@ApplicationScoped
@Path("/game")
public class GameEndpoint {

	/** The player service. */
	// wstrzykniecie interfejsu okreslajacego dostep do gry/inject interface for
	// game access
	@Inject
	private IPlayerService playerService;

	/** The game server. */
	// wstrzykniecie interfejsu okreslajacego dostep do listy gier/inject interface
	// for games list access
	@Inject
	private IGameServer gameServer;

	/** The gson. */
	private Gson gson;

	/**
	 * konstruktor klasy GameEndpont/Instantiates a new GameEndpoint.
	 */
	public GameEndpoint() {
		gson = new Gson();
	}

	/**
	 * Pobiera liste gier/Gets the games list.
	 *
	 * @return list
	 */
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getList() {
		System.out.println("getList");
		return toJson(gameServer.getListOfGames());
	}

	/**
	 * tworzy gre/ Createt game.
	 *
	 * @param playerId   - id gracza/the player id
	 * @param opponentId - id przeciwnika/the opponent id
	 * @return string
	 */
	@GET
	@Path("{playerId}/{opponentId}")
	@Produces(MediaType.TEXT_PLAIN)
	public String createtGame(@PathParam("playerId") long playerId, @PathParam("opponentId") long opponentId) {
		System.out.println("GameEndpoint.createtGame " + playerId + " vs " + opponentId);
		Game game = playerService.createGame(playerId, opponentId);
		if (game.getGuessPlayer().getPlayerId() == playerId) {
			return "guess";
		}
		return "word";
	}

	/**
	 * Pobiera gre/Gets the game.
	 *
	 * @param playerId - id gracza/the player id
	 * @return - gre/the game
	 */
	@GET
	@Path("gameByPlayerId/{playerId}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getGame(@PathParam("playerId") long playerId) {
		System.out.println("GameEndpoint.getGame for player id = " + playerId);
		GameDto game = playerService.getGame(playerId);
		System.out.println("GameEndpoint.getGame is = " + game);
		return Optional.ofNullable(game).map(this::toJson).map(json -> Response.ok(json, MediaType.TEXT_PLAIN).build())
				.orElse(Response.status(Response.Status.NOT_FOUND).entity("GAME FOR USER NOT EXIST!").build());
	}

//	@GET
//	@Path("endGame/{playerId}")
//	@Produces(MediaType.TEXT_PLAIN)
//	public Response endGame(@PathParam("playerId") long playerId) {
//		System.out.println("GameEndpoint.endGame for player id = "+playerId);		
//		GameDto game = playerService.endGame(playerId);	 		
//		return Optional
//				.ofNullable(  game )
//							.map(this::toJson)
//							.map( json -> Response.ok(json,MediaType.TEXT_PLAIN).build() )
//							.orElse( Response.status(Response.Status.NOT_FOUND).entity("GAME FOR USER NOT EXIST!" ).build() )
//							;
//	}

	/**
	 * Przesyla litere do gry/Send letter and return game.
	 *
	 * @param playerId - id gracza/the player id
	 * @param letter   - literathe letter
	 * @return - odpowiedz/the response
	 */
	@GET
	@Path("sendLetter/{playerId}/{letter}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response sendLetterAndReturnGame(@PathParam("playerId") long playerId, @PathParam("letter") String letter) {
		System.out.println("GameEndpoint.getGame " + playerId);
		return Optional.ofNullable(playerService.updateGappedWordLetter(playerId, letter)).map(this::toJson)
				.map(json -> Response.ok(json, MediaType.TEXT_PLAIN).build())
				.orElse(Response.status(Response.Status.NOT_FOUND).entity("GAME FOR USER NOT EXIST!").build());
	}

	/**
	 * Ustawia slowo i zwraca je do gry/Sets the word and return game.
	 *
	 * @param playerId - id gracza/the player id
	 * @param word     - slowo/the word
	 * @return - odpowiedz /the response
	 */
	@GET
	@Path("updateWord/{playerId}/{word}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response setWordAndReturnGame(@PathParam("playerId") long playerId, @PathParam("word") String word) {
		System.out.println("GameEndpoint.getGame " + playerId);
		return Optional.ofNullable(playerService.updateWord(playerId, word)).map(this::toJson)
				.map(json -> Response.ok(json, MediaType.TEXT_PLAIN).build())
				.orElse(Response.status(Response.Status.NOT_FOUND).entity("GAME FOR USER NOT EXIST!").build());
	}

	/**
	 * Zamienia obekt na Json/Change object to Json.
	 *
	 * @param o - objekt/the object
	 * @return Json object
	 */
	private String toJson(Object o) {
		return gson.toJson(o);
	}

}
