package model.persistance;

import java.net.UnknownHostException;

import model.Tweet;
import model.common.ConfigurationAccess;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.sun.org.apache.bcel.internal.generic.NEW;

public class DbClient {
	public static final String TWEET_COLLECTION = "tweets";

	private DB db;

	public void init() {
		try {
			MongoClient mongoClient = new MongoClient();
			db = mongoClient.getDB(ConfigurationAccess.getDBName());
		} catch (UnknownHostException e) {
			throw new RuntimeException("Unable to connect to database", e);
		}

	}

	public void saveTweet(Tweet tweet) {
		DBCollection collection = db.getCollection(TWEET_COLLECTION);
		DBObject dbTweet = BasicDBObjectBuilder.start()
				.add("id", tweet.getTwitterId()).add("text", tweet.getText())
				.add("trend", tweet.getTrend()).get();
		collection.save(dbTweet);
	}

	public void getTweets(String trend, long startId) {
		DBCollection collection = db.getCollection(TWEET_COLLECTION);
		DBObject query = new BasicDBObject();
		query.put("$and", new BasicDBObject[] {
				new BasicDBObject("trend", trend),
				new BasicDBObject("id", new BasicDBObject("$gt", startId)) });
		DBCursor dbCursor = collection.find(query);
		//TODO return result
	}
}
