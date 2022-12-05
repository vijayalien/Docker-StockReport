package pageObjects;

import customObjects.CustomSoftAsserts;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import pageObjects.moneyContorlPageObj.MoneyControl;
import pageObjects.testPageObj.TestPageObj;
import utilities.TestingTools;
import utilities.WaitHelpers;

import java.util.ArrayList;

public class Automation {
    private CustomSoftAsserts softAsserts;
    private RemoteWebDriver driver;
    public TestPageObj tobj;
    public MoneyControl mcPageObj;
    public BaseClass baseClass;


    public Automation(WebDriver driver, TestingTools testingTools) {
        tobj = new TestPageObj((RemoteWebDriver) driver,testingTools);
        baseClass=new BaseClass((RemoteWebDriver) driver,testingTools);
        mcPageObj=new MoneyControl((RemoteWebDriver) driver,testingTools);
        softAsserts = testingTools.getSoftAssertions();
        this.driver = (RemoteWebDriver) driver;
    }


}
