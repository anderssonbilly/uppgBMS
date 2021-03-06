package se.bms.wearep.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import se.bms.wearep.observer.TwitterObserver;

public class WeatherControlController extends TwitterObserver {

	@FXML
	protected Button tweetWeatherBtn;

	private boolean authorized;
	private String weather;

	@FXML
	public void initialize() {
		TwitterController.getTwitter().addObserver(this);
		toggleTweetBtn();
	}

	@FXML
	public void tweetWeather(ActionEvent event) {
		if (TwitterController.getTwitter().isAuthorized()) {
			System.out.println("Tweeting weather");
			if (weather != null) {
				((Button) event.getSource()).disableProperty().set(true);
				Label status = ((Label) ((Node) event.getSource()).getParent().getScene().lookup("#status"));
				status.setText("tweeting...");
				if (TwitterController.getTwitter().tweet(weather)) {
					status.setText("Tweet successful");
					TwitterController.getTwitter().updateTweetList();
					tweetWeatherBtn.disableProperty().set(true);
				} else
					status.setText("Something went wrong, could not send tweet");
			}
		} else
			System.err.println("Not authorized");
	}

	private void toggleTweetBtn() {
		if (this.authorized && tweetWeatherBtn != null)
			tweetWeatherBtn.disableProperty().set(false);
		else if (!this.authorized && tweetWeatherBtn != null)
			tweetWeatherBtn.disableProperty().set(true);
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

	@Override
	public void update(boolean isAuthorized) {
		System.out.println("Authorized: " + isAuthorized);
		this.authorized = isAuthorized;
		toggleTweetBtn();
	}
}
