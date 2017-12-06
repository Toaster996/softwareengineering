package info.seleniumcucumber.stepdefinitions;

import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import de.dhbw.softwareengeneering.test.TestUtil;
import env.DriverUtil;
import info.seleniumcucumber.methods.BaseTest;
import info.seleniumcucumber.methods.TestCaseFailed;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.logging.LogType;

import java.io.File;
import java.util.logging.Level;

public class UserDefinedSteps implements BaseTest  {


    protected WebDriver driver;

    @Before
    public final void setup(){

        TestUtil.setup();

        driver = new FirefoxDriver();
    }

    @After
    public final void tearDown() {
        driver.quit();
    }

    @Then("^I should see modal \"([^\"]*)\"$")
    public void iShouldSeeModal(String id) throws Throwable {
        WebElement webElement = driver.findElement(By.id(id));
        if(webElement == null){
            throw new TestCaseFailed("Modal with id " + id + " was not present");
        }

        if(!webElement.getTagName().equalsIgnoreCase("div")){
            throw new TestCaseFailed("Element with " + id + " was not a modal (not a div)");
        }

        if(!webElement.getAttribute("class").contains("modal")){
            throw new TestCaseFailed("Element with " + id + " was not a modal");
        }
    }

}
