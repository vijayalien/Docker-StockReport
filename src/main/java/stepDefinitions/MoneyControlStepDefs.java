package stepDefinitions;


import io.cucumber.java.bs.A;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import org.bouncycastle.asn1.esf.SPuri;
import org.testng.Reporter;
import testBase.TestBase;
import utilities.WaitHelpers;

public class MoneyControlStepDefs extends TestBase{


    @Given("user redirects to moneycontrol website {string}")
    public void redirectToWebsite(String url) {
        //tb.automation.driver.get("https://www.moneycontrol.com/");
        automation.mcPageObj.mcFirstPage.navigateToMCUrl(url);
    }

    @And("user get the title")
    public void getTitle() {
        //tb.automation.driver.getTitle();
        automation.mcPageObj.mcFirstPage.getMCTitle();
    }

    @And("user clicks on nifty50 button")
    public void clickNiftyButton(){
        automation.mcPageObj.mcFirstPage.clickOnNifty();
    }

    @And("user clicks on all stats")
    public void clickAllStats(){
        automation.mcPageObj.mcFirstPage.clickAllStats();
    }

    @And("validate the first page")
    public void validateFirstPage(){
        automation.mcPageObj.mcFirstPage.validateFirstPageMarket();
    }

    @And("user clicks on price and volume increase")
    public void clickPriceVolIncrease(){
        automation.mcPageObj.mcSecondPage.clickPriceVolIncrease();
    }

    @And("user wait for {string} seconds")
    public void waitSeconds(String sec){
        WaitHelpers.wait(Integer.parseInt(sec));
        Reporter.log(String.format("Waiting for <%s> seconds",sec));
    }

    @And("user validate all stats page")
    public void allStatsPage(){
        automation.mcPageObj.mcSecondPage.validateStatsTitle();
    }

    @And("user clicks on broker research page")
    public void clickBrokerResearch(){
        automation.mcPageObj.mcFirstPage.clickMcBrokerResearchPage();
    }

    @And("user validate broker research page")
    public void validateBrokerResearchPage(){
        automation.mcPageObj.brokerResearchPage.validateBrokerResearchPageTitle();
    }

    @And("user clicks on buy option")
    public void clickOnBuyOption(){
        automation.mcPageObj.brokerResearchPage.onClickBuy();
    }

    @And("user fetch broker results details")
    public void getBrokerResults(){
        automation.mcPageObj.brokerResearchPage.fetchResults();
    }
}