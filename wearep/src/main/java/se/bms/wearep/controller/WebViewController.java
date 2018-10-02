package se.bms.wearep.controller;


import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class WebViewController {
	
	private WebView browser;
	private WebEngine webEngine;
	
	@FXML
	protected Pane webView;
	
	@FXML
	protected Pane control;
	
    @FXML
    public void initialize() {
    	this.browser = new WebView();
		this.webEngine = browser.getEngine();
		webView.getChildren().add(browser);
    }
	
	public void changeWebpage(String url) {
		this.webEngine.load(url);
	}

	public void setControls(FXMLLoader loader) throws IOException {
		control.getChildren().setAll((Parent)loader.load());
	}
}
