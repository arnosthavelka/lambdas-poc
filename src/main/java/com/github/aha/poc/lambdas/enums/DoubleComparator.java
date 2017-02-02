package com.github.aha.poc.lambdas.enums;

public enum DoubleComparator implements CompareStrategy<Double> {

	EXACT((x, y) -> x.equals(y)),
	NUMBER((x, y) -> x.longValue() == y.longValue()), 
	LESS((x, y) -> x < y), 
	GREATER((x, y) -> x > y);

	private CompareStrategy<Double> strategy;

	DoubleComparator(final CompareStrategy<Double> strategy) {
		this.strategy = strategy;
	}

	@Override
	public boolean compare(Double x, Double y) {
		return strategy.compare(x, y);
	}
}
