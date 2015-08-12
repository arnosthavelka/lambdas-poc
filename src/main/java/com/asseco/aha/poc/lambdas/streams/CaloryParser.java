package com.asseco.aha.poc.lambdas.streams;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.asseco.aha.poc.lambdas.streams.dto.Calory;

@Component
public class CaloryParser extends AbstractParser<Calory> {

	private static final Logger LOG = LoggerFactory.getLogger(CaloryParser.class);

	@Override
	public List<Calory> parse(Stream<String> lines) {
		LOG.debug("Parsing countries ...");

		List<Calory> countries = lines.skip(2).map(s -> {
			// Alfalfa Seeds,Sprouted,Raw,1 Cup,33,10,0,1,1
			String[] strings = s.split(",");
			Calory mdo = new Calory();
			mdo.setName(strings[0]);
			mdo.setMeasure(strings[1]);
			if (strings.length > 5) {
				try {
					mdo.setCarbo(Integer.parseInt(strings[5]));
					mdo.setProtein(Integer.parseInt(strings[6]));
				} catch (NumberFormatException nfe) {
					// nothing to worry about
					return null;
				}
			}
			return mdo;
		}).filter(Objects::nonNull).collect(Collectors.toList());
		LOG.debug("No. of parsed countries={}", countries.size());

		return countries;
	}

}
