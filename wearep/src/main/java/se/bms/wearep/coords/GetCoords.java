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

public class GetCoords {
	String city = "";
	String firstHalfURL = "https://maps.googleapis.com/maps/api/geocode/json?address=";
	String secondHalfURL = "&sensor=false&key=AIzaSyDb5BNaLMM4s_e_-EJN7_Wj8EiUM6_FC08";
	String tempURL = "";
	String rawData = "";	// temporary variable for storing all the data from the API to be parsed
	Double longitude = null;
	Double latitude = null;
	
	
	public void run(String tmpCity) {
		city = tmpCity;
		setURL();
		collectJSON(tempURL);
		parseJSON();
		
	}
	
	
	private void setURL() {
		if(city.contains("/")) {
			city = "Piteå";
		}
		else if(city.contains("&")) {
			city = "Piteå";
		}
			
			
		tempURL = firstHalfURL + city + secondHalfURL; // creates the url with users input
	}
	
	private void parseJSON() {
		JSONParser parser = new JSONParser();
		JSONObject rootJSON = null;
		JSONArray json2 = null;
		JSONObject json3 = null;		// the creation of all the JSONObjects
		JSONObject json4 = null;
		JSONObject json5 = null;
		try {
			rootJSON = (JSONObject) parser.parse(rawData);
			json2 = (JSONArray) rootJSON.get("results");		// here we start digging into the JSON data to get to the coordinates 
			json3 = (JSONObject) json2.get(0);
			json4 = (JSONObject) json3.get("geometry");
			json5 = (JSONObject) json4.get("location");
		} catch (ParseException e) {
			System.out.println("Something went wrong with the JSON parser");
			e.printStackTrace();
		}
		longitude = (Double) json5.get("lng");
		latitude = (Double) json5.get("lat");
	}
	
	
	private void collectJSON(String tempURL) {
		
		try {
			URL url = new URL(tempURL);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();		//creating the connection to the url
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
		
		} catch (MalformedURLException mUrlE) {
			System.out.println("Something is terribly wrong with the url");
			mUrlE.printStackTrace();
			
		} catch(IOException ioe) {
			
			ioe.printStackTrace();
		}
		
	}

	public String getCity() {
		return city;
	}
	
	public Double getLongitude() {
		
		if(longitude != null) {
			Double roundedLongitude = round(longitude);
			
		return roundedLongitude;
		}
		else return 0.000;
	}
	
	public Double getLatitude() {
		if(latitude != null) {
			Double roundedLatitude = round(latitude);
			return roundedLatitude;
			}
			else return 0.000;
	}
	
	private double round(Double toBeRounded) {
		double rounding = toBeRounded;
		rounding = Math.round(rounding * 1000000) / 1000000.0;
		return rounding;
	}
	
}
	
	
	
