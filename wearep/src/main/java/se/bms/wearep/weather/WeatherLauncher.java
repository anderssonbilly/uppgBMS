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
		printer.createWeatherHtmlPage(pageTitle, weatherForecast);
		
		
		
		//TODO add to get twitter part
		//printer.createMessage(selectedWeatherObject);
	}
}
