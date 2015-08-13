package com.asseco.aha.poc.lambdas.streams;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.asseco.aha.poc.lambdas.streams.dto.City;

@Component
public class CityParser extends AbstractParser<City> {

	private static final Logger LOG = LoggerFactory.getLogger(CityParser.class);

	@Override
	public List<City> parse(Stream<String> lines) {
		LOG.debug("Parsing cities ...");

		List<City> countries = lines.skip(2).map(s -> {
			// ad,arans,Arans,04,,42.5833333,1.5166667
			String[] strings = s.split(",");
			City mdo = new City();
			mdo.setName(strings[2]);
			mdo.setCountryCode(strings[0].toUpperCase());
			mdo.setRegion(strings[3]);
			return mdo;
		}).filter(Objects::nonNull).collect(Collectors.toList());
		LOG.debug("No. of parsed cities={}", countries.size());

		return countries;
	}

}
