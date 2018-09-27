package se.bms.wearep.coords;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
public class getCoords {
	

	public void test(String city) {
		String tempURL = "https://maps.googleapis.com/maps/api/geocode/json?address=" + city + "&sensor=false&key=AIzaSyDb5BNaLMM4s_e_-EJN7_Wj8EiUM6_FC08"; // creates the url with users input
		String rawData = null;	// temporary variable for storing all the data from the API
		try {
			URL url = new URL(tempURL);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();
			int responseCode = connection.getResponseCode();
			System.out.println("Response code is: " + responseCode);		
			if (responseCode != 200) {		// 200 = OK
				throw new RuntimeException("HttpResponseCode: " + responseCode);		// prints response code if response code wasn't 200
			}
			else {
				Scanner scanner = new Scanner(url.openStream());
				while(scanner.hasNext()) {
					rawData += scanner.nextLine();
				}
				scanner.close();
			}
			Gson gson = new GsonBuilder().create();
			
		} catch (MalformedURLException mUrlE) {
			
			mUrlE.printStackTrace();
			
		} catch(IOException ioe) {
			
			ioe.printStackTrace();
		}
		
		
	}
	
}
