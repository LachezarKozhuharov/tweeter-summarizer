package model.persistance;

import java.net.UnknownHostException;

import model.common.ConfigurationAccess;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class DbClient {
	private static final String TWEET_COLLECTION = "tweets";

	private DB db;

	public void init() {
		try {
			MongoClient mongoClient = new MongoClient();
			db = mongoClient.getDB(ConfigurationAccess.getDBName());
		} catch (UnknownHostException e) {
			throw new RuntimeException("Unable to connect to database", e);
		}

	}

	public void saveTweet(String tweet) {
		DBCollection collection = db.getCollection(TWEET_COLLECTION);
		collection.save(new BasicDBObject("json", tweet));
	}
}
