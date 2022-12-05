package testBase;

import driverContext.DriverContext;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pageObjects.Automation;
import threadObjects.ThreadAutomation;
import threadObjects.ThreadLocalWebDriver;
import threadObjects.ThreadRemoteWebDriver;
import utilities.PropertiesHelper;
import utilities.StringHelpers;
import utilities.TestingTools;
import utilities.WaitHelpers;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class TestBase {
    protected String currentTestSession;
    protected Automation automation;

    public TestBase() {
        WaitHelpers.randomWaits(0, 3000);
        //  payorCode = System.getProperty("payorShortCode").toUpperCase();
        currentTestSession = StringHelpers.getCurrentDateTimeString();
        if (Boolean.parseBoolean(PropertiesHelper.getTestConfigs("remoteWebDriver"))) {
            ThreadRemoteWebDriver.setThreadDriver(new DriverContext().getRemoteWebDriver(currentTestSession));
            ThreadAutomation.setThreadAutomation(new Automation(ThreadRemoteWebDriver.getThreadDriver(), new TestingTools()));
        } else {
            ThreadLocalWebDriver.setThreadDriver(new DriverContext().getLocalWebDriver(currentTestSession));
            ThreadAutomation.setThreadAutomation(new Automation(ThreadLocalWebDriver.getThreadDriver(), new TestingTools()));
        }
        automation = ThreadAutomation.getThreadAutomation();
        //iterationHasFailures = false;
    }


    public void methodSetup() {
        if (Boolean.parseBoolean(PropertiesHelper.getTestConfigs("remoteWebDriver"))) {
            ThreadAutomation.setThreadAutomation(new Automation(ThreadRemoteWebDriver.getThreadDriver(), new TestingTools()));
        } else {
            ThreadAutomation.setThreadAutomation(new Automation(ThreadLocalWebDriver.getThreadDriver(), new TestingTools()));
        }
        automation = ThreadAutomation.getThreadAutomation();
    }


    public void methodTearDown(ITestResult iTestResult) {
        if (iTestResult.getStatus() == ITestResult.FAILURE) {
            // iterationHasFailures = true;
        }

    }

    public void testClassTearDown() {
        if (Boolean.parseBoolean(PropertiesHelper.getTestConfigs("remoteWebDriver"))) {
            ThreadRemoteWebDriver.getThreadDriver().quit();
        } else {
            ThreadLocalWebDriver.getThreadDriver().quit();
        }
        // Reporter.log("Iteration has failures: " + iterationHasFailures, true);
        if (Boolean.parseBoolean(PropertiesHelper.getTestConfigs("recordTestSession"))) {
            DriverContext driverContext = new DriverContext();
            driverContext.waitUntilVideoIsReady(currentTestSession);
//            if (!iterationHasFailures) {
//                // If the is passed, video is not required and will be deleted.
//                driverContext.deleteVideo(currentTestSession);
        }
    }
}

