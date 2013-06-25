package model.summarizer;

public class TextInfo implements Comparable<TextInfo> {
	private String[] text;
	private double weight;
	
	public TextInfo(String[] text, double weight) {
		this.text = text;
		this.weight = weight;
	}
	
	public String[] getText() {
		return text;
	}
	
	public void setText(String[] text) {
		this.text = text;
	}
	
	public double getWeight() {
		return weight;
	}
	
	public void setWeight(double weight) {
		this.weight = weight;
	}

	@Override
	public int compareTo(TextInfo o) {
		return (int)(this.getWeight() - o.getWeight());
	}
}
