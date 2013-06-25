package model.summarizer;
import java.util.List;

public interface TextsSummarizer {
	public List<String[]> getSummaries(List<String[]> texts, int summariesCont);
}
