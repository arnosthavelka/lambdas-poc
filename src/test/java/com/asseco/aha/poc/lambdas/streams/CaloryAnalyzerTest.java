package com.asseco.aha.poc.lambdas.streams;

import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.asseco.aha.poc.lambdas.LambdasPocApplication;
import com.asseco.aha.poc.lambdas.streams.dto.Calory;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = LambdasPocApplication.class)
public class CaloryAnalyzerTest {

	private static final Logger LOG = LoggerFactory.getLogger(CaloryAnalyzerTest.class);

	@Autowired
	private CaloryParser parser;

	@Test
	public void analyzeData() throws Exception {
		LOG.debug("Starting calory analyzer ...");
		// parsing lines2dto
		List<Calory> calories = parser.readFile("calories.csv");
		LOG.info("No. of calories={}", calories.size());

		// find most used measure where carbo < 10
		Set<Entry<String, Long>> entries = calories.stream().filter(s -> s.getCarbo() < 10).collect(Collectors.groupingBy(Calory::getMeasure, Collectors.counting())).entrySet();
		Entry<String, Long> entry = entries.stream().max(Entry.comparingByValue()).get();
		LOG.info("The most used measure in food is: {}", entry);
		
		LOG.debug("Calory analyzer finished.");
	}

}
