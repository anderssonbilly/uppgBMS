package se.bms.wearep.weather;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

class WeatherTest {

	// This test should be run first and needs to be green before any other test is run as other tests depend on this one
	@Test
	final void shouldTestgetSMHIIndataReturnsANewString() {
		// Arrange
			Weather weather = new Weather();
		// Act
			String expected = "";
			String actual = weather.getSMHIIndata();
		// Assert
			assertNotEquals(expected, actual);
			}

	// This test also needs to be green before any further tests are run as other tests depend on this one
	@Test
	final void testCreateJSONObjectFromSMHIdata() { // TODO better test with mocking
		// Arrange
		Weather weather = new Weather();
		String indata = weather.getSMHIIndata();
		// Act
		JsonObject jsonTree = weather.createJSONObjectFromSMHIdata(indata);
		//Assert
		assertTrue(jsonTree.isJsonObject());
	}


	@Test
	final void testReturnForecastStartTime() { // TODO "real" mocking
		// Arrange
		Weather weather = new Weather();
		String indata = "{\"approvedTime\":\"2018-09-28T15:38:32Z\",\"referenceTime\":\"2018-09-28T15:00:00Z\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[[10.188115,68.394796]]},\"timeSeries\":[{\"validTime\":\"2018-09-28T16:00:00Z\",\"parameters\":[{\"name\":\"spp\",\"levelType\":\"hl\",\"level\":0,\"unit\":\"percent\",\"values\":[-9]}]}]}";
		JsonObject jsonTree = weather.createJSONObjectFromSMHIdata(indata);
		// Act
		String expected = "\"2018-09-28T15:00:00Z\"";
		JsonElement actualJsonElement = weather.returnForecastStartTime(jsonTree);
		String actual = actualJsonElement.toString();
		//Assert
		assertEquals(expected, actual); 
	}


	@Test
	final void testReturnForecastApprovedTime() { // TODO "real" mocking
		// Arrange
		Weather weather = new Weather();
		String indata = "{\"approvedTime\":\"2018-09-28T15:38:32Z\",\"referenceTime\":\"2018-09-28T15:00:00Z\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[[10.188115,68.394796]]},\"timeSeries\":[{\"validTime\":\"2018-09-28T16:00:00Z\",\"parameters\":[{\"name\":\"spp\",\"levelType\":\"hl\",\"level\":0,\"unit\":\"percent\",\"values\":[-9]}]}]}";
		JsonObject jsonTree = weather.createJSONObjectFromSMHIdata(indata);
		// Act
		String expected = "\"2018-09-28T15:38:32Z\"";
		JsonElement actualJsonElement = weather.returnForecastApprovedTime(jsonTree);
		String actual = actualJsonElement.toString();
		//Assert
		assertEquals(expected, actual); 
	}


	@Test
	final void testCreateTimeSeriesJsonArrayFromJSonObject() { // TODO "real" mocking
		// Arrange
		Weather weather = new Weather();
		String indata = "{\"approvedTime\":\"2018-09-28T15:38:32Z\",\"referenceTime\":\"2018-09-28T15:00:00Z\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[[10.188115,68.394796]]},\"timeSeries\":[{\"validTime\":\"2018-09-28T16:00:00Z\",\"parameters\":[{\"name\":\"spp\",\"levelType\":\"hl\",\"level\":0,\"unit\":\"percent\",\"values\":[-9]}]}]}";
		JsonObject jsonTree = weather.createJSONObjectFromSMHIdata(indata);
		// Act
		JsonArray actual = weather.createTimeSeriesJsonArrayFromJSonObject(jsonTree);
		//Assert
		assertTrue(actual.isJsonArray());
	}


	@Test
	final void testCreateJSONArrayOfMeasurements() { // TODO better test with mocking
		// Arrange
		Weather weather = new Weather();
		String indata = weather.getSMHIIndata();
		JsonObject jsonTree = weather.createJSONObjectFromSMHIdata(indata);
		// Act
		JsonArray actual = weather.createJSONArrayOfMeasurements(jsonTree);
		//Assert
		assertTrue(actual.isJsonArray());
	}

	@Test
	final void testSetCoordinatesInUrl() { 
		// Arrange
		Weather weather = new Weather();
		Double [] coord = new Double[] {10.2, 68.4};
	// Act
		String expected = "https://opendata-download-metfcst.smhi.se/api/category/pmp3g/version/2/geotype/point/lon/10.2/lat/68.4/data.json";
		String actual = weather.setCoordinatesInUrl(coord);
	// Assert
		assertEquals(expected, actual);
	}
	

}
