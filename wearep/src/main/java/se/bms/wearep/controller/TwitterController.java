package se.bms.wearep.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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
		changeToBrowser();
	}

	private void changeToBrowser() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/webview.fxml"));
		
		twitter.getChildren().setAll((Parent)loader.load());
		
		WebViewController webView = loader.getController();
		webView.changeWebpage("http://www.google.se/");
		webView.setControls(new FXMLLoader(getClass().getResource("../view/logincon.fxml")));
	}
	
	
}
