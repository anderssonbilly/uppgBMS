package se.bms.wearep.weather;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import se.bms.wearep.dataout.HtmlPrinter;

public class WeatherLauncher {

	public static void main(String[] args) {
		run();
	}

	public static void run() {
		Weather weather = new Weather();
		String indata=weather.getSMHIIndata();
		JsonObject jsonTree = weather.createJSONObjectFromSMHIdata(indata);
		JsonArray weatherForecast = weather.createJSONArrayOfMeasurements(jsonTree);
		HtmlPrinter printer = new HtmlPrinter();
		String pageTitle = "Weather forecast";
		String location = "Uddevalla"; //TODO replace with below call to method to get location
		//String location = getCoords.cityToCoords;
		printer.createWeatherHtmlPage(pageTitle, location, weatherForecast);
		//for Twitter message
		WeatherSelector weatherSelector = new WeatherSelector();
		JsonObject jsonObjectTemp = weatherSelector.createTempObject(weatherForecast);
		JsonObject jsonObjectForecast = weatherSelector.createForecastObject(weatherForecast);
		JsonObject selectedWeatherObject = weatherSelector.createSelectedWeatherObject(jsonObjectTemp, jsonObjectForecast);
		String message = printer.createMessage(location, selectedWeatherObject);
		System.out.println(message);

	}
}
