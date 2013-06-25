package model.summarizer;

public class WordInfo {
	private int wordOccurences = 0;
	private int postsCount = 0;
	private double weight = 0;
	private String word;
	
	public WordInfo(String word) {
		this.word = word;
	}
	
	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public int getWordOccurences() {
		return wordOccurences;
	}
	
	public void setWordOccurences(int wordOccurences) {
		this.wordOccurences = wordOccurences;
	}
	
	
	public int getPostsCount() {
		return postsCount;
	}
	
	public void setPostsCount(int postsCount) {
		this.postsCount = postsCount;
	}
}

