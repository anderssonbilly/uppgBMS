package se.bms.wearep.weather;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import se.bms.wearep.dataout.HtmlPrinter;
import se.bms.wearep.dataout.WeatherMessage;

public class WeatherLauncher {

	//public static void run() {
	public static void run(String city, Double lon, Double lat) {
		System.out.println("Weather.run gets: " + city + " " + lon + " " + lat); //for reference only
		
		Weather weather = new Weather();
		String indata=weather.getSMHIIndata(lon, lat);
		JsonObject jsonTree = weather.createJSONObjectFromSMHIdata(indata);
		JsonArray weatherForecast = weather.createJSONArrayOfMeasurements(jsonTree);
		HtmlPrinter printer = new HtmlPrinter();
		String pageTitle = "Weather forecast";
		String location = city;
		printer.createWeatherHtmlPage(pageTitle, location, weatherForecast);
		//for Twitter message:
		WeatherSelector weatherSelector = new WeatherSelector();
		JsonObject jsonObjectTemp = weatherSelector.createTempObject(weatherForecast);
		JsonObject jsonObjectForecast = weatherSelector.createForecastObject(weatherForecast);
		JsonObject selectedWeatherObject = weatherSelector.createSelectedWeatherObject(jsonObjectTemp, jsonObjectForecast);
		WeatherMessage weatherMessage = new WeatherMessage();
		String message = weatherMessage.createMessage(location, selectedWeatherObject);
		System.out.println(message);

	}
}
