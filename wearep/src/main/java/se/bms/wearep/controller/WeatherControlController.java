package se.bms.wearep.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import se.bms.wearep.observer.AuthorizationObserver;

public class WeatherControlController extends AuthorizationObserver{
	
	@FXML
	protected Button tweetWeatherBtn;
	
	private boolean authorized;
	
	@FXML
	public void initialize() {
		TwitterController.getTwitter().addObserver(this);
		toggleTweetBtn();
	}
	
	@FXML
	public void tweetWeather(ActionEvent event) {
		// tweet the current weather
		if (TwitterController.getTwitter().isAuthorized()) {
			System.out.println("Tweeting weather");
			((Button) event.getSource()).disableProperty().set(true);
			((Label) ((Node) event.getSource()).getParent().getScene().lookup("#status")).setText("tweeting...");
		} else
			System.err.println("Not authorized");
	}
	
	private void toggleTweetBtn() {
		if(this.authorized && tweetWeatherBtn != null)
			tweetWeatherBtn.disableProperty().set(false);
		else if(!this.authorized && tweetWeatherBtn != null)
			tweetWeatherBtn.disableProperty().set(true);
	}
	
	@Override
	public void update(boolean isAuthorized) {
		System.out.println("Authorized: " + isAuthorized);
		this.authorized = isAuthorized;
		toggleTweetBtn();
	}
}
