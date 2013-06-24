package model;

import model.twitterclient.PersistTweetCallback;
import model.twitterclient.TweetCallback;
import model.twitterclient.TwitterClient;

public class TweetCollector implements Runnable {

	@Override
	public void run() {
		TweetCallback persistTweetCallback = new PersistTweetCallback();
		TwitterClient tweeterClient = new TwitterClient();

		tweeterClient.startClient(persistTweetCallback);
	}
}
