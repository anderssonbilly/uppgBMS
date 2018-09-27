/**
 * 
 */
package se.bms.wearep.weather;

/**
 * @author Malin Albinsson
 *
 */
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Weather {

	//Get weather data
	public String getSMHIIndata() { //TODO remove static
		String smhiIndata = ""; // store the JSON data streamed
		
		try {
			
			URL url = new URL("https://opendata-download-metfcst.smhi.se/api/category/pmp3g/version/2/geotype/point/lon/11.6/lat/58.2/data.json");
			HttpURLConnection connection = (HttpURLConnection)url.openConnection(); // parse URL to open connection
			connection.setRequestMethod("GET"); // set method to request data
			connection.connect(); // connect
			int responseCode = connection.getResponseCode(); // Get response status of API
			System.out.println("Response code is: " +responseCode);
			
			if(responseCode != 200) // 200 is OK --> continue, otherwise throw exception
				throw new RuntimeException("HttpResponseCode: " + responseCode);
			else // get the data
			{
				Scanner sc = new Scanner(url.openStream()); // read JSON data from stream
				while(sc.hasNext())
				{
					smhiIndata += sc.nextLine();
				}
			System.out.println("\nJSON Response in String format"); //for reference only
			System.out.println(smhiIndata); //for reference only
				sc.close(); // close stream when reading completed
			}
			
		} catch (MalformedURLException e1) {
			System.out.println("Malformed URL");
			System.out.println(e1.toString());
	
		} catch (IOException e2) {
			System.out.println("IO exception");
			System.out.println(e2.toString());
		}
		
		return smhiIndata;
		
	}
	/*
	//for testing purposes only
	public static void main(String[] args) {
		getSMHIIndata();

	}
	*/
}

