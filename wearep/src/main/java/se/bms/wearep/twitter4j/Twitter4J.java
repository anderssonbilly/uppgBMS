package se.bms.wearep.twitter4j;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;

public class Twitter4J {
	private Twitter twitter;
	
	public Twitter4J() {	
	
	}
	
	public Twitter4J(String conKey, String conSec) {

	}
	
	private void setup() {
		twitter = TwitterFactory.getSingleton();

	}
	
	public boolean authorize(String pin) {
		return false;
	}
}
