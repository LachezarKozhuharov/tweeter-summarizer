package model.twitterclient;

import model.persistance.DbClient;

public class PersistTweetCallback implements TweetCallback {
	private DbClient dbClient;

	public PersistTweetCallback() {
		dbClient = new DbClient();
		dbClient.init();
	}

	@Override
	public void onTweet(String tweet) {
		dbClient.saveTweet(tweet);
	}
}
