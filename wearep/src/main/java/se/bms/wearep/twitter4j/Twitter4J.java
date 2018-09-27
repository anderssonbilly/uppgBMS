package se.bms.wearep.twitter4j;

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
	
	public Twitter4J() {	
		this.twitter = TwitterFactory.getSingleton();	
		this.requestToken = getRequestToken();
		
		this.auth = new Authorization();
	}
	
	public boolean authorize(String pin) {
		this.accessToken = auth.validatePin(pin, requestToken);
		
		return (accessToken != null) ? true : false;
	}
	
	public String getAuthURL() {
		return requestToken.getAuthenticationURL();
	}
	
	private RequestToken getRequestToken() {
		try {
			return twitter.getOAuthRequestToken();
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean authorized() {
		return twitter.getAuthorization().isEnabled();
	}
}
