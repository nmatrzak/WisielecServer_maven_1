package endpoint;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Punkt dostepowy do webservice/Access point to webservice
 * 
 * @author Piotr Podgorski
 * @version 1.0
 * @since 2019-01-01
 */
@Path("/control")
public class ControlEndpoint {

	/**
	 * Wypisuje date uruchomienia servera/.Prints the date of server started
	 *
	 * @return the string
	 */
	@GET
	@Path("/health")
	@Produces({ MediaType.TEXT_PLAIN })
	public String health() {
		return "Server date time: " + getFomratedDateTime();
	}

	/**
	 * Zwraca wartosc daty w zadanym formacie/Gets the fomrated date time.
	 *
	 * @return the fomrated date time
	 */
	private String getFomratedDateTime() {
		LocalDateTime date = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
		return date.format(formatter);
	}

}
