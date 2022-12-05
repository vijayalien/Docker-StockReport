package pageObjects.moneyContorlPageObj;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import testBase.PageObjectBase;
import testBase.TestBase;
import utilities.PropertiesHelper;
import utilities.TestingTools;

import java.util.HashMap;
import java.util.Map;

public class FirstPage extends PageObjectBase {
    @FindBy(id="userId")
    private WebElement usernameTextField;

    @FindBy(xpath = "//li[@class='menu_l1  sub_nav']//a[@title='Markets']")
    private WebElement market;

    @FindBy(xpath = "//table[@class='rhsglTbl']//a[@title='Nifty 50']")
    private WebElement nifty50;

    @FindBy(xpath = "//a[@href='https://www.moneycontrol.com']")
    private WebElement clickMc;

    @FindBy(xpath = "//button[@class='avp-button avp-close-button']")
    private WebElement clickPopUpVideoButton;

    @FindBy(xpath = "(//a[@title='All Stats'])[2]")
    private WebElement clickOnAllStats;

    @FindBy(xpath = "//li[@class='menu_l1 active sub_nav']//a[@title='Markets']")
    private WebElement marketsHomePage;

    @FindBy(xpath = "//a[@title='Broker Research']")
    private WebElement brokerResearch;

    public FirstPage(RemoteWebDriver driver, TestingTools testingTools) {
        super(driver, testingTools);
    }

    public void navigateToMCUrl(String application){
        driver.get(PropertiesHelper.getValue(application,"url"));
        if(assertThatWebElementIsDisplayed(clickMc)){
            waitForWebElementAndClickIt(clickMc);
        }
    }

    public void validateFirstPageMarket(){
        if(assertThatWebElementIsDisplayed(clickPopUpVideoButton)){
            waitForWebElementAndClickIt(clickPopUpVideoButton);
        }
        Assert.assertEquals(assertThatWebElementIsDisplayed(market),true,"Money Control First page validated");
    }

    public void getMCTitle(){
        System.out.println(driver.getTitle());
    }

    public void clickOnNifty(){
        waitForWebElementAndClickIt(nifty50);
    }

    public void clickAllStats(){
        waitForWebElementAndClickIt(clickOnAllStats);
    }

    public void clickMcBrokerResearchPage(){
        hoverOverandClick(market,brokerResearch);
    }
}
