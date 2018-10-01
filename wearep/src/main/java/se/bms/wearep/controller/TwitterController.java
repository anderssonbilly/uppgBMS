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

public class TwitterController extends AnchorPane {
	@FXML
	protected Pane twitter;
	
	@FXML
	public void initialize() {
		// if twitter logged in, change to tweet view
	}
	
	@FXML
	public void twitterLogin() throws IOException {
		// create new twitter object
		// generate url
		// change to url page
		changeToBrowser("http://www.google.se/"); // for tests
	}

	@FXML
	public void enterPin(ActionEvent event) {
		String pin = ((TextField) ((Node)event.getSource()).getParent().getScene().lookup("#pin")).getText();
		authenticate(pin);
		// authenticate with the pin
	}
	
	private void authenticate(String pin) {
		// Check if successfull
		// if true, go to tweets page
		// if false go back to login
	}
	
	private void changeToBrowser(String url) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/webview.fxml"));
		
		twitter.getChildren().setAll((Parent)loader.load());
		
		WebViewController webView = loader.getController();
		webView.changeWebpage(url); // should be the twitter login url
		webView.setControls(new FXMLLoader(getClass().getResource("../view/logincon.fxml")));
	}
	
}
