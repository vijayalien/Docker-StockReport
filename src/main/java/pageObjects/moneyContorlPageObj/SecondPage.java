package pageObjects.moneyContorlPageObj;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import testBase.PageObjectBase;
import utilities.TestingTools;

public class SecondPage extends PageObjectBase {

    @FindBy(xpath = "//a[contains(text(),'Price Increase & Volume Increase (NSE)')]")
    private WebElement priceVolIncreaseButton;

    @FindBy(xpath = "//h1[contains(text(),'Increase In Price With Increasing Volumes')]")
    private WebElement validateIncreasePriceTitle;

    @FindBy(xpath = "//a[contains(text(),'STOCK MARKET STATISTICS')]")
    private WebElement validateAllStatsTitle;

    public SecondPage(RemoteWebDriver driver, TestingTools testingTools) {
        super(driver, testingTools);
    }


    public void clickPriceVolIncrease() {
        waitForWebElementAndClickIt(priceVolIncreaseButton);
        Assert.assertEquals(assertThatWebElementIsDisplayed(validateIncreasePriceTitle),true);
    }

    public void validateStatsTitle(){
        Assert.assertEquals(assertThatWebElementIsDisplayed(validateAllStatsTitle),true);
    }

}
