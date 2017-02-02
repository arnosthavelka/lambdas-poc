package com.github.aha.poc.lambdas.streams;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.aha.poc.lambdas.LambdasPocApplication;
import com.github.aha.poc.lambdas.streams.CountryParser;
import com.github.aha.poc.lambdas.streams.dto.Country;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LambdasPocApplication.class)
public class CountryAnalyzerTest {

	private static final Logger LOG = LoggerFactory.getLogger(CountryAnalyzerTest.class);

	@Autowired
	private CountryParser parser;

	@Test
	public void analyzeData() throws Exception {
		LOG.debug("Starting country analyzer ...");
		// parsing lines2dto
		List<Country> countries = parser.readFile("iso3166.csv");
		LOG.info("No. of countries={}", countries.size());

		// filtering example (cities starting on "Co")
		List<Country> subList = countries.stream().filter(c -> c.getName().startsWith("Co")).collect(Collectors.toList());
		LOG.info("No. of countries starting on 'Co%'={}", subList.size());

		// grouping example (calculate countries by the first letter in their code)
		Map<String, Long> entries = countries.stream().collect(Collectors.groupingBy(s -> s.getCode().substring(0, 1), Collectors.counting()));
		LOG.info("Countries statistics: {}", entries);
		// pick the max value
		Entry<String, Long> entry = entries.entrySet().stream().max(Map.Entry.comparingByValue()).get();
		LOG.info("The most used starting letter is: {}", entry);
		// sort by value and pick the top 5
		Comparator<Entry<String, Long>> comparator = Comparator.comparingLong(Map.Entry::getValue);
		List<Entry<String, Long>> topEntries = entries.entrySet().stream().sorted(comparator.reversed()).limit(5).collect(Collectors.toList());
		LOG.info("The top 5 starting letters are: {}", topEntries);

		LOG.debug("Country analyzer finished.");
	}

}
