package model;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TweetCollectorScheduler {

	private final ScheduledExecutorService scheduler;

	public TweetCollectorScheduler() {
		scheduler = Executors.newScheduledThreadPool(1);
	}

	public void start() {
		scheduler.scheduleAtFixedRate(new TweetCollector(), 0, 5,
				TimeUnit.MINUTES);
	}
}
