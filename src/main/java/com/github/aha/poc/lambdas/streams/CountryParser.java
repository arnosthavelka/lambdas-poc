package com.github.aha.poc.lambdas.streams;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.github.aha.poc.lambdas.streams.dto.Country;

@Component
public class CountryParser extends AbstractParser<Country> {

	private static final Logger LOG = LoggerFactory.getLogger(CountryParser.class);

	@Override
	public List<Country> parse(Stream<String> lines) {
		LOG.debug("Parsing countries ...");

		List<Country> countries = lines.skip(1).map(s -> {
			// AD,"Andorra"
			String[] strings = s.split(",");
			Country mdo = new Country();
			mdo.setCode(strings[0]);
			mdo.setName(strings[1].substring(1, strings[1].length() - 1));
			return mdo;
		}).collect(Collectors.toList());
		LOG.debug("No. of parsed countries={}", countries.size());

		return countries;
	}

}
