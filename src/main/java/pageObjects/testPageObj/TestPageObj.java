package pageObjects.testPageObj;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import testBase.PageObjectBase;
import utilities.TestingTools;

public class TestPageObj extends PageObjectBase {


    public TestPageObj(RemoteWebDriver driver, TestingTools testingTools) {
        super(driver, testingTools);
    }

    public void navigateToURl() {
        driver.get("https://www.google.com/");
    }

    public void searchForText(String text) {
        driver.findElement(By.xpath("")).sendKeys(text);
    }

    public String getTitle() {
        String title = driver.getTitle();
        System.out.println("title is  : " +title);
        return title;
    }

}
