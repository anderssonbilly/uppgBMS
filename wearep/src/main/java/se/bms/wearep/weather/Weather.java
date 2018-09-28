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

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class Weather {
	private String name;
	private String value;
	private String unit;
	private String forecastStartTime;
	private String approvedTime;
	private String validTime;
	
	private JsonArray weatherForecast = new JsonArray();
			
	/**
	 * @param name
	 * @param value
	 * @param unit
	 * @param forecastStartTime
	 * @param approvedTime
	 * @param validTime
	 */
	public Weather(String name, String value, String unit, String forecastStartTime, String approvedTime,
			String validTime) {
		this.name = name;
		this.value = value;
		this.unit = unit;
		this.forecastStartTime = forecastStartTime;
		this.approvedTime = approvedTime;
		this.validTime = validTime;
	}

	public Weather() {	
	}
	
	//Get weather data
	public String getSMHIIndata() {
		String smhiIndata = ""; // store the JSON data streamed
		
		try {
			// This is an example url working: "https://opendata-download-metfcst.smhi.se/api/category/pmp3g/version/2/geotype/point/lon/11.6/lat/58.2/data.json"
			Double [] coord = new Double[] {10.2, 68.4}; //TODO Replace with method to get coord
			String forUrl = setCoordinatesInUrl(coord);
			URL url = new URL(forUrl);
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
		//	System.out.println("\nJSON Response in String format"); //for reference only
		//	System.out.println(smhiIndata); //for reference only
			sc.close(); // close stream when reading completed
			connection.disconnect(); ////Disconnect the HttpURLConnection stream
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
	
	//Create first JSONObject with all weather data 
	public JsonObject createJSONObjectFromSMHIdata(String indata) {
		//indata = getSMHIIndata();
		JsonObject jsonTree = null;
		JsonParser parser = new JsonParser();
		try {
			jsonTree = (JsonObject) parser.parse(indata);
			// System.out.println("Got a jsonTree JsonObject");
			// System.out.println("Detta är ett jsonTree: " + jsonTree);
		} catch (JsonSyntaxException e) {
			System.out.println("Something wrong with the JSON - you've got a JsonSyntaxException");
			System.out.println(e.toString());
		}
	return jsonTree;
	}
		
	// Get forecast start time
	public JsonElement returnForecastStartTime(JsonObject jsonTree) {
		JsonElement forecastStartTime = jsonTree.get("referenceTime");
		System.out.println("Forecast start time: "+ forecastStartTime);
	return forecastStartTime;
	}
	
	// Get forecast approved time
	public JsonElement returnForecastApprovedTime(JsonObject jsonTree) {
		JsonElement approvedTime = jsonTree.get("approvedTime");
		System.out.println("Forecast approved: "+ approvedTime);
	return approvedTime;
	}
		
	// Create timeSeries JSonArray
	public JsonArray createTimeSeriesJsonArrayFromJSonObject(JsonObject jsonTree) {	
		JsonArray timeSeries = (JsonArray) jsonTree.get("timeSeries");
		// System.out.println("Got a timeSeries JsonArray");
		// System.out.println(timeSeries);
	return timeSeries;
	}
		
	/*	
	// Create parameters JSonArray
	public JsonArray createParametersJsonArray(JsonArray timeSeries) {
		JsonArray parameters = null;
		for(int i=0;i<timeSeries.size();i++) {
			JsonObject parametersObject = (JsonObject)timeSeries.get(i);
			parameters = (JsonArray) parametersObject.get("parameters");
			// System.out.println("Got a parameters JsonArray"); // for reference only
			// System.out.println("Parameters JSON-array: " + parameters);
			}
		return parameters;
	}
		*/
	/*
	// Create results JSonArray //TODO
	public JsonArray createResultsJsonArray(JsonArray parameters) {
		JsonArray results = null;
		for(int j=0;j<parameters.size();j++) {
			JsonObject resultsObject = (JsonObject)parameters.get(j);
			results = (JsonArray) resultsObject.get("values");
		//	System.out.println("Got a results JsonArray"); // for reference only
			System.out.println("Results JSON-array: " + results);
			JsonElement name = resultsObject.get("name");
			}
		return parameters;
	}
	*/
	
	// Create JSonArray with weather data JSONObjects for each measurement		
	public JsonArray createJSONArrayOfMeasurements(JsonObject jsonTree) {
		JsonElement forecastStartTimeElement = returnForecastStartTime(jsonTree);
		String forecastStartTime = forecastStartTimeElement.getAsString();
		JsonElement approvedTimeElement = returnForecastApprovedTime(jsonTree);
		String approvedTime = approvedTimeElement.getAsString();
		JsonArray timeSeries = createTimeSeriesJsonArrayFromJSonObject(jsonTree);
		for(int i=0;i<timeSeries.size();i++) {
			JsonObject parametersObject = (JsonObject)timeSeries.get(i);
			JsonArray parameters = (JsonArray) parametersObject.get("parameters");
			JsonElement validTimeElement = parametersObject.get("validTime");
			String validTime = validTimeElement.getAsString();
			System.out.println("\nForecast for " + validTime);
			// System.out.println("Got a parameters JsonArray"); // for reference only
		//	System.out.println("Parameters JSON-array: " + parameters); for reference only
			for(int j=0;j<parameters.size();j++) {
				JsonObject resultsObject = (JsonObject)parameters.get(j);
				JsonArray results = (JsonArray) resultsObject.get("values");
			//	System.out.println("Got a results JsonArray"); // for reference only
			//	System.out.println("Results: " + results);
				JsonElement nameElement = resultsObject.get("name");
				String name = nameElement.getAsString();
			//	System.out.println("Name: " + name);
				JsonElement unitElement = resultsObject.get("unit");
				String unit = unitElement.getAsString();
				for(int k=0;k<results.size();k++) {
					Object valuesObject = results.get(k);
					String value = (String) valuesObject.toString();
				//	System.out.println("Got a String value"); // for reference only
				//	System.out.println("Value: " + value);
					name = updateName(name);	
					if(name == "Percent of precipitation in frozen form")
						value = updateValueSpp(value);
					if(name == "Precipitation category") 
						value = updateValuePcat(value);
					if(name == "Weather symbol") 
						value = updateValueWSymb2(value);
					
				//	System.out.println("Forecast for: " + validTime + ". Approved on " + approvedTime + " and valid from (forecast start) " + forecastStartTime + " " + name + ": " + value + " " + unit); //TODO
					System.out.println(name + ": " + value + " " + unit );
					String jsonWeather = createJsonWeatherObject(validTime, name, value, unit, approvedTime,forecastStartTime);
					//System.out.println("This is jsonWeather from big method: " + jsonWeather); // for reference only	
					weatherForecast.add(jsonWeather);
					
				}
					
			}
		}
		System.out.println("This is weatherForecast: " + weatherForecast); //for reference only
		return weatherForecast;
		
	}
	

	
	//Method to create JSON object
	private String createJsonWeatherObject(String validTime, String name, String value, String unit, String approvedTime, String forecastStartTime) {
		Weather weather = new Weather(name, value, unit, forecastStartTime, approvedTime, validTime);
		Gson gson = new Gson();
		String json = gson.toJson(weather);
		//System.out.println("This is json from weather object: " + json); //for reference only
	return json;
	}
	
	// Method to set coordinates in URL
	// Coordinates will be delivered as Double array: Double[] {longitude, latitude};
	public String setCoordinatesInUrl(Double [] coord) {
		Double lon = coord[0];
		Double lat = coord[1];
		//defaultUrl = "https://opendata-download-metfcst.smhi.se/api/category/pmp3g/version/2/geotype/point/lon/x/lat/y/data.json";
		String url = "https://opendata-download-metfcst.smhi.se/api/category/pmp3g/version/2/geotype/point/lon/" + lon + "/lat/" + lat + "/data.json";
		System.out.println("URL for position " + lon + ", " + lat + " is: " + url);
		return url;
	}
	
	
	// Method to switch name
	private String updateName(String name) {
		// System.out.println("Now updating name"); // just for testing
		switch (name) {
		case "t": 
			return name = "Air temperature";
		case "msl": 
			return name = "Air pressure";
		case "vis": 
			return name = "Horizontal visibility";
		case "wd": 
			return name = "Wind direction";
		case "ws": 
			return name = "Wind speed";
		case "r": 
			return name = "Relative humidity";
		case "tstm": 
			return name = "Thunder probability";
		case "tcc_mean": 
			return name = "Mean value of total cloud cover";
		case "lcc_mean": 
			return name = "Mean value of low level cloud cover";
		case "mcc_mean": 
			name = "Mean value of medium level cloud cover";
		case "hcc_mean": 
			return name = "Mean value of high level cloud cover";
		case "gust": 
			return name = "Wind gust speed";
		case "pmin": 
			return name = "Minimum precipitation intensity";
		case "pmax": 
			return name = "Maximum precipitation intensity";
		case "spp": 
			return name = "Percent of precipitation in frozen form";
		case "pcat": 
			name = "Precipitation category";
			return name;
		case "pmean": 
			return name = "Mean precipitation intensity";
		case "pmedian": 
			return name = "Median precipitation intensity";
		case "Wsymb2": 
			return name = "Weather symbol";
		case "WSymb": 
			return name = "Weather symbol old";
		default:
			break;
		}
		return name;
	}
	
	// Method 1 to switch value
	private String updateValueWSymb2(String value) {
	//	System.out.println("Now updating value WSymb2"); // just for testing
		switch (value) {
		case "1": 
			return value = "Clear sky";
		case "2": 
			return value = "Nearly clear sky";
		case "3": 
			return value = "Variable cloudiness";
		case "4": 
			return value = "Halfclear sky";
		case "5": 
			return value = "Cloudy sky";
		case "6": 
			return value = "Overcast";
		case "7": 
			return value = "Fog";
		case "8": 
			return value = "Light rain showers";
		case "9": 
			return value = "Moderate rain showers";
		case "10": 
			return value = "Heavy rain showers";
		case "11": 
			return value = "Thunderstorm";
		case "12": 
			return value = "Light sleet showers";
		case "13": 
			return value = "Moderate sleet showers";
		case "14": 
			return value = "Heavy sleet showers";
		case "15": 
			return value = "Light snow showers";
		case "16": 
			return value = "Moderate snow showers";
		case "17": 
			return value = "Heavy snow showers";
		case "18": 
			return value = "Light rain";
		case "19": 
			return value = "Moderate rain";
		case "20": 
			return value = "Heavy rain";
		case "21": 
			return value = "Thunder";
		case "22": 
			return value = "Light sleet";
		case "23": 
			return value = "Moderate sleet";
		case "24": 
			return value = "Heavy sleet";
		case "25": 
			return value = "Light snowfall";
		case "26": 
			return value = "Moderate snowfall";
		case "27": 
			return value = "Heavy snowfall";
		}
		return value;
	}
	
	//Method 2 to switch value
	private String updateValuePcat(String value) {
	//	System.out.println("Now updating value pcat"); // just for testing
		switch (value) {
		case "0": 
			return value = "No precipitation";
		case "1": 
			return value = "Snow";
		case "2": 
			return value = "Snow and rain";
		case "3": 
			return value = "Rain";
		case "4": 
			return value = "Drizzle";
		case "5": 
			return value = "Freezing rain";
		case "6": 
			return value = "Freezing drizzle";
		}
		return value;
	}
	
	//Method 3 to switch value
	private String updateValueSpp(String value) {
	//	System.out.println("Now updating value spp"); // just for testing
		switch (value) {
		case "-9": 
			return value = "0";
		}
		return value;
	}
		
}

