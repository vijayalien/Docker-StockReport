package pageObjects.moneyContorlPageObj;

import com.google.common.collect.Tables;
import io.cucumber.messages.types.TableCell;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import pageObjects.TableObject;
import testBase.PageObjectBase;
import utilities.TestingTools;


public class BrokerResearchPage extends PageObjectBase {

    @FindBy(xpath = "//h1[contains(text(),'broker research')]")
    private WebElement brokerResearchTitle;

    @FindBy(xpath = "//li[@onclick=\"recommendationdrpval('buy');\"]")
    private WebElement onClickBuy;

    @FindBy(xpath = "//a[@class='aplyFilt']")
    private WebElement applyFilter;

    @FindBy(xpath = "//table[@class='tablesorter']")
    private WebElement brokerResultTable;

    public BrokerResearchPage(RemoteWebDriver driver, TestingTools testingTools) {
        super(driver, testingTools);
    }

    public void validateBrokerResearchPageTitle(){
        Assert.assertEquals(assertThatWebElementIsDisplayed(brokerResearchTitle),true);
    }

    public void onClickBuy(){
        waitForWebElementAndClickIt(onClickBuy);
        waitForWebElementAndClickIt(applyFilter);
    }

    public void fetchResults() {
        TableObject lineLevelTable = new TableObject(driver, testingTools, brokerResultTable);
        Integer numberOfLineLevelItems = lineLevelTable.getNumberOfDataRows();
        if (numberOfLineLevelItems > 0) {
            for (int row = 1; row <= numberOfLineLevelItems; row++) {
                System.out.println(lineLevelTable.getCellText("Date",row));
                System.out.println(lineLevelTable.getCellText("Company",row));
                System.out.println(lineLevelTable.getCellText("Broker",row));
                System.out.println(lineLevelTable.getCellText("Reco",row));
                System.out.println(lineLevelTable.getCellText("Price on\nReport dt.",row));
                System.out.println(lineLevelTable.getCellText("CMP",row));
                System.out.println(lineLevelTable.getCellText("Target\nPrice",row));
                System.out.println(lineLevelTable.getCellText("Profit\nPtial %",row));
                System.out.println(lineLevelTable.getCellText("Realized\nReturns",row));
                System.out.println(lineLevelTable.getCellText("Sensex\nReturns",row));

            }
        }
    }

}
