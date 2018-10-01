package se.bms.wearep.dataout;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.gson.JsonObject;

public class HtmlPrinter {
	
	public HtmlPrinter() {
		
	}
	
	//create message to send
		public String createMessage(JsonObject selectedWeatherObject) {
		
			String validTime = selectedWeatherObject.get("validTime").getAsString();
			String temperature = selectedWeatherObject.get("temperature").getAsString();
			String forecast = selectedWeatherObject.get("forecast").getAsString();
			
			String message = "Forecast for "+ validTime + ": " + forecast + ". Temperature " + temperature + " degrees Celsius.";
			
			return message;
		}


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
			
		} catch (FileNotFoundException fileNotFound) {
			System.err.println("File not found " + fileNotFound.getMessage());
		} catch (IOException ioExc) {
			System.err.println("IO exception error " + ioExc.getMessage());
		}
	
	}

}


