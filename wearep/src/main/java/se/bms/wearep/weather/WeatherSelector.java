
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
	
	//TODO kört fast
	 public JsonObject createSelectedWeatherObject(JsonObject jsonObjectTemp, JsonObject jsonObjectForecast) {
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
	 public JsonObject createTempObject(JsonArray weatherForecast) { //TODO change to array?
		 JsonObject jsonObject = null;
		 for(JsonElement element : weatherForecast) {
			 String validTime= null;
			 String forecast=null;
			 String temperature = null;
			 String unit = null;

			 if(element.getAsJsonObject().get("name").getAsString().equals("Air temperature")) {
			//  System.out.println("This is Air temperature " + element);
			  validTime = element.getAsJsonObject().get("validTime").getAsString();
			  temperature = element.getAsJsonObject().get("value").getAsString();
			//  unit = element.getAsJsonObject().get("unit").getAsString();
			 
			  Weather weather = new Weather(validTime, forecast, temperature);
			  System.out.println("Forecast for "+ validTime + " Temperature " + temperature + " degrees Celsius. ");
			  
			  String json = weatherSelected.createJsonStringFromWeather(weather);
			  jsonObject = weatherSelected.createJsonObjectFromJsonString(json);
			  break;	
			 } 
		 }
		System.out.println("Temp jsonobject created in getTempjson: " +jsonObject);
		return jsonObject;
	 }
	 
	 /*
	 //get temperature old
	 public JsonObject createTempObjectOld(JsonArray weatherForecast) {
		 JsonObject jsonObject = null;
		 for(JsonElement element : weatherForecast) {
			 String forecastFor= null;
			 String forecast=null;
			 String temperature = null;
			 String unit = null;
			
			 if(element.getAsJsonObject().get("name").getAsString().equals("Air temperature")) {
			//  System.out.println("This is Air temperature " + element);
			  forecastFor = element.getAsJsonObject().get("validTime").getAsString();
			  temperature = element.getAsJsonObject().get("value").getAsString();
			  unit = element.getAsJsonObject().get("unit").getAsString();
			  System.out.println("Forecast for "+ forecastFor + " Temperature " + temperature + " degrees Celsius. ");
			  jsonObject = element.getAsJsonObject();
			  //TODO just nu för många - se till att leverera bara minsta datumet på validTime
			  validTime = element.getAsJsonObject().get("validTime").getAsString();
			  forecast = element.getAsJsonObject().get("value").getAsString();
			  Weather weather = new Weather(validTime, forecast, temperature);
			  System.out.println("Forecast for "+ validTime + " Forecast " + forecast + ". ");
			  String json = weatherSelected.createJsonStringFromWeather(weather);
			  jsonObject = weatherSelected.createJsonObjectFromJsonString(json);
			 } 
		 }
		return jsonObject;
	 }
	 */
	 
	 //get forecast
	 public JsonObject createForecastObject(JsonArray weatherForecast) { //TODO change to array?
		 JsonObject jsonObject = null;
		 for(JsonElement element : weatherForecast) {
			 String  validTime= null;
			 String forecast=null;
			 String temperature = null;
			 String unit = null;
			
			 if(element.getAsJsonObject().get("name").getAsString().equals("Weather symbol")) {
				 
				validTime = element.getAsJsonObject().get("validTime").getAsString();
				forecast = element.getAsJsonObject().get("value").getAsString();
				Weather weather = new Weather(validTime, forecast, temperature);
				System.out.println("Forecast for "+ validTime + " Forecast " + forecast + ". ");

				// replaced by method below
			 // Gson gson = new Gson();
			  //String json = gson.toJson(weather);
			 
				String json = weatherSelected.createJsonStringFromWeather(weather);
				jsonObject = weatherSelected.createJsonObjectFromJsonString(json);
				
				//replaced by above method
				/*
				JsonParser parser = new JsonParser();
				try {
					jsonObject = (JsonObject) parser.parse(json);
					// System.out.println("Got a JsonObject");
					// System.out.println("This is a jsonObject: " + jsonObject);
				} catch (JsonSyntaxException e) {
					System.out.println("Something wrong with the JSON - you've got a JsonSyntaxException");
					System.out.println(e.toString());
				}
				*/
				break;
			}
		} 
		 System.out.println("Forecast jsonobject created in getForecastjson: " +jsonObject);
		 return jsonObject;
	}
	
	 
	/*
	public JsonArray selectWeatherVariables(JsonArray weatherForecast) {
		for (int i=0; i<weatherForecast.size(); i++) {
			
			
			
			Gson gson = new Gson();
			String json = ""+ weatherForecast.get(i);
			Weather weather = gson.fromJson(json, Weather.class);
			
			String name = weather.getName();
			String forecastFor = weather.getValidTime();
			String value = weather.getValue();
			System.out.println("Name: " + name);
			if(name.equals("Weather symbol")||name.equals("Air temperature")) {
				System.out.println("will create an object");
				if(name.equals("Weather symbol"))  { 
					System.out.println("Entering if!");
					name = "Forecast";
					System.out.println(name + " object created ");
					//break;
				}
				if(name.equals("Air temperature"))  { 
					System.out.println("Entering if!");
					name = "Temperature";
					String unit = " degrees " + weather.getUnit() + "cius";
					System.out.println(name + " object created ");
					//break;
				}
			//break;
			}
			else System.out.println(name +" No object");

        }
		return selectedWeatherArray;
	
	}
	*/
	//Method to create selected JSON object
			public JsonObject createSelectedJsonWeatherObject(String validTime, String forecast, String temperature) {
				Weather weather = new Weather(validTime, forecast, temperature);
				Gson gson = new Gson();
				String json = gson.toJson(weather);
				System.out.println("This is selected json: " + json); //for reference only
				JsonObject jsonObject = null;
				JsonParser parser = new JsonParser();
				try {
					jsonObject = (JsonObject) parser.parse(json);
					// System.out.println("Detta är ett jsonObject: " + jsonObject);
				} catch (JsonSyntaxException e) {
					System.out.println("Something wrong with the JSON - you've got a JsonSyntaxException");
					System.out.println(e.toString());
				}
				return jsonObject;
			}
}

/*
		public JsonArray selectWeatherVariables(JsonArray weatherForecast) {
        for (int i = 0; i < weatherForecast.size(); i++) {
        	Gson gson = new Gson();
			String json = ""+ weatherForecast.get(i);
			Weather weather = gson.fromJson(json, Weather.class);
			
			String name = weather.getName();
			System.out.println("Name: " + name);
			if (name =="Weather symbol") {
				String forecast = weather.getValue();
				String validTime = weather.getValidTime();
				String temperature = null;
				JsonObject jsonWeather1 = createSelectedJsonWeatherObject(validTime, forecast, temperature);
			 }
			if(name == "Air temperature")
			 {
				String temperature = weather.getValue();
				String forecast = null;
				String validTime = weather.getValidTime();
				JsonObject jsonWeather2 = createSelectedJsonWeatherObject(validTime, forecast, temperature);
			 }
			
				
				
				//skriv variablerna till fil
         
        }
		return selectedWeatherArray;
	*/
//  JsonObject weatherJsonObject = weatherForecast.get(i).getAsJsonObject();
    //   System.out.println(weatherForecast.get("name"));
   
//JsonArray jsonArray = weatherForecast.getAsJsonArray();

	//	for(int i=0;i<weatherForecast.size();i++) {
		//	JsonElement nameElement = weatherForecast.get(i);
			//name = nameElement.getAsString().get("name");
		//	String name = nameElement.getAsString();
			//JsonElement weatherJsonElement = (JsonElement)weatherForecast.get(i);
		//	Object object = weatherForecast.get(i);
		//	String name = (String) weatherForecast.toString();
			
		
		//	System.out.println("name is " + name);
			//if(name == "Weather Symbol" || name == "temperature" || name =="Wind direction"|| name =="Wind direction") {
	//			System.out.println("selected name is " + name);
				// get variables
			//	String validTime = (String) weatherJsonElement.toString();
			//	String approvedTime = (String) weatherJsonElement.toString();
				//String name = (String) weatherJsonElement.toString();
				//String name = (String) weatherJsonElement.toString();
				//String name = (String) weatherJsonElement.toString();
			//	JsonElement unitElement = ((JsonObject) weatherJsonElement).get("unit");
				//String unit = unitElement.getAsString();
				//JsonElement valueElement = ((JsonObject) weatherJsonElement).get("value");
				//String value = valueElement.getAsString();
				//JsonElement validTimeElement = ((JsonObject) weatherJsonElement).get("validTime");
				//String validTime = validTimeElement.getAsString();
			//	JsonElement approvedTimeElement = ((JsonObject) weatherJsonElement).get("approvedTime");
			//	String approvedTime = validTimeElement.getAsString();
				//JsonElement referenceTimeElement = ((JsonObject) weatherJsonElement).get("referenceTime");
			//	String referenceTime = referenceTimeElement.getAsString();
		//		System.out.println("name is " + name + "and unit is " + unit);
				// Create JSON with only selected weather variabels included
			//	String jsonWeather = createJsonWeatherObject(validTime, name, value, unit, approvedTime,forecastStartTime);
			//System.out.println("This is jsonWeather from big method: " + jsonWeather); // for reference only	
		//	weatherForecast.add(jsonWeather);
		//	}
	//	}
	//	return selectedWeatherArray;