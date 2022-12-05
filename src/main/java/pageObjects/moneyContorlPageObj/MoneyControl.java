package pageObjects.moneyContorlPageObj;

import customObjects.CustomSoftAsserts;
import org.openqa.selenium.remote.RemoteWebDriver;
import testBase.PageObjectBase;
import utilities.TestingTools;

public class MoneyControl {
    private CustomSoftAsserts softAsserts;
    private RemoteWebDriver driver;
    public FirstPage mcFirstPage;
    public SecondPage mcSecondPage;
    public BrokerResearchPage brokerResearchPage;

    public MoneyControl(RemoteWebDriver driver, TestingTools testingTools) {
        softAsserts = testingTools.getSoftAssertions();
        this.driver = driver;
        mcFirstPage = new FirstPage(driver, testingTools);
        mcSecondPage = new SecondPage(driver, testingTools);
        brokerResearchPage = new BrokerResearchPage(driver, testingTools);
    }
}
