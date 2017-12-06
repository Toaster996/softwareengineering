package de.dhbw.softwareengeneering.test;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features", glue = {"info.seleniumcucumber.stepdefinitions"}, plugin = {"pretty", "html:target/cucumber"})
public class RunTest {


}