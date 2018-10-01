package se.bms.wearep.weather;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import se.bms.wearep.dataout.HtmlPrinter;

// Comments left for development and testing purposes in this version
// Class used to run Weather
public class WeatherDemo {
	
	public static void main(String[] args) {
		run();
	}

	public static void run() {
		
		Weather weather = new Weather();
		String indata=weather.getSMHIIndata();
		//workaround indata:
		//String indata = "{\"approvedTime\":\"2018-09-28T15:38:32Z\",\"referenceTime\":\"2018-09-28T15:00:00Z\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[[10.188115,68.394796]]},\"timeSeries\":[{\"validTime\":\"2018-09-28T16:00:00Z\",\"parameters\":[{\"name\":\"t\",\"levelType\":\"hl\",\"level\":0,\"unit\":\"Cel\",\"values\":[21]}]}]}";
		JsonObject jsonTree = weather.createJSONObjectFromSMHIdata(indata);
		JsonArray weatherForecast = weather.createJSONArrayOfMeasurements(jsonTree);
		WeatherSelector weatherSelector = new WeatherSelector();
		JsonObject jsonObjectTemp = weatherSelector.createTempObject(weatherForecast);
		JsonObject jsonObjectForecast = weatherSelector.createForecastObject(weatherForecast);
		JsonObject selectedWeatherObject = weatherSelector.createSelectedWeatherObject(jsonObjectTemp, jsonObjectForecast);
		HtmlPrinter printer = new HtmlPrinter();
		String message = printer.createMessage(selectedWeatherObject);
		printer.printToFile(message);
	
	//	String defaultUrl = "https://opendata-download-metfcst.smhi.se/api/category/pmp3g/version/2/geotype/point/lon/x/lat/y/data.json";
	//	Double [] coord = new Double[] {0.0, 0.0};
	//	coord [0] = 10.2;
	//	coord [1] = 68.4;
	//	weather.setCoordinatesInUrl(coord);
		//weather.setCoordinatesInUrl(defaultUrl, coord);
		
	}
}
