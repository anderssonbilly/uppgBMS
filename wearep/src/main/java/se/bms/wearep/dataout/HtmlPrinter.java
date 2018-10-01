package se.bms.wearep.dataout;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import se.bms.wearep.weather.Weather;

public class HtmlPrinter {
	
	public HtmlPrinter() {
		
	}
	
	public void createMessage(JsonArray weatherForecast) {
		System.out.println("Weather forcast is: " + weatherForecast);
		for (int i=0; i<weatherForecast.size(); i++) {
			Gson gson = new Gson();
			String json = ""+ weatherForecast.get(i);
			Weather weather = gson.fromJson(json, Weather.class);
			
			String name = weather.getName();
			String forecastFor = weather.getValidTime();
			String value = weather.getValue();
			System.out.println("Name: " + name);
			if(name.equals("Weather symbol"))  { 
				System.out.println("Entering if!");
				name = "Forecast";
				String message = forecastFor + " " + name + ": " + value;
				System.out.println(message); //for reference only
				printToFile(message);
			//	break;
			}
			if(name.equals("Air temperature"))  { 
				System.out.println("Entering if!");
				name = "Temperature";
				String unit = " degrees " + weather.getUnit() + "cius";
				String message = forecastFor + " " + name + ": " + value + unit;
				System.out.println(message); //for reference only
				printToFile(message);
			//	break;
			}
			else System.out.println(name +" No html created");
		}
		//return message;
	
	}
	

		
	// Method to write to file
	public void printToFile(String message) {
		FileOutputStream weatherOutfile = null;
		try {
			weatherOutfile = new FileOutputStream("C:/Users/gospe/Documents/Ska_till_NAS/Systemintegratör/Datakommunikation_och_nätverk/weatherOutfile.html", true);
			
			weatherOutfile.write(message.getBytes());
			
		} catch (FileNotFoundException fileNotFound) {
			System.err.println("File not found " + fileNotFound.getMessage());
		} catch (IOException ioExc) {
			System.err.println("IO exception error " + ioExc.getMessage());
		}
	
	}

}

		
	

