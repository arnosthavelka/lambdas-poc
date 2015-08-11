package com.asseco.aha.poc.lambdas.enums;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class StringComparatorTest {

	@Test
	public void testExact() {
		assertThat(StringComparator.EXACT.compare("aha", "aha"), equalTo(true));
		assertThat(StringComparator.EXACT.compare("aha", "Aha"), equalTo(false));
	}

	@Test
	public void testPrefix() {
		assertThat(StringComparator.PREFIX.compare("aha", "aha"), equalTo(true));
		assertThat(StringComparator.PREFIX.compare("ahaaaaa", "aha"), equalTo(true));
		assertThat(StringComparator.PREFIX.compare("aha", "ahaaaaa"), equalTo(false));
	}

	@Test
	public void testSufix() {
		assertThat(StringComparator.SUFIX.compare("aha", "aha"), equalTo(true));
		assertThat(StringComparator.SUFIX.compare("aaaaaha", "aha"), equalTo(true));
		assertThat(StringComparator.SUFIX.compare("ahaaaaa", "aha"), equalTo(false));
	}

	@Test
	public void testLower() {
		assertThat(StringComparator.LOWER.compare("aha", "aha"), equalTo(true));
		assertThat(StringComparator.LOWER.compare("aha", "AHA"), equalTo(true));
		assertThat(StringComparator.LOWER.compare("AHA", "aha"), equalTo(true));
		assertThat(StringComparator.LOWER.compare("ahaaaaa", "aha"), equalTo(false));
	}

}
