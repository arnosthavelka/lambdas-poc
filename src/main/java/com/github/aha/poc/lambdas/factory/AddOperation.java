package com.github.aha.poc.lambdas.factory;

import java.util.Arrays;

public class AddOperation implements MathOperation<Integer> {

	@Override
	public Integer calc(Integer... values) {
		return Arrays.asList(values).stream().reduce(0, Integer::sum);
	}

}
