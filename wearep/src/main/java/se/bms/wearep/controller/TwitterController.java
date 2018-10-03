package se.bms.wearep.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import se.bms.wearep.twitter4j.Twitter4J;

public class TwitterController extends AnchorPane {
	@FXML
	protected Pane twitter;

	private static Twitter4J twitter4j;

	@FXML
	public void initialize() {
		if (twitter4j == null)
			twitter4j = new Twitter4J();
	}

	@FXML
	public void twitterLogin() throws IOException {
		changeToBrowser(twitter4j.getAuthURL());
	}

	@FXML
	public void enterPin(ActionEvent event) {
		String pin = ((TextField) ((Node) event.getSource()).getParent().getScene().lookup("#pin")).getText();
		System.out.println("Pin: " + pin);
		authenticate(pin);
	}

	private void authenticate(String pin) {
		if (twitter4j.authorize(pin)) {
			// change to show tweets
			System.out.println("Authentication sucess");
		} else {
			// give error message and display new login prompt
			System.out.println("Authentication failed");
		}
	}

	private void changeToBrowser(String url) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/webview.fxml"));

		twitter.getChildren().setAll((Parent) loader.load());

		WebViewController webView = loader.getController();
		webView.changeWebpage(url); // should be the twitter login url
		webView.setControls(new FXMLLoader(getClass().getResource("../view/logincon.fxml")));
	}

	public static Twitter4J getTwitter() {
		return twitter4j;
	}
}
