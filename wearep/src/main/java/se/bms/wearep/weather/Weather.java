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
	private String temperature;
	private String forecast;
	
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
	
	public Weather(String validTime, String forecast, String temperature) {
		this.validTime = validTime;
		this.setForecast(forecast);
		this.setTemperature(temperature);
	}
	
	public Weather(String name, String value, String unit, String forecastStartTime, String approvedTime,
			String validTime, String temperature, String forecast) {
		this.name = name;
		this.value = value;
		this.unit = unit;
		this.forecastStartTime = forecastStartTime;
		this.approvedTime = approvedTime;
		this.validTime = validTime;
		this.setTemperature(temperature);
		this.setForecast(forecast);
	}

	
	
	public Weather() {	
	}
	
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the unit
	 */
	public String getUnit() {
		return unit;
	}

	/**
	 * @param unit the unit to set
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}

	/**
	 * @return the forecastStartTime
	 */
	public String getForecastStartTime() {
		return forecastStartTime;
	}

	/**
	 * @param forecastStartTime the forecastStartTime to set
	 */
	public void setForecastStartTime(String forecastStartTime) {
		this.forecastStartTime = forecastStartTime;
	}

	/**
	 * @return the approvedTime
	 */
	public String getApprovedTime() {
		return approvedTime;
	}

	/**
	 * @param approvedTime the approvedTime to set
	 */
	public void setApprovedTime(String approvedTime) {
		this.approvedTime = approvedTime;
	}

	/**
	 * @return the validTime
	 */
	public String getValidTime() {
		return validTime;
	}

	/**
	 * @param validTime the validTime to set
	 */
	public void setValidTime(String validTime) {
		this.validTime = validTime;
	}

	/**
	 * @return the weatherForecast
	 */
	public JsonArray getWeatherForecast() {
		return weatherForecast;
	}

	/**
	 * @param weatherForecast the weatherForecast to set
	 */
	public void setWeatherForecast(JsonArray weatherForecast) {
		this.weatherForecast = weatherForecast;
	}
	
	// Method to set coordinates in URL
	private String setCoordinatesInUrl(Double lon, Double lat) {
			//System.out.println("lat: "+ lat + " long " + lon);
			String url = "https://opendata-download-metfcst.smhi.se/api/category/pmp3g/version/2/geotype/point/lon/" + lon + "/lat/" + lat + "/data.json";
		//	System.out.println("URL for position " + lon + ", " + lat + " is: " + url);
		return url;
	}
	
	//Get weather data
	protected String getSMHIIndata(Double lon, Double lat) {
		String smhiIndata = ""; // store the JSON data streamed
		
		try {
			// This is an example url working: "https://opendata-download-metfcst.smhi.se/api/category/pmp3g/version/2/geotype/point/lon/11.6/lat/58.2/data.json"
			String forUrl = setCoordinatesInUrl(lon, lat);
		//	System.out.println("This is string forURL " + forUrl);
			URL url = new URL(forUrl);
			System.out.println("URL: " +url);
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
	protected JsonObject createJSONObjectFromSMHIdata(String indata) {
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
	private JsonElement returnForecastStartTime(JsonObject jsonTree) {
		JsonElement forecastStartTime = jsonTree.get("referenceTime");
		System.out.println("Forecast start time: "+ forecastStartTime);
	return forecastStartTime;
	}
	
	// Get forecast approved time
	private JsonElement returnForecastApprovedTime(JsonObject jsonTree) {
		JsonElement approvedTime = jsonTree.get("approvedTime");
		System.out.println("Forecast approved: "+ approvedTime);
	return approvedTime;
	}
	
	
	// Get formatted forecast start time
		private String getFormattedForecastStartTime(JsonObject jsonTree) {
			String forecastStartTime = getStringFromElementInJsonObject("referenceTime", jsonTree);
	        forecastStartTime = formatStringDate(forecastStartTime) + " UTC";
			System.out.println("Forecast start time: "+ forecastStartTime);
		return forecastStartTime;
		}
		
	// Get formatted approved time
			private String getFormattedApprovedTime(JsonObject jsonTree) {
				String approvedTime = getStringFromElementInJsonObject("approvedTime", jsonTree);
		        approvedTime = formatStringDate(approvedTime) + " UTC";
				System.out.println("Approved time: "+ approvedTime);
			return approvedTime;
			}

	
	// Create JSonArray with weather data JSONObjects for each measurement		
		protected JsonArray createJSONArrayOfMeasurements(JsonObject jsonTree) {
			String forecastStartTime = getFormattedForecastStartTime(jsonTree);
			String approvedTime = getFormattedApprovedTime(jsonTree);
	        String jsonArrayName = "timeSeries";
	        JsonArray timeSeries = getJsonArrayFromJsonObject(jsonArrayName, jsonTree);
	        for(int i=0;i<timeSeries.size();i++) {
	            String validTime = getStringFromJsonArray(timeSeries, i, "validTime");
	            validTime = formatStringDate(validTime) + " UTC";
	            JsonArray parameters = getJsonArrayFromJsonArray(timeSeries, "parameters", i);
	            for(int j=0;j<parameters.size();j++) {
	                String name = getStringFromJsonArray(parameters, j, "name");
	                String unit = getStringFromJsonArray(parameters, j, "unit");
	                JsonArray results = getJsonArrayFromJsonArray(parameters, "values", j);
	                for (int k=0; k<results.size(); k++) {
	                	Object valuesObject = results.get(k);
						String value = (String) valuesObject.toString();
	                    name = updateName(name);	
						if(name == "Percent of precipitation in frozen form")
							value = updateValueSpp(value);
						if(name == "Precipitation category") 
							value = updateValuePcat(value);
						if(name == "Forecast") 
	                        value = updateValueWSymb2(value); 
						
                 //       System.out.println("Forecast for: " + validTime + ". Approved on " + approvedTime + " and valid from (forecast start) " + forecastStartTime + " " + name + ": " + value + " " + unit);
                        JsonObject jsonWeather = createJsonWeatherObject(validTime, name, value, unit, approvedTime,forecastStartTime);
                        weatherForecast.add(jsonWeather);
	                    }
	                
	            }
	        }
	        return weatherForecast;
	    }

	// get JsonArray from JsonArray  //Not used?
		private JsonArray getJsonArrayFromJsonArray(JsonArray jsonArray, String newJsonArrayName, int i) {	
			JsonObject jsonObject = getJsonObjectFromJsonArray(jsonArray, i);
			JsonArray newJsonArray = (JsonArray) jsonObject.get(newJsonArrayName);
	    return newJsonArray;
	    }

	  
	
	// get String from Element in JSON object	
	    private String getStringFromElementInJsonObject(String variableName, JsonObject jsonObject) {
	    String variable = jsonObject.get(variableName).getAsString();
	    return variable;
	    }

	// Get JSonArray from JsonObject
	    private JsonArray getJsonArrayFromJsonObject(String jsonArrayName, JsonObject jsonObject) {	
	        JsonArray jsonArray = (JsonArray) jsonObject.get(jsonArrayName);
	    return jsonArray;
	    }

	// Get JSonObject from JsonArray
	    private JsonObject getJsonObjectFromJsonArray(JsonArray jsonArray, int i) {	
	        JsonObject jsonObject = (JsonObject) jsonArray.get(i);
	    return jsonObject;
	    }

	//get String from Json Array
	    private String getStringFromJsonArray(JsonArray jsonArray, int i, String variableName) {
	        JsonObject jsonObject = getJsonObjectFromJsonArray(jsonArray, i);
	        String variable = getStringFromElementInJsonObject(variableName, jsonObject);
	        return variable;
	    }
	

	
	//Format date string
	private String formatStringDate(String dateString) {
		String[] s0 = dateString.split("T");
		dateString = s0[0] + " " + s0[1].substring(0,s0[1].length()-4);
		return dateString;
	}
		
	

	//Method to create JSON object not string
		private JsonObject createJsonWeatherObject(String validTime, String name, String value, String unit, String approvedTime, String forecastStartTime) {
			Weather weather = new Weather(name, value, unit, forecastStartTime, approvedTime, validTime);
			Gson gson = new Gson();
			String json = gson.toJson(weather);
			//System.out.println("This is json from weather object: " + json); //for reference only
			JsonObject jsonObject = null;
			JsonParser parser = new JsonParser();
			try {
				jsonObject = (JsonObject) parser.parse(json);
				// System.out.println("Got a JsonObject");
				// System.out.println("This is a jsonObject: " + jsonObject);
			} catch (JsonSyntaxException e) {
				System.out.println("Something wrong with the JSON - you've got a JsonSyntaxException");
				System.out.println(e.toString());
			}
			return jsonObject;
		}
		
		//Method to create JSON string
		protected String createJsonStringFromWeather(Weather weather) {
			Gson gson = new Gson();
			String json = gson.toJson(weather);
		//	System.out.println("This is json from weather object: " + json); //for reference only
			return json;
		}
		
		//Method to create JSON object from JSON string
		protected JsonObject createJsonObjectFromJsonString(String json) {
			JsonObject jsonObject = null;
			JsonParser parser = new JsonParser();
			try {
				jsonObject = (JsonObject) parser.parse(json);
				// System.out.println("Got a JsonObject");
				// System.out.println("Detta är ett jsonObject: " + jsonObject);
			} catch (JsonSyntaxException e) {
				System.out.println("Something wrong with the JSON - you've got a JsonSyntaxException");
				System.out.println(e.toString());
			}
			return jsonObject;
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
			return name = "Forecast";
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

	/**
	 * @return the temperature
	 */
	public String getTemperature() {
		return temperature;
	}

	/**
	 * @param temperature the temperature to set
	 */
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	/**
	 * @return the forecast
	 */
	public String getForecast() {
		return forecast;
	}

	/**
	 * @param forecast the forecast to set
	 */
	public void setForecast(String forecast) {
		this.forecast = forecast;
	}

	
}

