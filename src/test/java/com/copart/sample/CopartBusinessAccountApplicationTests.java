package com.copart.sample;

import org.junit.Test;
import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources")
public class CopartBusinessAccountApplicationTests {

	@Test
	public void contextLoads() {
	}

}

