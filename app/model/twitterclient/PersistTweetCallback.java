package model.twitterclient;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;

import model.Tweet;
import model.persistance.DbClient;

public class PersistTweetCallback implements TweetCallback {
	private DbClient dbClient;
	private ObjectMapper mapper;
	private String trend;

	public PersistTweetCallback(String trend) {
		this.trend = trend;
		mapper = new ObjectMapper();
		dbClient = new DbClient();
		dbClient.init();
	}

	@Override
	public void onTweet(String tweetString) {
		try {
			// TODO we have to remember the id value of the first tweet so later
			// we can retrieve tweets for the last X minutes for a specific
			// trend

			Tweet tweet = mapper.readValue(tweetString, Tweet.class);
			tweet.setTrend(trend);
			dbClient.saveTweet(tweet);
		} catch (IOException e) {
			System.out.println("Cannot map tweet: " + tweetString);
			return;
		}
	}
}
