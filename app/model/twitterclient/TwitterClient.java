package model.twitterclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;

import model.common.ConfigurationAccess;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.api.client.auth.oauth.OAuthHmacSigner;
import com.google.api.client.auth.oauth.OAuthParameters;
import com.google.api.client.http.GenericUrl;

public class TwitterClient {

	private static final String STREAMING_API_URI = "https://stream.twitter.com/1.1/statuses/sample.json?language=en";
	private static final String OAUTH_VERSION_10 = "1.0";

	public void startClient(TweetCallback callback) {
		HttpClient httpclient = new DefaultHttpClient();
		String uri = STREAMING_API_URI;
		HttpGet httpGet = new HttpGet(uri);
		String authToken = getAuthorizationHeader(httpGet.getMethod(), uri);
		System.out.println(authToken);
		httpGet.setHeader("Authorization", authToken);

		try {
			HttpResponse response = httpclient.execute(httpGet);
			InputStream is = response.getEntity().getContent();

			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
				callback.onTweet(line);
			}
		} catch (IOException e) {
			httpGet.releaseConnection();
		}

	}

	private String getAuthorizationHeader(String httpMethod,
			String streamingAddress) {
		OAuthHmacSigner s = new OAuthHmacSigner();
		s.clientSharedSecret = ConfigurationAccess.getApplicationSecret();
		s.tokenSharedSecret = ConfigurationAccess.getAccessTokenSecret();

		OAuthParameters p = new OAuthParameters();
		p.consumerKey = ConfigurationAccess.getApplicationKey();

		p.token = ConfigurationAccess.getAccessToken();
		p.signer = s;
		p.version = OAUTH_VERSION_10;
		p.computeNonce();
		p.computeTimestamp();
		try {
			p.computeSignature(httpMethod, new GenericUrl(streamingAddress));
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
		return p.getAuthorizationHeader();
	}
}
