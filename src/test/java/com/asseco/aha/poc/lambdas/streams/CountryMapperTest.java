package com.asseco.aha.poc.lambdas.streams;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.asseco.aha.poc.lambdas.LambdasPocApplication;
import com.asseco.aha.poc.lambdas.streams.dto.Country;

/**
 * Based on this article https://dzone.com/articles/Towards-More-Functional-Java-Using-Streams-and-Lambdas?edition=155254&utm_source=Weekly%20Digest&utm_medium=email&utm_campaign=wd%202016-08-31.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = LambdasPocApplication.class)
public class CountryMapperTest {

	private static final Logger LOG = LoggerFactory.getLogger(CountryMapperTest.class);
	
	private static final String DELIMITER = "=";
	
	private static final String AND = "\n";

	@Autowired
	private CountryParser parser;

	@Test
	public void analyzeData() throws Exception {
		LOG.debug("Starting country mapper ...");
		// parsing lines2dto
		List<Country> countries = parser.readFile("iso3166.csv");

		// calculate countries by the first letter in their code
		Map<String, Long> entries = countries.stream().collect(Collectors.groupingBy(s -> s.getCode().substring(0, 1), Collectors.counting()));
		// map countries to the output
		String value = entries.entrySet().stream().map(entry -> String.join(DELIMITER, entry.getKey(), entry.getValue().toString())).collect(Collectors.joining(AND));		
		LOG.info("Countries mapping:\n{}", value);
		
		LOG.debug("Country mapper finished.");
	}

}
