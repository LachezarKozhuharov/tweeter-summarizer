package model.common;

import play.Play;

/**
 * Provides access to twitter summarizer specific configuration in
 * application.conf file.
 */
public class ConfigurationAccess {

	/**
	 * The twitter application key property key.
	 */
	private static final String TWITTER_APP_KEY = "twitter.app.key";

	/**
	 * The twitter application secret property key.
	 */
	private static final String TWITTER_APP_SECRET = "twitter.app.secret";

	/**
	 * The twitter access token property key.
	 */
	private static final String TWITTER_APP_ACCESS_TOKEN = "twitter.app.accesstoken";

	/**
	 * The twitter access secret property key.
	 */
	private static final String TWITTER_APP_ACCESS_TOKEN_SECRET = "twitter.app.accesstokensecret";

	/**
	 * Retrieves the value of the application key property.
	 */
	public static String getApplicationKey() {
		return Play.application().configuration().getString(TWITTER_APP_KEY);
	}

	/**
	 * Retrieves the application secret property.
	 */
	public static String getApplicationSecret() {
		return Play.application().configuration().getString(TWITTER_APP_SECRET);
	}

	/**
	 * Retrieves the access token property.
	 */
	public static String getAccessToken() {
		return Play.application().configuration()
				.getString(TWITTER_APP_ACCESS_TOKEN);
	}

	/**
	 * Retrieves the access token secret property.
	 */
	public static String getAccessTokenSecret() {
		return Play.application().configuration()
				.getString(TWITTER_APP_ACCESS_TOKEN_SECRET);
	}
}
