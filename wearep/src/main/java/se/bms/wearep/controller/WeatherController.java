package se.bms.wearep.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class WeatherController{

	@FXML
	protected Pane weather;

	@FXML
	protected TextField city;
	
	@FXML
	public void getWeather(ActionEvent event) throws IOException {
		System.out.println("City: " + city.getText());
		getWeatherData(getCoords(city.getText()));
		// when data is saved to disk
		// show data in html file in the webview
		changeToBrowser("http://www.smhi.se/");
	}



	private Double[] getCoords(String city) {
		// get coords from city
		return null;
	}

	private void getWeatherData(Double[] coords) {
		// get weather data from coords
	}

	private void saveData() {
		// save the data to disk
	}

	private void changeToBrowser(String url) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/webview.fxml"));

		weather.getChildren().setAll((Parent) loader.load());

		WebViewController webView = loader.getController();
		webView.changeWebpage(url);
		webView.setControls(new FXMLLoader(getClass().getResource("../view/weathercon.fxml")));
	}

}
