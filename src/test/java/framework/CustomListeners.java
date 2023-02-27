package framework;

import com.aventstack.extentreports.*;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;

public  class CustomListeners extends BaseClass implements ITestListener {

    @Override
    public void onTestSuccess(ITestResult iTestResult){
        try {
            extentTest.pass(iTestResult.getMethod().getXmlTest().getName() +" PASSED! " + iTestResult.getMethod().getMethodName() ,MediaEntityBuilder.createScreenCaptureFromBase64String(failed(iTestResult.getMethod().getMethodName())).build());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onTestFailure(ITestResult iTestResult){
        try {
            failed(iTestResult.getMethod().getMethodName());
            extentTest.fail(iTestResult.getMethod().getXmlTest().getName() + iTestResult.getMethod().getMethodName() +" FAILED! " + iTestResult.getThrowable(),MediaEntityBuilder.createScreenCaptureFromBase64String(failed(iTestResult.getMethod().getMethodName())).build());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
