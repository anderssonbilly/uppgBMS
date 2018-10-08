package se.bms.wearep.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import se.bms.wearep.observer.TwitterObserver;
import se.bms.wearep.twitter4j.Twitter4J;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.User;

public class TweetController extends TwitterObserver {

	@FXML
	protected TextArea tweet;

	@FXML
	protected Label characters;

	@FXML
	protected VBox tweetsBox;

	private boolean authorized;
	protected ResponseList<Status> tweets;
	protected Twitter4J twitter;

	@FXML
	public void initialize() {
		TwitterController.getTwitter().addObserver(this);
		twitter = TwitterController.getTwitter();
		setListeners();

		if (authorized)
			loopTweets();

		System.out.println(tweetsBox.getParent().getParent().getParent());
	}

	@FXML
	protected void tweet(ActionEvent event) throws IOException {
		// TODO Display error to user
		String msg = tweet.getText();
		if (authorized) {
			if(twitter.tweet(msg)) {
				tweet.setText("");
				characters.setText("0/140");
				loopTweets();}
		}
	}

	@FXML
	protected void checkCharacters(KeyEvent event) {
		String chars = characters.getText();
		chars = tweet.getLength() + chars.substring(characters.getText().indexOf("/"), characters.getText().length());
		characters.setText(chars);
	}

	private void setListeners() {
		tweet.setTextFormatter(
				new TextFormatter<String>(change -> change.getControlNewText().length() <= 140 ? change : null));
	}

	private void loopTweets() {
		tweetsBox.getChildren().clear();
		this.tweets = twitter.getTweets();
		for (Status status : tweets) {
			buildTweet(status);
		}
	}

	private void buildTweet(Status status) {
		VBox vBox = new VBox();
		vBox.setPrefSize(800, 100);
		vBox.setPadding(new Insets(0, 150, 0, 150));

		HBox nameBox = new HBox();
		nameBox.setAlignment(Pos.CENTER_LEFT);
		nameBox.setPrefSize(500, 50);

		VBox messageBox = new VBox();
		messageBox.setPrefSize(500, 35);

		HBox dateBox = new HBox();
		dateBox.setAlignment(Pos.CENTER_RIGHT);
		dateBox.setPrefSize(500, 15);

		User user = null;
		if (status.isRetweet())
			user = status.getRetweetedStatus().getUser();
		else
			user = status.getUser();

		Image image = new Image(user.getMiniProfileImageURL());
		ImageView imageView = new ImageView(image);
		Text name = new Text(user.getName() + ", " + user.getScreenName());
		nameBox.getChildren().addAll(imageView, name);

		Text message = new Text(status.getText());
		message.setWrappingWidth(500);
		messageBox.getChildren().add(message);

		Text date = new Text(status.getCreatedAt().toString());
		dateBox.getChildren().add(date);

		Separator separator = new Separator();

		vBox.getChildren().addAll(nameBox, messageBox, dateBox, separator);

		tweetsBox.getChildren().add(vBox);
	}
	
	@Override
	public void updateTwitter() {
		loopTweets();
	}
	
	@Override
	public void update(boolean isAuthorized) {
		System.out.println("Authorized: " + isAuthorized + ", showing tweets");
		this.authorized = isAuthorized;
	}
}
