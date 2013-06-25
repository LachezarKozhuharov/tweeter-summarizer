package model.twitterclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;

import model.common.ConfigurationAccess;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.google.api.client.auth.oauth.OAuthHmacSigner;
import com.google.api.client.auth.oauth.OAuthParameters;
import com.google.api.client.http.GenericUrl;

public class TwitterClient {

	private static final String OAUTH_VERSION_10 = "1.0";

	public void sampleStatusesStreaming(TweetCallback callback) {
		HttpClient httpclient = new DefaultHttpClient();
		String uri = TwitterApiUrls.Streaming.SAMPLE_STATUSES + "?language=en";
		HttpGet httpGet = new HttpGet(uri);
		String authToken = getAuthorizationHeader(httpGet.getMethod(), uri);
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

	public void filterStatusesStreaming(TweetCallback callback, String track,
			long listenPeriod) throws UnsupportedEncodingException {
		//TODO use http timeouts
		
		long startTime = System.currentTimeMillis();
		long endTime = startTime + listenPeriod;

		HttpClient httpclient = new DefaultHttpClient();
		String uri = TwitterApiUrls.Streaming.FILTER_STATUSES;

		HttpPost httpPost = new HttpPost(uri);

		List<NameValuePair> pairs = new LinkedList<>();
		pairs.add(new BasicNameValuePair("track", track));
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(pairs, "UTF-8");

		httpPost.setEntity(entity);
		System.out.println(httpPost.getMethod() + "  " + uri);
		String authToken = getAuthorizationHeader(httpPost.getMethod(), uri,
				pairs.get(0));
		System.out.println(authToken);
		httpPost.setHeader("Authorization", authToken);

		try {
			HttpResponse response = httpclient.execute(httpPost);
			InputStream is = response.getEntity().getContent();

			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line;
			while ((line = br.readLine()) != null) {
				callback.onTweet(line);
				if (System.currentTimeMillis() > endTime) {
					return;
				}
			}
		} catch (IOException e) {
			httpPost.releaseConnection();
		}
	}

	public String getAvailableTrends() {
		HttpClient httpclient = new DefaultHttpClient();
		String url = TwitterApiUrls.Rest.AVAILABLE_TRENDS;
		HttpGet httpGet = new HttpGet(url);
		String authToken = getAuthorizationHeader(httpGet.getMethod(), url);
		httpGet.setHeader("Authorization", authToken);

		try {
			HttpResponse response = httpclient.execute(httpGet);
			InputStream is = response.getEntity().getContent();

			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
				return line;
			}
		} catch (IOException e) {
			httpGet.releaseConnection();
		}

		return null;
	}

	public List<String> getPlaceTrends(String woeid) {
		HttpClient httpclient = new DefaultHttpClient();
		String url = TwitterApiUrls.Rest.PLACE_TRENDS + "?id=" + woeid;
		HttpGet httpGet = new HttpGet(url);
		String authToken = getAuthorizationHeader(httpGet.getMethod(), url);
		httpGet.setHeader("Authorization", authToken);

		try {
			HttpResponse response = httpclient.execute(httpGet);
			InputStream is = response.getEntity().getContent();

			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line;
			while ((line = br.readLine()) != null) {

				//TODO parse result and return proper value
				return null;
			}
		} catch (IOException e) {
			httpGet.releaseConnection();
		}

		return null;

	}

	private String getAuthorizationHeader(String httpMethod, String url,
			NameValuePair... pairs) {
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
			GenericUrl genericUrl = new GenericUrl(url);
			if (pairs != null) {
				for (NameValuePair pair : pairs) {
					genericUrl.put(pair.getName(), pair.getValue());
				}
			}
			p.computeSignature(httpMethod, genericUrl);

		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
		return p.getAuthorizationHeader();
	}
}
