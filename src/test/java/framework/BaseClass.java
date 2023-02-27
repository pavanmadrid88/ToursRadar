package framework;

import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;
import com.aventstack.extentreports.*;


public class BaseClass extends BasePage {
    Logger logger = LoggerFactory.getLogger(BaseClass.class);


    @BeforeSuite
    public void beforeClass() {
        reports = new ExtentReports();
        spark = new ExtentSparkReporter("reports/ExtentSparkReport.html");
        extentTest = reports.createTest("Europe Tours");
    }


    @BeforeMethod
    public void beforeMethod() {
        init();
    }


    @AfterMethod
    public void afterMethod() {
        teardown();
    }

    @AfterSuite
    public void afterClass() {
        reports.attachReporter(spark);
        reports.flush();
    }
}
