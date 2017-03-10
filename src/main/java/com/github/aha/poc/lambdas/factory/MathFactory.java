package com.github.aha.poc.lambdas.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class MathFactory {

	  final static Map<MathEnum, Supplier<MathOperation<Integer>>> map = new HashMap<>();

	  static {
	    map.put(MathEnum.ADD, AddOperation::new);
	    map.put(MathEnum.MULTIPLY, MultiplyOperation::new);
	  }   

	  public MathOperation<Integer> getShape(MathEnum mathType){

	     Supplier<MathOperation<Integer>> shape = map.get(mathType);

	     if(shape != null) {
	       return shape.get();
	     }

	     throw new IllegalArgumentException("No such operation " + mathType);
	  }
	  
}
