package model;

import java.io.UnsupportedEncodingException;

import model.twitterclient.PersistTweetCallback;
import model.twitterclient.TweetCallback;
import model.twitterclient.TwitterClient;

final class StreamingApiRunnable implements Runnable {
	private final String trend;

	StreamingApiRunnable(String trend) {
		this.trend = trend;
	}

	@Override
	public void run() {
		TwitterClient client = new TwitterClient();
		TweetCallback callback = new PersistTweetCallback(trend);
		
		try {
			client.filterStatusesStreaming(callback, trend, 1000 * 60 * 5);
		} catch (UnsupportedEncodingException e) {
			System.out.println("Error while listening for trend " + trend);
		}
	}
}