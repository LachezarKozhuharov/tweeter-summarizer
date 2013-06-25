package model.twitterclient;

public class TwitterApiUrls {
	public class Streaming {
		private static final String STREAM_BASE = "https://stream.twitter.com/1.1/";
		public static final String SAMPLE_STATUSES = STREAM_BASE
				+ "statuses/sample.json";
		public static final String FILTER_STATUSES = STREAM_BASE
				+ "statuses/filter.json";
	}

	public class Rest {
		private static final String TRENDS_BASE = "https://api.twitter.com/1.1/trends/";
		public static final String AVAILABLE_TRENDS = TRENDS_BASE
				+ "available.json";
		public static final String PLACE_TRENDS = TRENDS_BASE + "place.json";
	}
}
