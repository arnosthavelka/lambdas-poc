package com.github.aha.poc.lambdas.enums;

public enum StringComparator implements CompareStrategy<String> {

	EXACT((x, y) -> x.equals(y)), 
	PREFIX((x, y) -> x.startsWith(y)), 
	SUFIX((x, y) -> x.endsWith(y)), 
	LOWER((x, y) -> x.toLowerCase().equals(y.toLowerCase()));

	private CompareStrategy<String> strategy;

	StringComparator(final CompareStrategy<String> strategy) {
		this.strategy = strategy;
	}

	@Override
	public boolean compare(String x, String y) {
		return strategy.compare(x, y);
	}
}
