package com.asseco.aha.poc.lambdas.streams;

import java.util.List;
import java.util.stream.Stream;

public interface LineParser<T> {

	public List<T> parse(Stream<String> lines);

	public List<T> read(String fileName);
}
