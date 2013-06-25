package model.summarizer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class HybridTFIDFTextsSummarizer implements TextsSummarizer {
	private static int LENGTH_THRESHOLD = 20;
	private static double DISTANCE_THRESHOLD = 0.77;
	@Override
	public List<String[]> getSummaries(List<String[]> texts, int summariesCont) {
		Map<String, WordInfo> wordInfos = new HashMap<String, WordInfo>();
		int wordsInPosts = 0;
		for (String[] text : texts) {
			wordsInPosts += text.length;
			Set<String> wordsInText = new HashSet<String>();
			for (String word: text) {
				WordInfo info = null;
				if (wordInfos.containsKey(word)) {
					info = wordInfos.get(word);
				} else {
					info = new WordInfo(word);
				}
				if (!wordsInText.contains(text)) {
					info.setPostsCount(info.getPostsCount() + 1);
				}
				wordsInText.add(word);
				info.setWordOccurences(info.getWordOccurences() + 1);
			}
		}
		
		for (WordInfo wordInfo : wordInfos.values()) {
			calculateWeight(wordInfo, wordsInPosts, texts.size());
		}
		
		PriorityQueue<TextInfo> orderedTexts = new PriorityQueue<TextInfo>();
		for (String[] text: texts) {
			double[] wordWeights = new double[text.length];
			for (int i = 0; i < text.length; i++) {
				wordWeights[i] = wordInfos.get(text[i]).getWeight();
			}
			double textWeight = textWeight(wordWeights);
			orderedTexts.add(new TextInfo(text, textWeight));
		}
		
		List<String[]> textSummaries = new ArrayList<String[]>(summariesCont);
		int foundSummaries = 0;
		int summarizedIndex = 0;
		int textsCount = texts.size();
		String[] summaryCandidate = orderedTexts.poll().getText();
		while (foundSummaries < summariesCont && summarizedIndex < textsCount) {
			boolean isDifferent = true;
			for (String[] summary : textSummaries) {
				if (cosineDistance(summary, summaryCandidate) < DISTANCE_THRESHOLD) {
					isDifferent = false;
					break;
				}
			}
			if (isDifferent) {
				textSummaries.add(summaryCandidate);
				foundSummaries ++;
			}
			summarizedIndex++;
		}
		
		return textSummaries;
	}
	
	private void calculateWeight(WordInfo wordInfo, int wordsInAllPosts, int posts) {
		double tf = (double)wordInfo.getWordOccurences() / wordsInAllPosts;
		double idf = (double)posts / wordInfo.getPostsCount(); 
		double weight = tf * log(idf, 2);
		wordInfo.setWeight(weight);
	}
	
	private double log(double x, int base) {
	    return Math.log(x) / Math.log(base);
	}
	
	private double textWeight(double[] wordWeights) {
		double sum = 0;
		for (double wordWeight: wordWeights) {
			sum += wordWeight;
		}
		double lengthCoef = Math.max(wordWeights.length, LENGTH_THRESHOLD);
		return sum / lengthCoef;
	}
	
	private double cosineDistance(String[] text1, String[] text2) {
		Map<String, Integer[]> wordsOccurences = new HashMap<String, Integer[]>();
		for (String word: text1) {
			if (wordsOccurences.containsKey(text1)) {
				wordsOccurences.get(word)[0]++;
			} else {
				wordsOccurences.put(word, new Integer[]{1,0});
			}
		}
		for (String word: text2) {
			if (wordsOccurences.containsKey(text2)) {
				wordsOccurences.get(word)[1]++;
			} else {
				wordsOccurences.put(word, new Integer[]{0,1});
			}
		}
		Collection<Integer[]> vectorTuples = wordsOccurences.values();
		int text1Square = 0;
		int text2Square = 0;
		int vectorsProduct = 0;
		for (Integer[] tuple: vectorTuples) {
			text1Square += tuple[0] * tuple[0];
			text2Square += tuple[1] * tuple[1];
			vectorsProduct += tuple[0] * tuple[1];
		}
		double cosineDistance = vectorsProduct / (Math.sqrt(text1Square) + Math.sqrt(text2Square));
		return cosineDistance;
	}
}
