package endpoint;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Punkt dostępowy do webservice/Access point to webservice
 * 
 * @author Piotr Podgórski
 * @version 1.0
 * @since 2019-01-01
 */
//wartość adnotacji @Path jest czescia szablonu adresu URI/The @Path annotation’s value is a partial URI path
@Path("/control")
public class ControlEndpoint {

	/**
	 * Wypisuje date uruchomienia servera/.Prints the date of server started
	 *
	 * @return the string
	 */
	// Adnotacja @GET jest desygnatorem metody żądania i odpowiada podobnie nazwanej
	// metodzie HTTP/ The @GET annotation is a request method designator and
	// corresponds to the similarly named HTTP method
	@GET
	@Path("/health")
	// Adnotacja @Produces służy do określania typów lub reprezentacji nośników
	// MIME, które mogą być tworzone i wysyłane do klienta./ The @Produces
	// annotation is used to specify the MIME media types or representations a
	// resource can produce and send back to the client.
	@Produces({ MediaType.TEXT_PLAIN })
	public String health() {
//		System.out.println("ControlEndpoint.health");	
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
