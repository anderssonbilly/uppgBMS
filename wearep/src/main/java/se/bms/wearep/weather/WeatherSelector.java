
package se.bms.wearep.weather;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class WeatherSelector {
	JsonArray selectedWeatherArray;
	Weather weatherSelected = new Weather();

	 public JsonObject createSelectedWeatherObject(JsonObject jsonObjectTemp, JsonObject jsonObjectForecast) {
		 //TODO: add check that validTime is the same in both objects
		 String temperature = jsonObjectTemp.get("temperature").getAsString();
		 String validTime = jsonObjectForecast.get("validTime").getAsString();
		 String forecast = jsonObjectForecast.get("forecast").getAsString();
		 
		 Weather weather = new Weather(validTime, forecast, temperature);
		  System.out.println("Forecast for "+ validTime + " Temperature " + temperature + " degrees Celsius. ");
		  
		  String json = weatherSelected.createJsonStringFromWeather(weather);
		  JsonObject selectedWeatherObject = weatherSelected.createJsonObjectFromJsonString(json);

		  System.out.println("this is the selected weather object: " + selectedWeatherObject);
		return selectedWeatherObject;
		 }
	
	 
	//get temperature new
	 //TODO check that appropriate record is selected
	 public JsonObject createTempObject(JsonArray weatherForecast) { 
		 JsonObject jsonObject = null;
		 for(JsonElement element : weatherForecast) {
			 String validTime= null;
			 String forecast=null;
			 String temperature = null;
			// String unit = null;

			 if(element.getAsJsonObject().get("name").getAsString().equals("Air temperature")) {
			//  System.out.println("This is Air temperature " + element);
			  validTime = element.getAsJsonObject().get("validTime").getAsString();
			  temperature = element.getAsJsonObject().get("value").getAsString();
			//  unit = element.getAsJsonObject().get("unit").getAsString();
			 
			  Weather weather = new Weather(validTime, forecast, temperature);
			  System.out.println("Forecast for "+ validTime + " Temperature " + temperature + " degrees Celsius. ");
			  
			  String json = weatherSelected.createJsonStringFromWeather(weather);
			  jsonObject = weatherSelected.createJsonObjectFromJsonString(json);
			  break;	// will only keep first measurement
			 } 
		 }
		System.out.println("Temp jsonobject created in getTempjson: " +jsonObject);
		return jsonObject;
	 }
	 
	
	 
	 //get forecast
	//TODO check that appropriate record is selected
	 public JsonObject createForecastObject(JsonArray weatherForecast) { 
		 JsonObject jsonObject = null;
		 for(JsonElement element : weatherForecast) {
			 String  validTime= null;
			 String forecast=null;
			 String temperature = null;
			// String unit = null;
			
			 if(element.getAsJsonObject().get("name").getAsString().equals("Forecast")) {
				 
				validTime = element.getAsJsonObject().get("validTime").getAsString();
				forecast = element.getAsJsonObject().get("value").getAsString();
				Weather weather = new Weather(validTime, forecast, temperature);
				System.out.println("Forecast for "+ validTime + " Forecast " + forecast + ". ");
			 
				String json = weatherSelected.createJsonStringFromWeather(weather);
				jsonObject = weatherSelected.createJsonObjectFromJsonString(json);
				
				break;
			}
		} 
		 System.out.println("Forecast jsonobject created in getForecastjson: " +jsonObject);
		 return jsonObject;
	}
	
	//Method to create selected JSON object
			public JsonObject createSelectedJsonWeatherObject(String validTime, String forecast, String temperature) {
				Weather weather = new Weather(validTime, forecast, temperature);
				Gson gson = new Gson();
				String json = gson.toJson(weather);
			//	System.out.println("This is selected json: " + json); //for reference only
				JsonObject jsonObject = null;
				JsonParser parser = new JsonParser();
				try {
					jsonObject = (JsonObject) parser.parse(json);
					// System.out.println("This is a jsonObject: " + jsonObject);
				} catch (JsonSyntaxException e) {
					System.out.println("Something wrong with the JSON - you've got a JsonSyntaxException");
					System.out.println(e.toString());
				}
				return jsonObject;
			}
}

