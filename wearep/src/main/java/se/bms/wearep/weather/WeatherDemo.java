package se.bms.wearep.weather;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

// Comments left for development and testing purposes in this version
// Class used to run Weather
public class WeatherDemo {
	
	public static void main(String[] args) {
		run();
	}

	public static void run() {
		Weather weather = new Weather();
	//	String defaultUrl = "https://opendata-download-metfcst.smhi.se/api/category/pmp3g/version/2/geotype/point/lon/x/lat/y/data.json";
	//	Double [] coord = new Double[] {0.0, 0.0};
	//	coord [0] = 10.2;
	//	coord [1] = 68.4;
	//	weather.setCoordinatesInUrl(coord);
		//weather.setCoordinatesInUrl(defaultUrl, coord);
		String indata=weather.getSMHIIndata();
		JsonObject jsonTree = weather.createJSONObjectFromSMHIdata(indata);
		//weather.returnForecastStartTime(jsonTree);
		//returnForecastApprovedTime(jsonTree);
		//JsonArray timeSeries = createTimeSeriesJsonArrayFromJSonObject(jsonTree);
	JsonArray weatherForecast = weather.createJSONArrayOfMeasurements(jsonTree);
		//JsonArray parameters = createParametersJsonArray(timeSeries);
		//createResultsJsonArray(parameters);
		//createJSONObject(indata);
		//getSMHIIndata();
	//	System.out.println(weatherForecast);
	//	String name="temperature";
	//	String value="21";
	//	String unit = "degrees";
	//	String approvedTime = "20180519";
	//	String forecastStartTime = "20180518";
	//	String validTime = "20180520";
		//createJson(validTime, name, value, unit, approvedTime, forecastStartTime);
	//	createJsonWeatherObject(validTime, name, value, unit, approvedTime,forecastStartTime);
		
	}
}
