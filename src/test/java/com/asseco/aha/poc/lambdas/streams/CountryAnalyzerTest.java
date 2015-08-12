package com.asseco.aha.poc.lambdas.streams;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
import com.asseco.aha.poc.lambdas.streams.dto.Country;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = LambdasPocApplication.class)
public class CountryAnalyzerTest {

	private static final Logger LOG = LoggerFactory.getLogger(CountryAnalyzerTest.class);

	@Autowired
	private LineReader reader;

	@Autowired
	private CountryParser parser;

	@Test
	public void testExact() throws Exception {
		LOG.debug("Starting country analyzer ...");
		// loading file as stream
		try (Stream<String> lines = reader.read("iso3166.csv")) {
			// parsing lines2dto
			List<Country> countries = parser.parse(lines);
			LOG.info("No. of countries={}", countries.size());

			// filtering example (cities starting on "Co")
			List<Country> subList = countries.stream().filter(c -> c.getName().startsWith("Co")).collect(Collectors.toList());
			LOG.info("No. of countries starting on 'Co%'={}", subList.size());

			// grouping example (calculate countries by the first letter in their code)
			Map<String, Long> entries = countries.stream().collect(Collectors.groupingBy(s -> s.getCode().substring(0, 1), Collectors.counting()));
			Entry<String, Long> entry = entries.entrySet().stream().max(Map.Entry.comparingByValue()).get();
			LOG.info("Countries statistics: {}", entries);
			LOG.info("The most used starting letter is: {}", entry);
		}
		LOG.debug("Country analyzer Finished.");
	}

}
