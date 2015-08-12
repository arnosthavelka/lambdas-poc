package com.asseco.aha.poc.lambdas.streams;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.asseco.aha.poc.lambdas.LambdasPocApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = LambdasPocApplication.class)
public class CountryAnalyzerTest {

	@Autowired
	private CountryAnalyzer analyzer;

	@Value("${spring.application.name}")
	private String appName;

	@Test
	public void testExact() throws Exception {
		// just analyzer execution
		analyzer.run();
	}

}
