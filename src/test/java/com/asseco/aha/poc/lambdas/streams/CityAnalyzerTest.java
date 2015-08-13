package com.asseco.aha.poc.lambdas.streams;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
		Country country = countries.stream().filter(s -> s.getCode().equals(entry.getKey())).findFirst().get();
		LOG.info("The country with most cities is: {} ({}) [No of cities: {}]", country.getName(), entry.getKey(), entry.getValue());


		LOG.debug("City analyzer finished.");
	}

}
