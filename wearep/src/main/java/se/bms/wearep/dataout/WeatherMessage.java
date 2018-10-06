package se.bms.wearep.dataout;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


public class WeatherMessage {

	
	public WeatherMessage() {
		
	}
	
	// create weather message to publish as html
		public String createMessageFromJsonArray(JsonArray weatherForecast) {
			HtmlPrinter htmlPrinter = new HtmlPrinter();
			JsonArray jsonArray = weatherForecast;
			String validTime = "";
			String message="";
			for(int i=0;i<jsonArray.size();i++) {
				JsonObject jsonObject = (JsonObject)jsonArray.get(i);
				validTime = jsonObject.get("validTime").getAsString();
				String name = jsonObject.get("name").getAsString();
				String value = jsonObject.get("value").getAsString();
				String unit = jsonObject.get("unit").getAsString();
		
				message = "<strong>" + validTime +": </strong>" + name +" " + value + " " + unit + "<br>";
				
				htmlPrinter.printToFile(message);
			} 
			return message;
		}
		
	//create twitter Weather message
		public String createMessage(String location, JsonObject selectedWeatherObject) {
		
			String validTime = selectedWeatherObject.get("validTime").getAsString();
			String temperature = selectedWeatherObject.get("temperature").getAsString();
			String forecast = selectedWeatherObject.get("forecast").getAsString();
			
			String message = "Forecast for " + location + " " + validTime + ": " + forecast + ". Temperature " + temperature + " degrees Celsius.";
			
			return message;
		}
		
		// create html page first part
		public String createMessageHtmlPageSetup(String pageTitle) { 
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
		
		// Currently not used
	/*
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
	*/
		
		// Currently not used
	/*
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
		
		*/
	

}
