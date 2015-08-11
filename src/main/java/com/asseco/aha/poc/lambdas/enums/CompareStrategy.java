package com.asseco.aha.poc.lambdas.enums;

@FunctionalInterface
public interface CompareStrategy<T> {

	boolean compare(T x, T y);
}
