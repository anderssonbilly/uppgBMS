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
	
	// create weather message to publish as html
	public String createMessageFromJsonArray(JsonArray weatherForecast) {
		JsonArray jsonArray = weatherForecast;
		String validTime = "";
		String message = "<h1>Forecast for Uddevalla</h1>";
		printToFile(message);
		for(int i=0;i<jsonArray.size();i++) {
			JsonObject jsonObject = (JsonObject)jsonArray.get(i);
			validTime = jsonObject.get("validTime").getAsString();
			String name = jsonObject.get("name").getAsString();
			String value = jsonObject.get("value").getAsString();
			String unit = jsonObject.get("unit").getAsString();
	
			message = "<strong>" + validTime +":</strong>" + name +" " + value + " " + unit + "<br>";
			
			printToFile(message);
		} 
		return message;
	}
	
	//create twitter Weather message
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
			weatherOutfile.close();
			
		} catch (FileNotFoundException fileNotFound) {
			System.err.println("File not found " + fileNotFound.getMessage());
		} catch (IOException ioExc) {
			System.err.println("IO exception error " + ioExc.getMessage());
		}
	
	}

	//General method to create message
	// messageTemplate should be formatted like "text" + v0 + "text" + v2 + v1 + "text"
	public String createMessageGeneral(JsonObject jsonobject, String[] variables, String messageTemplate) {
		String message = messageTemplate;
		for (int i=0; i< variables.length; i++) {
			String variableName = variables[i];
			variableName = jsonobject.get(variableName).getAsString();
			message = message.replace(("v"+i), variableName);
		}
			
		return message;
	}
	
	//create messageTemplate
	public String createMessageTemplate(String origin, boolean header) {
		String messageTemplate;
		if (origin=="WeatherTweet")
			messageTemplate = "v0: v1. Temperature v2 degrees Celsius.";
		//if (origin =="Twitter")
		//	messageTemplate = "Twitter v0: v1 TODO v2 Twitter";
		if (origin=="Location")
			messageTemplate = "Forecast for v0";
		else 
			messageTemplate = null;
		if (header)
			messageTemplate = "<h1>" + messageTemplate + "</h1>";
		return messageTemplate;
	}
	
	// create html page first part
	public String createMessageHtmlPageSetup(String pageTitle) { //TODO add String pageTitle to skapa metoden
		String message = "<html>\r\n" + 
				"	\r\n" + 
				"	<head>\r\n" + 
				"	\r\n" + 
				"		<title>" + pageTitle +"</title>\r\n" + 
				"\r\n" + 
				"	</head>\r\n" + 
				"\r\n" + 
				"	<body>";
		return message;
		
	}
	
	// create html page ending
	public String createMessageHtmlPageEnding() {
		String message = "</body>\r\n" + 
				"\r\n" + 
				"</html>";
		return message;
	}
	// create html page
	public void createWeatherHtmlPage(String pageTitle, JsonArray weatherForecast) {
		String message = createMessageHtmlPageSetup(pageTitle);
		printToFile(message);
		createMessageFromJsonArray(weatherForecast);
		message = createMessageHtmlPageEnding();
		printToFile(message);
	}
}


