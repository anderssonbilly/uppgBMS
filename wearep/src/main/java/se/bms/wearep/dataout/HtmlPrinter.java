package se.bms.wearep.dataout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import com.google.gson.JsonArray;

public class HtmlPrinter {
	
	public HtmlPrinter() {
		
	}
	
	WeatherMessage weatherMessage = new WeatherMessage();
	
	

	// Method to write to file
	protected void printToFile(String message) {
		FileOutputStream weatherOutfile = null;
		File outputFile = new File("weatherOutfile.html");
		try {
			outputFile.createNewFile();

			weatherOutfile = new FileOutputStream(outputFile, true);
			
			weatherOutfile.write(message.getBytes());
			weatherOutfile.close();
			
		} catch (FileNotFoundException fileNotFound) {
			System.err.println("File not found " + fileNotFound.getMessage());
		} catch (IOException ioExc) {
			System.err.println("IO exception error " + ioExc.getMessage());
		}
	
	}

	
	
	// create html page
	public void createWeatherHtmlPage(String pageTitle, String location, JsonArray weatherForecast) {
		String message = weatherMessage.createMessageHtmlPageSetup(pageTitle);
		printToFile(message);
		message = "<h1>Forecast for " + location + "</h1>";
		printToFile(message);
		weatherMessage.createMessageFromJsonArray(weatherForecast);
		message = weatherMessage.createMessageHtmlPageEnding();
		printToFile(message);
	}
}


