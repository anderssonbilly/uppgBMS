package se.bms.wearep.coords;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class getCoords {
	

	public void getCoords(String city) {
		String tempURL = "https://maps.googleapis.com/maps/api/geocode/json?address=" + city + "&sensor=false&key=AIzaSyDb5BNaLMM4s_e_-EJN7_Wj8EiUM6_FC08"; // creates the url with users input
		String rawData = "";	// temporary variable for storing all the data from the API
		try {
			URL url = new URL(tempURL);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();
			int responseCode = connection.getResponseCode();
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
			
			//System.out.println(rawData);
			JSONParser parser = new JSONParser();
			JSONObject rootJSON = null;
			JSONArray json2 = null;
			JSONObject json3 = null;
			JSONObject json4 = null;
			JSONObject json5 = null;
			try {
				rootJSON = (JSONObject) parser.parse(rawData);
				json2 = (JSONArray) rootJSON.get("results");
				json3 = (JSONObject) json2.get(0);
				json4 = (JSONObject) json3.get("geometry");
				json5 = (JSONObject) json4.get("location");
			} catch (ParseException e) {
				System.out.println("Something went wrong with the JSON parser");
				e.printStackTrace();
			}
			
			Double longitude = (Double) json5.get("lng");
			Double latitude = (Double) json5.get("lat");
			System.out.println("Longitude: " + longitude + "\nLatitude: " + latitude + "\nFor city: " + city);
			
			
		} catch (MalformedURLException mUrlE) {
			
			mUrlE.printStackTrace();
			
		} catch(IOException ioe) {
			
			ioe.printStackTrace();
		}
		
		
	}
	public static void main(String[] args) {
		getCoords test = new getCoords();
		test.getCoords("Berlin");
	}

}
