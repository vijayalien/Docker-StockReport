package pageObjects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import testBase.PageObjectBase;
import utilities.TestingTools;
import utilities.WaitHelpers;

import java.util.ArrayList;

public class BaseClass extends PageObjectBase {
    public BaseClass(RemoteWebDriver driver, TestingTools testingTools) {
        super(driver, testingTools);
    }

    public void assertAllSoftAsserts() {
        softAsserts.assertAll();
    }

    public void softAssertAFail(String assertFailureMessage) {
        softAsserts.fail(assertFailureMessage);
    }

    public void hardAssertAFail(String assertFailureMessage) {
        Assert.fail(assertFailureMessage);
    }

    public void quitDriver() {
        driver.quit();
    }

    public void pause() {
        WaitHelpers.wait(3);
    }

    public ArrayList<String> getVisibleTabs() {
        return new ArrayList<String>(driver.getWindowHandles());
    }

    public String getCurrentURLString() {
        return driver.getCurrentUrl();
    }

    public void switchToTab(String tabId) {
        driver.switchTo().window(tabId);
    }

    public void switchToTabNumber(int tabNumber) {
        ArrayList<String> openedTabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(openedTabs.get(tabNumber - 1));
    }

    public void openNewTabAndSwitchToIt() {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.open()");
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
    }

    public int getNumberOfTabsOpened() {
        return new ArrayList<String>(driver.getWindowHandles()).size();
    }

    public void closeCurrentTab() {
        driver.close();
    }

    public void refresh(){
        driver.navigate().refresh();
    }
}
