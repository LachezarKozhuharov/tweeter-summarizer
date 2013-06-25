package model;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import model.twitterclient.TwitterClient;

public class TweetCollector implements Runnable {
	private static String US_COUNTRY_WOEID = "23424977";

	private ExecutorService executor;

	public TweetCollector() {
		this.executor = Executors.newFixedThreadPool(10);
	}

	@Override
	public void run() {
		TwitterClient client = new TwitterClient();
		List<String> trends = client.getPlaceTrends(US_COUNTRY_WOEID);
		for (final String trend : trends) {
			executor.execute(new StreamingApiRunnable(trend));
		}
	}
}
