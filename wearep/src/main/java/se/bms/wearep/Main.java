package se.bms.wearep;

import se.bms.wearep.coords.GetCoords;
import se.bms.wearep.weather.*;
public class Main {

	public static void main(String[] args) {
		GetCoords getcoords = new GetCoords();
		getcoords.run("Sundsandvik");
		System.out.println(getcoords);
		String city = getcoords.getCity();
		Double lon = getcoords.getLongitude();
		Double lat = getcoords.getLatitude();
		System.out.println("Main prints city: "+ city + " lon: " + lon + " lat "+ lat);
		WeatherLauncher.run(city, lon, lat);

	}

}
