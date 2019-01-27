package tests;

import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import org.junit.Test;

import game.Game;
import game.Player;

/**
 * klasa testujace klase Game / The class for test Game class.
 * 
 * @author Norbert Matrzak
 * @version 1.0
 * @since 2019-01-01
 */
public class GameTests {

	/**
	 * test ciągłosci identyfikatorow gry/ test of continous ids game.
	 */
	@Test
	public void gamesGetContinousIds() {
		Game game1 = new Game();
		Game game2 = new Game();

		assertTrue(game1.getGameId() > 0);
		assertTrue(game2.getGameId() == game1.getGameId() + 1);
	}

	/**
	 * test zmian aktywnosci w grze/test changes in game acvtivity.
	 */
	@Test
	public void gameAcvtivityHasChange() {
		Game game = new Game();
		LocalDateTime lastActivity = game.getLastActivity();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		game.updateWord("TEST");
		assertTrue(game.getLastActivity().isAfter(lastActivity));
	}

	/**
	 * test gry z para graczy/test of game with word-player and guess-player.
	 */
	@Test
	public void gameHasWordAndGuessPlayers() {
		Player player1 = new Player("Piotr");
		Player player2 = new Player("Wojtek");

		Game game = new Game();
		game.setGuessPlayer(player1);
		game.setWordPlayer(player2);

		game.init();

		assertTrue(game.getWordPlayer().getName().equals(player1.getName())
				|| game.getWordPlayer().getName().equals(player2.getName()));
		assertTrue(game.getGuessPlayer().getName().equals(player2.getName())
				|| game.getGuessPlayer().getName().equals(player1.getName()));
	}

}
