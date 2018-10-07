package se.bms.wearep;

import java.io.File;

import se.bms.wearep.coords.GetCoords;
import se.bms.wearep.weather.*;
public class Main {

	public static void main(String[] args) {
		File outputFile = new File("weatherOutfile.html");
		outputFile.delete();
		GetCoords getcoords = new GetCoords();
		getcoords.run("Göteborg");
	//	System.out.println(getcoords);
		String city = getcoords.getCity();
		Double lon = getcoords.getLongitude();
		Double lat = getcoords.getLatitude();
	//	System.out.println("Main prints city: "+ city + " lon: " + lon + " lat "+ lat);
		WeatherLauncher weather = new WeatherLauncher();
		weather.run(city, lon, lat);
	}

}
