package se.bms.wearep.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class TwitterLoginControlController {
	
	@FXML
	protected Label loginInfo;

	@FXML
	protected Pane loginCon;
	
	@FXML
	public void enterPin(ActionEvent event) throws IOException {
		String pin = ((TextField) ((Node) event.getSource()).getParent().getScene().lookup("#pin")).getText();
		System.out.println("Pin: " + pin);
		authenticate(pin);
	}

	private void authenticate(String pin) throws IOException {
		if (TwitterController.getTwitter().authorize(pin)) {
			System.out.println("Authentication success");
			changeToTweets();
		} else {
			System.err.println("Authentication failed");
			reload();
		}
	}

	public void setLoginInfo(String info) {
		loginInfo.setText(info);
	}

	private void changeToTweets() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/tweet.fxml"));

		Pane parent = (Pane) loginCon.getParent().getParent().getParent();		
		parent.getChildren().set(0, loader.load());
	}
	
	private void reload() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/webview.fxml"));

		Pane parent = (Pane) loginCon.getParent().getParent().getParent();		
		parent.getChildren().set(0, loader.load());

		WebViewController webView = loader.getController();
		webView.changeWebpage(TwitterController.getTwitter().getAuthURL());
		loader = new FXMLLoader(getClass().getResource("../view/logincon.fxml"));
		webView.setControls(loader);
		((TwitterLoginControlController) loader.getController()).setLoginInfo("Authentication failed, please try again...");
	}
}
