package com.github.aha.poc.lambdas.streams;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.aha.poc.lambdas.LambdasPocApplication;
import com.github.aha.poc.lambdas.streams.CityParser;
import com.github.aha.poc.lambdas.streams.CountryParser;
import com.github.aha.poc.lambdas.streams.dto.City;
import com.github.aha.poc.lambdas.streams.dto.Country;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LambdasPocApplication.class)
public class CityAnalyzerTest {

	private static final Logger LOG = LoggerFactory.getLogger(CityAnalyzerTest.class);

	@Autowired
	private CityParser parser;

	@Autowired
	private CountryParser countryParser;

	@Test
	public void analyzeData() throws Exception {
		LOG.debug("Starting city analyzer ...");
		
		// parsing cities
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
		Map<Integer, Map<String, Long>> statEntries = cities.stream().collect(Collectors.groupingBy(City::getRegion, Collectors.groupingBy(City::getCountryCode, Collectors.counting())));
		List<Entry<Integer, Map<String, Long>>> rsEntries = statEntries.entrySet().stream().limit(5).collect(Collectors.toList());
		LOG.info("Region (0-4) statistics:");
		for (Entry<Integer, Map<String, Long>> region : rsEntries) {
			Set<Entry<String, Long>> valueEntrySet = region.getValue().entrySet();
			long regionCityCount = valueEntrySet.stream().collect(Collectors.summarizingLong(Map.Entry::getValue)).getSum();
			LOG.info("\t{} [size={}]", region.getKey(), regionCityCount);
			for (Entry<String, Long> country : valueEntrySet) {
				LOG.info("\t\t{} ({}) [No. of cities={}]", getCountryName(countries, country.getKey()), country.getKey(), country.getValue());
			}

		}

		LOG.debug("City analyzer finished.");
	}

	private String getCountryName(List<Country> countries, String code) {
		Optional<Country> country = countries.stream().filter(s -> s.getCode().equals(code)).findFirst();
		return country.isPresent() ? country.get().getName() : "N/A";
	}
}
