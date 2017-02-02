package com.github.aha.poc.lambdas.enums;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.github.aha.poc.lambdas.enums.Operation;

@RunWith(MockitoJUnitRunner.class)
public class OperationTest {

	@Test
	public void shouldAddTwoNumbers() {
		assertThat(Operation.ADD.compute(5d, 5d), equalTo(new Double(10)));
	}

	@Test
	public void shouldSubtractTwoNumbers() {
		assertThat(Operation.SUBTRACT.compute(10d, 5d), equalTo(new Double(5d)));
	}

	@Test
	public void shouldMultiplyTwoNumbers() {
		assertThat(Operation.MULTIPLY.compute(5d, 5d), equalTo(new Double(25)));
	}

	@Test
	public void shouldDivideTwoNumbers() {
		assertThat(Operation.DIVIDE.compute(10d, 2d), equalTo(new Double(5d)));
	}

	@Test
	public void shouldDetermineMaximumOfTwoNumbers() {
		assertThat(Operation.MAX.compute(10d, 5d), equalTo(new Double(10d)));
	}
}
