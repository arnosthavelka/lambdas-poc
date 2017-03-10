package com.github.aha.poc.lambdas.factory;

import java.util.Arrays;

public class MultiplyOperation implements MathOperation<Integer> {

	@Override
	public Integer calc(Integer... values) {
		return values.length == 0 ? 0 : Arrays.stream(values).reduce(1, (a1, a2) -> a1 * a2);

	}

}
