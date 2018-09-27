package se.bms.wearep.weather;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class WeatherTest {

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

}
