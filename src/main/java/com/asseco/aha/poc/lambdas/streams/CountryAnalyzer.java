package com.asseco.aha.poc.lambdas.streams;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.asseco.aha.poc.lambdas.streams.dto.Country;

@Component
public class CountryAnalyzer {

	private static final Logger LOG = LoggerFactory.getLogger(CountryAnalyzer.class);

	public void run() throws Exception {
		LOG.debug("Starting country analyzer");
		// loading file as stream
		try (Stream<String> lines = Files.lines(Paths.get("target", "classes", "iso3166.csv"))) {
			// parsing lines2dto
			List<Country> countries = lines.skip(1).map(s -> {
				// AD,"Andorra"
				String[] strings = s.split(",");
				Country mdo = new Country();
				mdo.setCode(strings[0]);
				mdo.setName(strings[1].substring(1, strings[1].length() - 1));
				return mdo;
			}).collect(Collectors.toList());
			LOG.info("No. of countries={}", countries.size());
			// filter cities starting on "Co"
			List<Country> subList = countries.stream().filter(c -> c.getName().startsWith("Co")).collect(Collectors.toList());
			LOG.info("No. of countries starting on 'Co%'={}", subList.size());
		}
	}

}
