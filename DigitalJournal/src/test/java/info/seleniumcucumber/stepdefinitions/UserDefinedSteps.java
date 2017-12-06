package info.seleniumcucumber.stepdefinitions;

import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.runtime.ScenarioImpl;
import de.dhbw.softwareengeneering.test.TestUtil;
import de.dhbw.softwareengineering.model.User;
import de.dhbw.softwareengineering.model.dao.UserDAO;
import de.dhbw.softwareengineering.utilities.Constants;
import env.DriverUtil;
import info.seleniumcucumber.methods.BaseTest;
import info.seleniumcucumber.methods.TestCaseFailed;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.logging.LogType;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class UserDefinedSteps implements BaseTest  {


    protected WebDriver driver;

    @Before
    public final void setup(){

        TestUtil.setup();

        driver = DriverUtil.getDefaultDriver();
    }

    @Then("^I should see modal \"([^\"]*)\"$")
    public void iShouldSeeModal(String id) throws Throwable {
        WebElement webElement = null;
        for(int i = 0; i < 30; i++) {
            Thread.sleep(500);

            try {
                webElement = driver.findElement(By.id(id));
            } catch (NoSuchElementException e) {}


            if (webElement != null && !webElement.getTagName().equalsIgnoreCase("div") && webElement.getAttribute("class").contains("modal")) {
               break;
            }

        }

        if (webElement == null) {
            throw new TestCaseFailed("Modal with id " + id + " was not present");
        }

        if (!webElement.getTagName().equalsIgnoreCase("div")) {
            throw new TestCaseFailed("Element with " + id + " was not a modal (not a div)");
        }

        if (!webElement.getAttribute("class").contains("modal")) {
            throw new TestCaseFailed("Element with " + id + " was not a modal");

        }
    }

    @Then("^I should see bootstrapalert \"([^\"]*)\"$")
    public void iShouldSeeBootstrapalert(String id) throws Throwable {
        WebElement webElement = null;
        for(int i = 0; i < 30; i++) {
            Thread.sleep(500);

            try {
                webElement = driver.findElement(By.id(id));
            } catch (NoSuchElementException e) {}


            if (webElement != null && !webElement.getTagName().equalsIgnoreCase("div") && webElement.getAttribute("class").contains("alert")) {
                break;
            }

        }
        if (webElement == null) {
            throw new TestCaseFailed("Alert with id " + id + " was not present");
        }

        if (!webElement.getTagName().equalsIgnoreCase("div")) {
            throw new TestCaseFailed("Element with " + id + " was not an alert (not a div)");
        }

        if (!webElement.getAttribute("class").contains("alert")) {
            throw new TestCaseFailed("Element with " + id + " was not an alert");

        }
    }

}
