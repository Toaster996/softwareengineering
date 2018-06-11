package de.dhbw.softwareengineering.digitaljournal;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.Ignore;
import org.junit.runner.RunWith;

@Ignore
@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features", glue = {"info.seleniumcucumber.stepdefinitions"}, plugin = {"pretty", "html:target/cucumber"})
public class RunCucumberTest {


}