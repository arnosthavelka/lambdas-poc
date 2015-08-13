package com.asseco.aha.poc.lambdas.streams;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.asseco.aha.poc.lambdas.LambdasPocApplication;
import com.asseco.aha.poc.lambdas.streams.dto.City;
import com.asseco.aha.poc.lambdas.streams.dto.Country;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = LambdasPocApplication.class)
public class CityAnalyzerTest {

	private static final Logger LOG = LoggerFactory.getLogger(CityAnalyzerTest.class);

	@Autowired
	private CityParser parser;

	@Autowired
	private CountryParser countryParser;

	@Test
	public void analyzeData() throws Exception {
		LOG.debug("Starting city analyzer ...");
		
		// parsing lines2dto
		List<City> cities = parser.readZip("worldcitiespop.zip", "worldcitiespop.txt");

		// reading countries for better logging
		List<Country> countries = countryParser.readFile("iso3166.csv");

		// calculate cities by countries
		Map<String, Long> entries = cities.stream().collect(Collectors.groupingBy(City::getCountryCode, Collectors.counting()));

		// pick the country with max cities
		Entry<String, Long> entry = entries.entrySet().stream().max(Map.Entry.comparingByValue()).get();
		LOG.info("The country with most cities is: {} ({}) [No of cities: {}]", getCountryName(countries, entry.getKey()), entry.getKey(), entry.getValue());
		// pick the top 10
		Comparator<Entry<String, Long>> comparator = Comparator.comparingLong(Map.Entry::getValue);
		List<Entry<String, Long>> topEntries = entries.entrySet().stream().sorted(comparator.reversed()).limit(10).collect(Collectors.toList());
		Function<? super Entry<String, Long>, ? extends String> keyMapper = k -> getCountryName(countries, k.getKey());
		BinaryOperator<Long> mergeFunction = (v1, v2) -> v1;
		// Map implementation must be defined to preserve sorting
		Map<String, Long> ttEntries = topEntries.stream().collect(Collectors.toMap(keyMapper, Map.Entry::getValue, mergeFunction, LinkedHashMap::new));
		LOG.info("The top 10 countries with most cities are: {}", ttEntries);

		// calculate cities in countries and regions
		// TODO

		LOG.debug("City analyzer finished.");
	}

	private String getCountryName(List<Country> countries, String code) {
		Country country = countries.stream().filter(s -> s.getCode().equals(code)).findFirst().get();
		return country.getName();
	}
}