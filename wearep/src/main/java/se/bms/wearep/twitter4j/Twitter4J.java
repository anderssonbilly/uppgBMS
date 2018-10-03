package se.bms.wearep.twitter4j;

import java.util.ArrayList;
import java.util.List;

import se.bms.wearep.observer.AuthorizationObserver;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class Twitter4J {
	private Twitter twitter;
	private RequestToken requestToken;
	private AccessToken accessToken;

	private Authorization auth;
	private Tweet tweet;

	private TwitterFactory tf;

	private final List<AuthorizationObserver> observers = new ArrayList<>();

	public Twitter4J() {
		this.tf = new TwitterFactory();

		this.auth = new Authorization();
		this.tweet = new Tweet();
	}

	public boolean authorize(String pin) {
		if (requestToken != null) {
			this.accessToken = auth.validatePin(pin, requestToken, twitter);
			if (accessToken != null) {
				updateObservers(true);
				return true;
			} else {
				updateObservers(false);
				return false;
			}
		} else {
			System.err.println("No request token found");
			updateObservers(false);
			return false;
		}

	}

	public String getAuthURL() {
		this.requestToken = getRequestToken();
		return (requestToken != null) ? requestToken.getAuthenticationURL() : "";
	}

	public boolean tweet(String msg) {
		if (isAuthorized())
			return (tweet.tweet(msg, twitter)) ? true : false;
		else {
			System.err.println("Not authorized");
			return false;
		}
	}

	public ResponseList<Status> getTweets() {
		if (isAuthorized())
			return tweet.getTweets(twitter);
		else {
			System.err.println("Not authorized");
			return null;
		}
	}

	private RequestToken getRequestToken() {
		this.twitter = tf.getInstance();
		try {
			return twitter.getOAuthRequestToken();
		} catch (TwitterException e) {
			System.err.println("Unable to recieve request token");
			// e.printStackTrace();
			return null;
		}
	}

	public boolean isAuthorized() {
		if (requestToken != null) {
			boolean a = twitter.getAuthorization().isEnabled();
			updateObservers(a);
			return a;
		} else {
			updateObservers(false);
			return false;
		}
	}

	public void addObserver(AuthorizationObserver o) {
		observers.add(o);
		isAuthorized();
	}

	private void updateObservers(boolean isAuthorized) {
		for (AuthorizationObserver observer : observers) {
			observer.update(isAuthorized);
		}
	}
}
