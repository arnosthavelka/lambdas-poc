package com.asseco.aha.poc.lambdas.streams;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.asseco.aha.poc.lambdas.LambdasPocApplication;

/**
 * @See https://github.com/JosePaumard/jdk8-lambda-tour/blob/master/src/org/paumard/jdk8/Scrabble.java
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = LambdasPocApplication.class)
public class ScrabbleWorldAnalyzerTest {

	private static final Logger LOG = LoggerFactory.getLogger(ScrabbleWorldAnalyzerTest.class);

	private static final int[] scrabbleENScore = {
			// a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z
			1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10 };

	private static final int[] scrabbleENDistribution = {
			// a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z
			9, 2, 2, 1, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1 };

	// histogram of the letters in a given word
	private Function<String, Map<Integer, Long>> lettersHisto = word -> word.chars().boxed()
			.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

	// score of a given word, taking into account that the given word
	// might contain blank letters
	Function<String, Integer> scoreWithBlanks = word -> lettersHisto
			.apply(word)
			.entrySet()
			.stream()
			// Map.Entry<letters, # used>
			.mapToInt(
					entry -> scrabbleENScore[entry.getKey() - 'a']
							* (int) Long.min(entry.getValue(), scrabbleENDistribution[entry.getKey() - 'a'])).sum();
	@Autowired
	private LineReader reader;

	@Test
	public void analyzeData() throws Exception {
		LOG.debug("Starting scrabble world analyzer ...");
		// parsing lines2dto
		try (Stream<String> lines = reader.read("ospd.txt")) {
			Set<String> words = lines.map(String::toLowerCase).collect(Collectors.toSet());
			LOG.info("No. of words in the Scrabble dictionnary={}", words.size());

			Map<Integer, List<String>> collect = words.stream().collect(Collectors.groupingBy(scoreWithBlanks));
			Optional<Entry<Integer, List<String>>> max = collect.entrySet().stream().max(Map.Entry.comparingByKey());
			LOG.info("The max score (from EN scrabble dictionary) is: {}", max.isPresent() ? max.get().getKey() : "N/A");
			LOG.info("The words with max score: {}", max.isPresent() ? max.get().getValue() : "N/A");

		}

		LOG.debug("Scrabble world analyzer finished.");
	}

}
