package se.bms.wearep.twitter4j;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class Authorization {
	
	public AccessToken validatePin(String pin, RequestToken requestToken, Twitter twitter) {		
		try {
			AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, pin);
			System.out.println("Access token recieved");
			return accessToken;
		} catch (TwitterException e) {
			System.err.println("Unable to recieve access token");
			// e.printStackTrace();
		}
		return null;
	}
	
}
