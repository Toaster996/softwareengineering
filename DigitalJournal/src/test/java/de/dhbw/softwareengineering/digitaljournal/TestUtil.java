package de.dhbw.softwareengineering.digitaljournal;

import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;

public class TestUtil {

    public static String baseUrl = "http://localhost:8080/";

    public static void setup(){
        // Don't log unnecessary information
        System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE,"/dev/null");

        File geckodriver = new File(TestUtil.class.getClassLoader().getResource("geckodriver.exe").getFile());
        System.setProperty("webdriver.gecko.driver", geckodriver.getAbsolutePath());
    }
}
