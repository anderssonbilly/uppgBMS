package se.bms.wearep.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyEvent;

public class TweetController {

	@FXML
	protected TextArea tweet;
	
	@FXML
	protected Label characters;
	
    @FXML
    public void initialize() {
        tweet.setTextFormatter(new TextFormatter<String>(change -> 
        	change.getControlNewText().length() <= 140 ? change : null));
    }

    @FXML
    protected void checkCharacters(KeyEvent event) {
    	String chars = characters.getText();
    	chars = tweet.getLength() + chars.substring(characters.getText().indexOf("/"), characters.getText().length());
    	characters.setText(chars);
    }
}
