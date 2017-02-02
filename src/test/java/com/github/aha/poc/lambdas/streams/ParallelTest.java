package com.github.aha.poc.lambdas.streams;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.aha.poc.lambdas.streams.dto.Country;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ParallelTest {

	private static final Logger LOG = LoggerFactory.getLogger(ParallelTest.class);

	@Autowired
	private CountryParser parser;

	@Test
	public void filterData() {
		LOG.debug("Starting filtering streaming ...");
		// parsing lines2dto
		List<Country> countries = parser.readFile("iso3166.csv");
		LOG.info("No. of total countries={}", countries.size());
		
		LOG.info("\nRunning regular stream ...");
		processData(countries.stream());
		LOG.info("\nRunning parallel stream ...");
		processData(countries.parallelStream());

		LOG.debug("Parallel filtering finished.");
	}

	private void processData(Stream<Country> stream) {
		long msStart = System.currentTimeMillis();
		// filter data
		List<Country> data = stream
				.filter(p -> p.getName().contains("au"))
				.sorted(Comparator.comparing(Country::getName).reversed())
				.collect(Collectors.toList());
		LOG.debug("No. of filtered countries={}", data.size());
		// print data
		LOG.info("Found countries (containng 'au') are:");
		data.stream().forEach(p -> LOG.info("{}\t{}", p.getCode(), p.getName()));
		// print spent time
		LOG.info("Time={} ms", System.currentTimeMillis() - msStart);
	}

	
	@Test
	public void matchData() throws Exception {
		LOG.debug("Starting matching streaming ...");
		// parsing lines2dto
		List<Country> countries = parser.readFile("iso3166.csv");
		LOG.info("No. of total countries={}", countries.size());
		
		long msStart = System.currentTimeMillis();
		LOG.info("allMatch={}", countries.parallelStream().allMatch(p -> p.getName().length() > 1));
		LOG.info("noneMatch={}", countries.parallelStream().noneMatch(p -> p.getName().indexOf("xyz") >= 0));
		LOG.info("anyMatch={}", countries.parallelStream().anyMatch(p -> p.getName().indexOf("au") > 0));
		LOG.info("Time={} ms", System.currentTimeMillis() - msStart);
		
		LOG.debug("Parallel matching finished.");		
	}
}
