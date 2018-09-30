package se.bms.wearep.twitter4j;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class Tweet {
	public boolean tweet(String msg, Twitter twitter) {
		try {
			if(msg.length() >= 140)
				throw new TwitterException("Tweet to long");
			twitter.updateStatus(msg);
			return true;
		} catch (TwitterException e) {
			System.err.println("Tweet refused: " + e.getMessage());
			// e.printStackTrace();
			return false;
		}
	}
	
	public ResponseList<Status> getTweets(Twitter twitter){
		try {
			ResponseList<Status> tweets = twitter.getUserTimeline();
			return tweets;
		} catch (TwitterException e) {
			System.err.println("Could not get timeline: " + e.getMessage());
			// e.printStackTrace();
			return null;
		}
	}
}
