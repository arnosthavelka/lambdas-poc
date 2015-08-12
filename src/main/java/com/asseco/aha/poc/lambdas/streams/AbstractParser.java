package com.asseco.aha.poc.lambdas.streams;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class AbstractParser<T> implements LineParser<T> {

	@Autowired
	private LineReader reader;

	@Override
	public List<T> read(String fileName) {
		try (Stream<String> lines = reader.read(fileName)) {
			return this.parse(lines);
		}
	}

}
