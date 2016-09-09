package com.asseco.aha.poc.lambdas.streams;

import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.asseco.aha.poc.lambdas.LambdasPocApplication;
import com.asseco.aha.poc.lambdas.streams.dto.Calory;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LambdasPocApplication.class)
public class CaloryAnalyzerTest {

	private static final Logger LOG = LoggerFactory.getLogger(CaloryAnalyzerTest.class);

	@Autowired
	private CaloryParser parser;

	private Predicate<Calory> isDriedUp50() {
		return p -> "Dried".equals(p.getMeasure()) && p.getCarbo() < 50;
	}

	public List<Calory> filterCalories(List<Calory> data, Predicate<Calory> predicate) {
		return data.stream().filter(predicate).collect(Collectors.<Calory> toList());
	}

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
		
		List<Calory> filterCalories = filterCalories(calories, isDriedUp50());
		LOG.info("The filtered calories by measure='Dried' and carbo < 50 are:");
		filterCalories.forEach(c -> LOG.info("\t- {}", c.getName()));

		LOG.debug("Calory analyzer finished.");
	}

}
