package com.asseco.aha.poc.lambdas.enums;

@FunctionalInterface
public interface OperationStrategy<T> {

	T compute(T x, T y);
}
