package model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Tweet {
	private long id;
	private String text;
	private String trend;

	public long getTwitterId() {
		return id;
	}

	public String getText() {
		return text;
	}

	public String getTrend() {
		return trend;
	}

	public void setTrend(String trend) {
		this.trend = trend;
	}
}
