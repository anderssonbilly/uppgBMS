package se.bms.wearep.dataout;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class HtmlPrinter {
	
	public HtmlPrinter() {
		
	}
	
	WeatherMessage weatherMessage = new WeatherMessage();
	
	

	// Method to write to file
	public void printToFile(String message) {
		FileOutputStream weatherOutfile = null;
		try {
			//TODO change to appropriate path
			weatherOutfile = new FileOutputStream("C:/Users/gospe/Documents/Ska_till_NAS/Systemintegratör/Datakommunikation_och_nätverk/weatherOutfile.html", true);
			
			//TODO need to update path
			/*
			Path currentRelativePath = Paths.get("");
			String s = currentRelativePath.toAbsolutePath().toString();
			System.out.println(s);
			weatherOutfile = new FileOutputStream(s, true);
		*/
			
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


