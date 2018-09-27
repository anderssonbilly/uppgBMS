package se.bms.wearep;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import se.bms.wearep.twitter4j.Twitter4J;

public class Launcher {

	public static void main(String[] args) throws IOException {
		Twitter4J twitter = new Twitter4J();
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		while(true) {
			switch (in.readLine()) {
			case "auth":
				System.out.println("Pin: ");
				String pin = in.readLine();
				System.out.println("entered pin: " + pin);
				System.out.println(twitter.authorize(pin));
				break;
			case "url":
				System.out.println(twitter.getAuthURL());
			case "isauth":
				System.out.println(twitter.authorized());
			default:
				break;
			}
		}
	}

}
