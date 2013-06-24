package model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Tweet {
	private long id;
	private String text;

	public long getId() {
		return id;
	}

	public String getText() {
		return text;
	}
}
