package com.asseco.aha.poc.lambdas.enums;

public enum Operation implements OperationStrategy<Double> {

	ADD((x, y) -> x + y),
	SUBTRACT((x, y) -> x - y),
	MULTIPLY((x, y) -> x * y),
	DIVIDE((x, y) -> x / y),
    MAX(Double::max);

	private OperationStrategy<Double> operationStrategy;

	Operation(final OperationStrategy<Double> operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    @Override
    public Double compute(Double x, Double y) {
        return operationStrategy.compute(x, y);
    }	
}
