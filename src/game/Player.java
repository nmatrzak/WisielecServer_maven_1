package game;

import java.time.LocalDateTime;
import java.util.Random;

/**
 * klasa gracz/The Class Player.
 * 
 * @author Piotr Podgórski
 * @version 1.0
 * @since 2019-01-01
 */
public class Player {

	/**
	 * stala ilosci sekund sprawdzania aktywnosci przeciwnika/The Constant
	 * MAX_ECHO_SECONDS.
	 */
	private static final int MAX_ECHO_SECONDS = 10;

	/** id gracza/The player id. */
	private long playerId = 0;

	/** nr kolejnej rundy gracza/The seq player id. */
	private static long seqPlayerId = 0;

	/** nazwa/The name. */
	private String name;

	/** punkty/The points. */
	private long points = 0;

	/** liczba zwyciestw/The count wins. */
	private long countWins = 0;

	/** liczba porazek/The count losts. */
	private long countLosts = 0;

	/** status gracza/The status of player */
	private PlayerStatus status = PlayerStatus.CREATED;

	/** czy gra z komputerem/The is computer. */
	private boolean isComputer = false;

	/** data ostatniej aktywnosci/date of last activity. */
	private LocalDateTime lastActivity;

	/**
	 * konstruktor klasy Player/Instantiates a new player.
	 *
	 * @param name - nazwa/the name
	 */
	public Player(String name) {
		playerId = ++seqPlayerId;
		System.out.println("Player '" + name + "' created with id=" + playerId);
		this.name = name;
		updateLastActivity();
	}

	/**
	 * uaktualnienie ostatniej aktywnosci/Update last activity.
	 */
	public void updateLastActivity() {
//System.out.println("Player with id="+playerId+" updateLastActivity");
		lastActivity = LocalDateTime.now();
	}

	/**
	 * czy jest odpowedz/ is feedback.
	 *
	 * @return jesli nie ma odpowiedzi, zwraca true/true, if non feedback
	 */
	public boolean noneFeedBack() {
		return LocalDateTime.now().minusSeconds(MAX_ECHO_SECONDS).isAfter(lastActivity);
	}

	/**
	 * pobranie id gracza/Gets the player id.
	 *
	 * @return id gracza/the player id
	 */
	public long getPlayerId() {
		return playerId;
	}

	/**
	 * stworzenie losowej nazwy gracza/Creates the random name.
	 *
	 * @return the string
	 */
	public static String createRandomName() {
		return "VirtualPlayer" + (10000000 + (new Random()).nextInt(10000000));
	}

	/**
	 * stworzenie gracza wirtualnego/Creates the computer player.
	 *
	 * @return the player
	 */
	public static Player createComputerPlayer() {
		Player player = new Player(createRandomName());
		player.isComputer = true;
		return player;
	}

	/**
	 * dodaj punkty/Adds the points.
	 *
	 * @param countUniqueLetters - liczba unikalnych liter/the count unique letters
	 * @return the long
	 */
	public long addPoints(long countUniqueLetters) {
		this.points += countUniqueLetters;
		return countUniqueLetters;
	}

	/**
	 * liczba zwyciestw/ No of wins.
	 *
	 * @return the long
	 */
	public long incWin() {
		return ++countWins;
	}

	/**
	 * liczba porazek/ No of losts
	 *
	 * @return the long
	 */
	public long incLost() {

		return ++countLosts;
	}

	/**
	 * koniec gry/End game.
	 */
	public void endGame() {
		status = PlayerStatus.CREATED;
	}

	/**
	 * pobranie nazwy gracza/Gets the name.
	 *
	 * @return nazwa/the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * pobranie liczby punktow/Gets the points.
	 *
	 * @return liczba punktow/the points
	 */
	public long getPoints() {
		return points;
	}

	/**
	 * pobranie liczby zwyciestw/Gets the count wins.
	 *
	 * @return liczba zwyciestw/the count wins
	 */
	public long getCountWins() {
		return countWins;
	}

	/**
	 * pobranie liczby porazek/Gets the count losts.
	 *
	 * @return liczba prazek/the count losts
	 */
	public long getCountLosts() {
		return countLosts;
	}

	/**
	 * ustaw imie gracza/Sets the name.
	 *
	 * @param name - nowe imie/the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * pobierz status gracza/Gets the player status.
	 *
	 * @return staus gracza/the player status
	 */
	public PlayerStatus getStatus() {
		return status;
	}

	/**
	 * ustaw status gracza/Sets the player status.
	 *
	 * @param status - nowy status gracza/the new status
	 */
	public void setStatus(PlayerStatus status) {
		this.status = status;
	}

	/**
	 * sprawdz czy gra z komputerem/chack, is game with computer.
	 *
	 * @return jesli gra z komputerem ustaw true/true, if is computer
	 */
	public boolean isComputer() {
		return isComputer;
	}

	/*
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("Player: %s, points: %d, wins: %d, losts: %d, is_computer: %d", name, points, countWins,
				countLosts, isComputer ? 1 : 0);
	}
}
