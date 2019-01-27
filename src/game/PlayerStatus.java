package game;

import java.util.Set;

import jersey.repackaged.com.google.common.collect.Sets;

/**
 * klasa wyliczeniowa stanow gracza/ The Enum of player status.
 * 
 * @author Norbert Matrzak
 * @version 1.0
 * @since 2019-01-01
 */

public enum PlayerStatus {

	/** niewidoczny/The invisible. */
	INVISIBLE,
	/** zapisany/The created. */
	CREATED,
	/** w grze/The playing. */
	PLAYING;

	/** status zajetosci gracza/The busy statuses. */
	public static Set<PlayerStatus> busyStatuses = Sets.immutableEnumSet(PLAYING);// , WON, LOST );

}
