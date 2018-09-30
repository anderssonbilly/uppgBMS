package se.bms.wearep;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import se.bms.wearep.twitter4j.Twitter4J;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.User;

public class Launcher {

	public static void main(String[] args) throws IOException {
		Twitter4J twitter = new Twitter4J();

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		while (true) {
			switch (in.readLine()) {
			case "auth":
				System.out.println("Pin: ");
				String pin = in.readLine();
				System.out.println("entered pin: " + pin);
				System.out.println(twitter.authorize(pin));
				break;
			case "url":
				System.out.println(twitter.getAuthURL());
				break;
			case "isauth":
				System.out.println(twitter.isAuthorized());
				break;
			case "tweet":
				System.out.print("Msg: ");
				String msg = in.readLine();
				System.out.println(twitter.tweet(msg));
				break;
			case "get":
				ResponseList<Status> tweets = twitter.getTweets();
				if (tweets != null) {
					for (Status status : tweets) {
						User user;
						if (status.isRetweet())
							user = status.getRetweetedStatus().getUser();
						else
							user = status.getUser();
						
						System.out.println("------");
						System.out.println("Name: " + user.getName() + ", " + user.getScreenName());
						System.out.println("Msg: " + status.getText());
						System.out.println("Date: " + status.getCreatedAt());
						System.out.println("------");
					}
				}
				break;
			default:
				break;
			}
		}
	}

}
