package stepDefinitions;

import io.cucumber.java.en.And;
import org.testng.Reporter;
import testBase.TestBase;
import utilities.WaitHelpers;

public class BaseStepDefs extends TestBase {


    @And("user relods the page")
    public void pageRealod(){
    automation.baseClass.refresh();
    }

}
