/*
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
		HtmlPrinter printer = new HtmlPrinter();
		String pageTitle = "Weather forecast";
		printer.createWeatherHtmlPage(pageTitle, weatherForecast);
	//TODO	String message = printer.createMessageFromJsonArray(weatherForecast);
	//TODO3	printer.printToFile(message);
		//TODO2WeatherSelector weatherSelector = new WeatherSelector();
		//TODO2JsonObject jsonObjectTemp = weatherSelector.createTempObject(weatherForecast);
		//TODO2JsonObject jsonObjectForecast = weatherSelector.createForecastObject(weatherForecast);
		//TODO2 JsonObject selectedWeatherObject = weatherSelector.createSelectedWeatherObject(jsonObjectTemp, jsonObjectForecast);
		//TODO HtmlPrinter printer = new HtmlPrinter();
		//TODO String message = printer.createMessage(selectedWeatherObject);
		//TODO printer.printToFile(message);
		
		// alternative way with more general method to create message
		HtmlPrinter printer = new HtmlPrinter();
		String origin = "location";
		boolean header = true;
		String[] variables = {"Uddevalla"};
		String messageTemplate = printer.createMessageTemplate(origin, header);
		String message = printer.createMessageGeneral(selectedWeatherObject, variables, messageTemplate);
		printer.printToFile(message);
		origin = "weather";
		header = false;
		//variables = {"validTime", "forecast","temperature"};
		variables[0] = "validTime";
		variables[1] = "validTime";
		variables[2] = "validTime";
		//String messageTemplate = "Forecast for v0: v1. Temperature v2 degrees Celsius.";
		message = printer.createMessageGeneral(selectedWeatherObject, variables, messageTemplate);
		printer.printToFile(message);
		//testing until here
	
	//	String defaultUrl = "https://opendata-download-metfcst.smhi.se/api/category/pmp3g/version/2/geotype/point/lon/x/lat/y/data.json";
	//	Double [] coord = new Double[] {0.0, 0.0};
	//	coord [0] = 10.2;
	//	coord [1] = 68.4;
	//	weather.setCoordinatesInUrl(coord);
		//weather.setCoordinatesInUrl(defaultUrl, coord);
		
	}
}
*/