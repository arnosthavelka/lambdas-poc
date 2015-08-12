package com.asseco.aha.poc.lambdas.enums;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DoubleComparatorTest {

	@Test
	public void testExact() {
		assertThat(DoubleComparator.EXACT.compare(2d, 2d), equalTo(true));
		assertThat(DoubleComparator.EXACT.compare(2.15d, 2.15d), equalTo(true));
		assertThat(DoubleComparator.EXACT.compare(2d, 3d), equalTo(false));
	}

	@Test
	public void testNumber() {
		assertThat(DoubleComparator.NUMBER.compare(2.15d, 2.15d), equalTo(true));
		assertThat(DoubleComparator.NUMBER.compare(2.15d, 2.55d), equalTo(true));
		assertThat(DoubleComparator.NUMBER.compare(2.15d, 3.15d), equalTo(false));
	}

	@Test
	public void testLess() {
		assertThat(DoubleComparator.LESS.compare(2.15d, 2.16d), equalTo(true));
		assertThat(DoubleComparator.LESS.compare(2.16d, 2.15d), equalTo(false));
	}

	@Test
	public void testGreater() {
		assertThat(DoubleComparator.GREATER.compare(2.16d, 2.15d), equalTo(true));
		assertThat(DoubleComparator.GREATER.compare(2.15d, 2.16d), equalTo(false));
	}

}
