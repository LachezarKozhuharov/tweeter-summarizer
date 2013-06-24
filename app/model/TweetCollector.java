package model;

import model.tweeterclient.PersistTweetCallback;
import model.tweeterclient.TweetCallback;
import model.tweeterclient.TweeterClient;

public class TweetCollector implements Runnable {

	@Override
	public void run() {
		TweetCallback persistTweetCallback = new PersistTweetCallback();
		TweeterClient tweeterClient = new TweeterClient();

		tweeterClient.startClient(persistTweetCallback);
	}
}
