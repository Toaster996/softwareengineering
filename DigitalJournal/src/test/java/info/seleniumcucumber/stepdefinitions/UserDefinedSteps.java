package info.seleniumcucumber.stepdefinitions;

import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import env.DriverUtil;
import info.seleniumcucumber.methods.BaseTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;

public class UserDefinedSteps implements BaseTest  {


    protected WebDriver driver;

    @Before
    public final void setup(){
        File geckodriver = new File(getClass().getClassLoader().getResource("geckodriver.exe").getFile());
        System.setProperty("webdriver.gecko.driver", geckodriver.getAbsolutePath());
        driver = new FirefoxDriver();
    }

    @After
    public final void tearDown() {
        driver.quit();
    }

    @Then("^I should see modal \"([^\"]*)\"$")
    public void iShouldSeeModal(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
