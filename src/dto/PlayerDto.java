
package dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;
/**
 * Obiekt klasy PlayerDto sluży do przenoszenie danych gracza pomiedzy serwisami
 * (client server) Klasa implementuje interface Serializable (nie zawiera on
 * żadnych metod)./ The object of PlayerDto class carries game data between
 * processes (client and server) The class implements a tag interface
 * Serializable.This interface dosn't contain any method.
 * 
 * @author Norbert Matrzak
 * @version 1.0
 * @since 2019-01-01
 */
@XmlRootElement
public class PlayerDto implements Serializable {

	/** stala/The Constant serialVersionUID. */
	private static final long serialVersionUID = 7526471155622776147L;

	/**nazwa gracza/The player name. */
	private String name;

	/** liczba punktow/The No of points. */
	private long points = 0;

	/** liczba zwyviestw/The No of wins. */
	private long countWins = 0;

	/** liczba przegranych/The No of losts. */
	private long countLosts = 0;

	/** status gry/The game status. */
	private String status;

	/** Id gracza/The player id. */
	private long playerId = 0;

	/**
	 * konstruktor klasy PlayerDto/Instantiates a new playerDto.
	 */
	public PlayerDto() {
	}

	/**
	 * konstruktor klasy PlayerDto/Instantiates a new playerDto.
	 *
	 * @param playerId   - id gracza/the player id
	 * @param name       - nazwa gracza/the player name
	 * @param points     - liczba punktow/the No of points
	 * @param countWins  - liczba zwyciestw/the No of wins
	 * @param countLosts - liczba porazek/the No of losts
	 * @param status     - status gry/the game status
	 */
	public PlayerDto(long playerId, String name, long points, long countWins, long countLosts, String status) {
		this.playerId = playerId;
		this.name = name;
		this.points = points;
		this.countWins = countWins;
		this.countLosts = countLosts;
		this.status = status;
	}

	/**
	 *Ustawia id gracza/Sets the player id.
	 *
	 * @param playerId - new id gracza/the new player id
	 */
	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}

	/**
	 * Pobiera id gracza/Gets the player id.
	 *
	 * @return id gracza/playerId
	 */
	public long getPlayerId() {
		return playerId;
	}

	/**
	 * Pobiera nazwe gracza/Gets the player name.
	 *
	 * @return nazwa/name
	 */
	public String getName() {
		return name;
	}

	/**
	 *Ustawia imie gracza/Sets the player name.
	 *
	 * @param name - nowa nazwa gracza/the new player name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Pobiera liczbe punktow/Gets the No of points.
	 *
	 * @return liczba punktow/points
	 */
	public long getPoints() {
		return points;
	}

	/**
	 * Ustawia liczbe punktow/Sets the No of points.
	 *
	 * @param points - nowa liczba punktow/the new No of points
	 */
	public void setPoints(long points) {
		this.points = points;
	}

	/**
	 * Pobiera liczbe zwyciestw/Gets the No of wins.
	 *
	 * @return liczba zwyciestw/countWins
	 */
	public long getCountWins() {
		return countWins;
	}

	/**
	 * Ustawia liczbe zwyciestw/Sets the No of wins.
	 *
	 * @param countWins - nowa liczba zwyciestw/the new No of wins
	 */
	public void setCountWins(long countWins) {
		this.countWins = countWins;
	}

	/**
	 * Pobiera liczbe porazek/Gets the No of losts.
	 *
	 * @return liczba przegranych/countLosts
	 */
	public long getCountLosts() {
		return countLosts;
	}

	/**
	 * Ustawia liczbe porazek/Sets the No of losts.
	 *
	 * @param countLosts - nowa liczba porazek/the new No of losts
	 */
	public void setCountLosts(long countLosts) {
		this.countLosts = countLosts;
	}

	/**
	 * Pobiera status gry/Gets the status.
	 *
	 * @return status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Ustawia status gry/Sets the status.
	 *
	 * @param status - nowy status/the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/*
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Player: Name=" + name;
	}
}
