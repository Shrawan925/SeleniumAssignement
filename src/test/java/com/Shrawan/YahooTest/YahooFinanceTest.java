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

public class YahooFinanceTest {
	public static String YAHOO_SITE_URL="https://finance.yahoo.com/";
	public static String STOCK_SYMBOL="TESLA";
	public static double MIN_STOCK_PRICE= 200.0;
	
    public WebDriver driver;
    SoftAssert softassert = new SoftAssert();

    @BeforeTest
    public void LaunchYahooFBrowser() {
        //driver = new FirefoxDriver();
    	driver=new ChromeDriver();
        driver.manage().window().maximize();
        System.out.println("Chrome Browser Launched as Expected");
        softassert.assertTrue(true,"Chrome Browser Launched as Expected");
        //softassert.assertAll();
    }

    //@Parameters("url")
    @Test(priority=1)
    public void OpenSite() {
        try {
            driver.get(YAHOO_SITE_URL);
            System.out.println("Opened Yahoo Finance Site As Expected");
            softassert.assertTrue(driver.getCurrentUrl().contains("yahoo.com"), "Yahoo Finance URL opened as Expected: " + "https://finance.yahoo.com/");
        } catch (Exception e) {
            System.out.println("Test failed: " + e.getMessage());
            softassert.fail("Test Failed: " + e.getMessage());
        }
        //softassert.assertAll();
    }

    //@Parameters("STOCK_SYMBOL")
    @Test(priority = 2)
    public void searchStock() {
        WebElement searchBox = waitForElement(By.id("ybar-sbq"), 10);
        searchBox.sendKeys(STOCK_SYMBOL);
        System.out.println("Entered " + STOCK_SYMBOL + " in the search text box As Expected");
        softassert.assertTrue(searchBox.getAttribute("value").contains(STOCK_SYMBOL), "Test Data Entered in Search text box as Expected: " + STOCK_SYMBOL);
        //softassert.assertAll();
    }

    @Test(priority = 3)
    public void verifyAutoSuggest() {
        WebElement firstSuggestion = waitForElement(By.xpath("//*[@id='ybar-sf']/div[4]/div[1]/div/ul[1]/li[1]"), 10);
        String suggestionText = firstSuggestion.getAttribute("title");
        System.out.println("Autosuggest text: " + suggestionText);
        softassert.assertTrue(suggestionText.equalsIgnoreCase("Tesla, Inc."), "Autosuggest text verification passed: " + suggestionText);
        //softassert.assertAll();
    }

    @Test(priority = 4)
    public void clickFirstSuggestion() {
        WebElement firstSuggestion = waitForElement(By.xpath("//*[@id='ybar-sf']/div[4]/div[1]/div/ul[1]/li[1]"), 10);
        firstSuggestion.click();
        System.out.println("Browser clicked on First Suggested Autocomplete Text as Expected");
        softassert.assertTrue(true, "Browser clicked on First Suggested Autocomplete Text as Expected");
        //softassert.assertAll();
    }

    //@Parameters("MIN_STOCK_PRICE")
    @Test(priority = 5)
    public void verifyStockPrice() {
        WebElement stockPriceElement = waitForElement(By.xpath("//section[@class='yf-16vvaki']//span"), 10);
        double stockPrice = Double.parseDouble(stockPriceElement.getText().replace(",", ""));
        System.out.println("TESLA Current stock price is: $" + stockPrice);
        softassert.assertTrue(stockPrice > MIN_STOCK_PRICE, "Verify the TESLA Stock Price is greater than " + MIN_STOCK_PRICE + ": $" + stockPrice);
        //softassert.assertAll();
    }

    @Test(priority = 6)
    public void captureStockData() {
        WebElement previousCloseElement = waitForElement(By.xpath("//*[@id='nimbus-app']//section/article/div[3]/ul/li[1]/span[2]/fin-streamer"), 10);
        WebElement stVolume = waitForElement(By.xpath("//*[@id='nimbus-app']//section/article/div[3]/ul/li[7]/span[2]"), 10);

        String previousClose = previousCloseElement.getText();
        String volume = stVolume.getText();

        System.out.println("TESLA Stock Previous Closing Price: " + previousClose);
        System.out.println("TESLA Total Stock Volume: " + volume);
        softassert.assertTrue(!previousClose.isEmpty(), "TESLA Stock Previous Closing Price: " + previousClose);
        softassert.assertTrue(!volume.isEmpty(), "TESLA Total Stock Volume: " + volume);
        //softassert.assertAll();
    }

    @AfterTest
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
        System.out.println("Test completed, browser closed.");
        softassert.assertTrue(true,"Test completed, browser closed.");
        softassert.assertAll(); // Report all assertion failures at the end
    }

    // Utility method to wait for an element
    public WebElement waitForElement(By by, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }
}