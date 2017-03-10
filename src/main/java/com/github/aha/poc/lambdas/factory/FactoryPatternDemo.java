package com.github.aha.poc.lambdas.factory;

import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FactoryPatternDemo {
	
	/** Logger. */
	private static final Logger LOG = LoggerFactory.getLogger(FactoryPatternDemo.class);	

	public static void main(String[] args) {

		Supplier<MathFactory> shapeFactory = MathFactory::new;

		Integer addValue = shapeFactory.get().getShape(MathEnum.ADD).calc(1, 2, 3);
		LOG.info("1 + 2 + 3 = {}", addValue);
		Integer multiplyValue = shapeFactory.get().getShape(MathEnum.MULTIPLY).calc(2, 3);
		LOG.info("2 * 3 = {}", multiplyValue);

	}
}