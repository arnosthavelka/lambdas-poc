package com.github.aha.poc.lambdas.streams;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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
	
    @Test
    public void boxing() {
        Random randomizer = new Random();
        int MAX_SIZE = 4;
        List<Integer> values = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Stream<Integer> boxed = IntStream.range(0, (values.size() + MAX_SIZE - 1) / MAX_SIZE).boxed();
        boxed.parallel().forEach(i -> {
            int timeout = randomizer.nextInt(1000);
            LOG.debug("Part {} is waiting {}ms", i, timeout);
            List<Integer> subList = values.subList(i * MAX_SIZE, Integer.min(MAX_SIZE * (i + 1), values.size()));
            subList.stream().forEach(v -> LOG.debug("Part {} - value {}", i, v));
        });
    }


    @Test
    public void reduce() {
        BigDecimal remaing = BigDecimal.valueOf(25);
        List<Integer> values = Arrays.asList(5, 3, 3, 4, 5);
        LOG.debug("Staring reduce with remaining value={}", remaing);
        remaing = values.stream().map(BigDecimal::valueOf).reduce(remaing, (rem, value) -> {
            if (BigDecimal.ZERO.compareTo(rem) < 0) {
                BigDecimal rest = rem.subtract(value);
                LOG.debug("Procesing value={}, remains={}", value, rest);
                return rest;
            }
            LOG.debug("Ignoring value={}", value);
            return rem;
        });
        LOG.debug("Reduce finished with remaining value={}", remaing);

    }
    
}
