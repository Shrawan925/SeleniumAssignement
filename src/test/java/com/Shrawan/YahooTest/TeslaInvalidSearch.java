package com.Shrawan.YahooTest;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.annotations.Test.*;
import org.testng.asserts.SoftAssert;
import io.github.bonigarcia.wdm.WebDriverManager;

public class TeslaInvalidSearch {
	public static String YAHOO_SITE_URL="https://finance.yahoo.com/";
	public static String STOCK_SYMBOL="TESLA";
	public static double MIN_STOCK_PRICE= 200.0;
	
    public WebDriver driver;
    SoftAssert softassert = new SoftAssert();

    @BeforeTest
    public void LaunchYahooFBrowser() {
    	//WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        System.out.println("Firefox Browser Launched as Expected");
    }

    //@Parameters("url")
    @Test(priority=1)
    public void OpenSite() {
        try {
            driver.get(YAHOO_SITE_URL);
            System.out.println("Opened Yahoo Finance Site As Expected  Firefox browser");
            softassert.assertTrue(driver.getCurrentUrl().contains("yahoo.com"), "Yahoo Finance URL opened as Expected in Firefox browser: " +YAHOO_SITE_URL);
        } catch (Exception e) {
            System.out.println("Test failed: " + e.getMessage());
            softassert.fail("Test Failed: " + e.getMessage());
        }
    }

    @Test(priority = 2)
    public void searchInvalidStock() {
        String searchInvalidStock = "WELCOME";
        WebElement searchBox = waitForElement(By.id("ybar-sbq"), 10);
        searchBox.sendKeys(searchInvalidStock);
        System.out.println("Entered Invalid data: " + searchInvalidStock + " in the search text box As Expected");
        softassert.assertTrue(searchBox.getAttribute("value").contains(searchInvalidStock), "INVALID Test Data Entered in Search text box as Expected: " + searchInvalidStock);
    }

    @AfterTest
    public void teardown() {
        if (driver != null) {
            driver.close();
        }
        System.out.println("Test completed, browser closed.");
        softassert.assertAll(); // Report all assertion failures at the end
    }

    // Utility method to wait for an element
    public WebElement waitForElement(By by, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }
}