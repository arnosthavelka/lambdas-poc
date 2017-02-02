package com.github.aha.poc.lambdas.streams;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleTest {

	private static final Logger LOG = LoggerFactory.getLogger(SimpleTest.class);

	@Test
	public void filterData() throws Exception {
		// based on http://stackoverflow.com/questions/23696317/java-8-find-first-element-by-predicate
		List<Integer> list = Arrays.asList(1, 10, 3, 7, 5);
		int a = list.stream()
		            .peek(num -> LOG.debug("processing value " + num))
		            .filter(x -> x > 5)
		            .findFirst()
		            .get();
		LOG.debug("First value is {}", a);

	}

}
