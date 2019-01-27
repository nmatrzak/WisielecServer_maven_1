package service;

import java.util.List;

import dto.GameDto;
import dto.PlayerDto;
import game.Game;

/**
 * interfejs PlayerService/The Interface IPlayerService.
 * 
 * @author Piotr Podgórski
 * @version 1.0
 * @since 2019-01-01
 */

public interface IPlayerService {

	/**
	 * pobiera playersDto/Gets the playersDto.
	 *
	 * @return the playersDto
	 */
	List<PlayerDto> getPlayersDto();

	/**
	 * pobiera gracza/Gets the player.
	 *
	 * @param userName - nazwa uzytkownika/the user name
	 * @return gracz/the player
	 */
	PlayerDto getPlayer(String userName);

	/**
	 * pobiera gracza/Gets the player.
	 *
	 * @param id - nr id/the id
	 * @return gracz/the player
	 */
	PlayerDto getPlayer(long id);

	/**
	 * utworz gracza/Creates the player.
	 *
	 * @param userName nazwa uzytkownika/the user name
	 * @return playerDto
	 */
	PlayerDto createPlayer(String userName);

	/**
	 * usun gracza/Removes the player.
	 *
	 * @param id - nr id/the id
	 * @return playerDto
	 */
	PlayerDto removePlayer(long id);

	/**
	 * uworz gre/Creates the game.
	 *
	 * @param playerId   - id gracza/the player id
	 * @param opponentId - id przeciwnika/the opponent id
	 * @return game/the game
	 */
	Game createGame(long playerId, long opponentId);

	/**
	 * zaktualizuj slowo litera/Update gapped word with letter.
	 *
	 * @param playerId - id gracza/the player id
	 * @param letter   - litera/the letter
	 * @return gameDto
	 */
	GameDto updateGappedWordLetter(long playerId, String letter);

	/**
	 * zaktualizuj zgadywane slowo/Update guess word.
	 *
	 * @param playerId - id gracza/the player id
	 * @param word     - slowo/the word
	 * @return gameDto
	 */
	GameDto updateWord(long playerId, String word);

	/**
	 * pobiera gre/Gets the game.
	 *
	 * @param playerId - id gracza/the player id
	 * @return game
	 */
	GameDto getGame(long playerId);

	/**
	 * gracz aktywny/Player alive.
	 *
	 * @param playerId - id gracza/the player id
	 */
	void playerAlive(long playerId);

}
