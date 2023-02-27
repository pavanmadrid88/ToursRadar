package framework;

import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import config.DataConstants;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;


import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import com.aventstack.extentreports.*;


public class BasePage {


    public static ThreadLocal<WebDriver> threadLocalWebDriver;

    public static Properties properties;
    public static ExtentReports reports;
    public static ExtentTest extentTest;
    public static ExtentSparkReporter spark;
//    public static UrlCredentialsManager urlCredentialsManager;


    Logger logger = LoggerFactory.getLogger(BasePage.class);

    public BasePage() {
        try {
            InputStream fileInputStream = BasePage.class.getResourceAsStream("/config.properties");
            properties = new Properties();
            properties.load(fileInputStream);
//            urlCredentialsManager = new UrlCredentialsManager(properties);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void init() {


        threadLocalWebDriver = new ThreadLocal<WebDriver>();
        /*URL url = null;*/


        String browser = properties.getProperty("browser");
        switch (browser.trim().toUpperCase()) {
            case DataConstants.Browsers.CHROME:
                logger.info("Setting up Driver");
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--window-size=1920x1200");
                chromeOptions.addArguments("--headless");
                logger.info("Driver set up done");
                threadLocalWebDriver.set(new ChromeDriver(chromeOptions));
                break;
            case DataConstants.Browsers.FIREFOX:
                logger.info("Setting up Firefox Driver");
                WebDriverManager.firefoxdriver().setup();
                logger.info("Driver set up done");
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setHeadless(true);
                firefoxOptions.addArguments("--start-maximized");
                logger.info("thread local driver set up done");
                break;
            default:
                Assert.fail("Invalid Browser name specified");
                break;
        }


        threadLocalWebDriver.get().manage().window().fullscreen();
        threadLocalWebDriver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        threadLocalWebDriver.get().manage().deleteAllCookies();

        String url = properties.getProperty("url");
        logger.info("Launching URL:" + url);
        threadLocalWebDriver.get().get(url);
        threadLocalWebDriver.get().manage().window().maximize();


    }

    public void enterText(By textFieldLocator, String text) {
        WebDriverWait wait = new WebDriverWait((WebDriver) threadLocalWebDriver.get(), Duration.ofSeconds(35));
        wait.until(ExpectedConditions.elementToBeClickable(textFieldLocator));
        threadLocalWebDriver.get().findElement(textFieldLocator).clear();
        threadLocalWebDriver.get().findElement(textFieldLocator).sendKeys(text);
    }

    public void clickElement(By elementToBeClickedLocator) {
        logger.info("Clicking on element whose locator is :" + elementToBeClickedLocator);
        WebDriverWait wait = new WebDriverWait(threadLocalWebDriver.get(), Duration.ofSeconds(40));
        wait.until(ExpectedConditions.elementToBeClickable(elementToBeClickedLocator)).click();
        logger.info("Clicked on element whose locator is :" + elementToBeClickedLocator);
    }

    public Boolean isElementDisplayed(By elementLocator, long seconds) {
        logger.info("Checking Display Status of element whose locator is :" + elementLocator);
        WebDriverWait wait = new WebDriverWait(threadLocalWebDriver.get(), Duration.ofSeconds(seconds));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(elementLocator)).isDisplayed();
    }

    public void selectDropdownItem(By selectElement, String item) {
        logger.info("Selecting dropdown on element whose locator is :" + selectElement + ";item : " + item);
        Select select = new Select(threadLocalWebDriver.get().findElement(selectElement));
        select.selectByVisibleText(item);
        logger.info("Selected dropdown on element whose locator is :" + selectElement + ";item : " + item);

    }

    public String getPageTitle() {
        logger.info("Returning page title");
        return threadLocalWebDriver.get().getTitle();
    }

    public String getPageHeaderText() {
        logger.info("Returning page header text");
        By pageTitle = By.cssSelector("h1[class*='hero__title']");
        return getText(pageTitle);

    }

    public String getText(By elementLocator) {
        logger.info("Returning text of element whose locator is:" + elementLocator);
        WebDriverWait wait = new WebDriverWait(threadLocalWebDriver.get(), Duration.ofSeconds(30));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(elementLocator)).getText();
    }

    public String failed(String methodName) throws IOException {
        try {
            String sourceFile = ((TakesScreenshot) threadLocalWebDriver.get()).getScreenshotAs(OutputType.BASE64);
            return sourceFile;
        } catch (Exception e) {
            return "Error capturing Screenshot";
        }
    }

    public String getCurrentTimestamp() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        Date now = new Date();
        return simpleDateFormat.format(now);
    }


    public void teardown() {
        threadLocalWebDriver.get().quit();
    }

    public int getElementSize(By locator) {
        List<WebElement> elements = threadLocalWebDriver.get().findElements(locator);
        return elements.size();
    }

    public void switchToDefaultContent() {
        threadLocalWebDriver.get().switchTo().defaultContent();
    }

    public void switchToLatestWindowHandle(){

        Set<String> windowHandles = threadLocalWebDriver.get().getWindowHandles();

        for(String windowHandle:windowHandles){
            threadLocalWebDriver.get().switchTo().window(windowHandle);
        }

    }

    public int getWindowHandleSize(){
        return threadLocalWebDriver.get().getWindowHandles().size();
    }

}
