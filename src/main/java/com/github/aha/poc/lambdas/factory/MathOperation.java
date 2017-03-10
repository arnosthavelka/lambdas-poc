package com.github.aha.poc.lambdas.factory;

public interface MathOperation<T extends Number> {
	
	T calc(T... values);

}
