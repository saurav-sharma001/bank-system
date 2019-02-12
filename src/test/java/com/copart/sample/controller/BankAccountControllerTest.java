package com.copart.sample.controller;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.java.en.Given;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features="classpath:features/account_creation.feature")
public class BankAccountControllerTest {

	@Given("")
	public void user() {
		
	}
}
