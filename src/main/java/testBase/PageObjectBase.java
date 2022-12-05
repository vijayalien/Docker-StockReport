package testBase;

import customObjects.CustomSoftAsserts;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Reporter;
import pageObjects.Automation;
import utilities.TestingTools;
import utilities.WaitHelpers;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

import static java.time.Duration.ofMillis;
import static java.time.Duration.ofSeconds;

public class PageObjectBase {
    protected RemoteWebDriver driver;
    protected TestingTools testingTools;
    protected CustomSoftAsserts softAsserts;

    public PageObjectBase(RemoteWebDriver driver, TestingTools testingTools) {
        this.driver = driver;
        this.testingTools = testingTools;
        this.softAsserts = testingTools.getSoftAssertions();
        PageFactory.initElements(driver, this);
    }


    protected void waitForWebElement(WebElement webElementToWaitFor) {
        Wait wait = new FluentWait(driver)
                .withTimeout(ofMillis(20000))
                .pollingEvery(ofMillis(500))
                .ignoring(NoSuchElementException.class);

        try {
            wait.until(ExpectedConditions.visibilityOf(webElementToWaitFor));
        } catch (TimeoutException timeoutException) {
            softAsserts.fail(String.format("[WebElement Helper] The wait for the web element <%s> has timed out.", webElementToWaitFor));
        } catch (Exception exception) {
            softAsserts.fail(String.format("[WebElement Helper] There are other exception for the web element <%s>.", webElementToWaitFor));
        }
    }

    protected void waitForWebElementToBeClickable(WebElement webElementToWaitFor) {
        Wait wait = new FluentWait(driver)
                .withTimeout(ofMillis(20000))
                .pollingEvery(ofMillis(250))
                .ignoring(NoSuchElementException.class);

        try {
            WebElement webElement = (WebElement) wait.until(ExpectedConditions.elementToBeClickable(webElementToWaitFor));
        } catch (TimeoutException timeoutException) {
            softAsserts.fail(String.format("[WebElement Helper] The wait for the web element <%s> to be clickable has timed out.", webElementToWaitFor));
        } catch (Exception exception) {
            softAsserts.fail(String.format("[WebElement Helper] There are other exception for the web element <%s>.", webElementToWaitFor));
        }
    }

    protected void waitForWebElementWithoutAsserts(WebElement webElementToWaitFor) {
        Wait wait = new FluentWait(driver)
                .withTimeout(ofMillis(10000))
                .pollingEvery(ofMillis(500))
                .ignoring(NoSuchElementException.class);

        try {
            WebElement webElement = (WebElement) wait.until(ExpectedConditions.visibilityOf(webElementToWaitFor));
        } catch (Exception exception) {
            Reporter.log(String.format("[WebElement Helper] Web element <%s> wait without asserts has an exception. %n%s.", webElementToWaitFor, exception));
        }
    }

    protected void waitForContextToAppearAndSwitchToIt(WebElement frameWebElement) {
        Wait wait = new FluentWait(driver)
                .withTimeout(ofMillis(10000))
                .pollingEvery(ofMillis(500))
                .ignoring(NoSuchElementException.class);

        try {
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameWebElement));
        } catch (TimeoutException timeoutException) {
            softAsserts.fail(String.format("[WebElement Helper] The wait for the context <%s> has timed out.", frameWebElement));
        } catch (Exception exception) {
            softAsserts.fail(String.format("[WebElement Helper] There are other exception when waiting for context <%s>.", frameWebElement));
        }
    }

    protected WebElement waitForWebElementWithoutAsserting(WebElement webElementToWaitFor) {
        Wait wait = new FluentWait(driver)
                .withTimeout(ofMillis(10000))
                .pollingEvery(ofMillis(500))
                .ignoring(NoSuchElementException.class);
        WebElement webElement = null;

        try {
            webElement = (WebElement) wait.until(ExpectedConditions.visibilityOf(webElementToWaitFor));
        } catch (TimeoutException timeoutException) {
            Reporter.log(String.format("[WebElement Helper] The wait for the web element <%s> has timed out.", webElementToWaitFor), true);
        }
        return webElement;
    }

    protected void waitForWebElementToDisappear(WebElement webElementToWaitFor) {
        Wait wait = new FluentWait(driver)
                .withTimeout(ofMillis(7500))
                .pollingEvery(ofSeconds(1))
                .ignoring(NoSuchElementException.class);

        try {
            wait.until(ExpectedConditions.invisibilityOf(webElementToWaitFor));
        } catch (TimeoutException timeoutException) {
            softAsserts.fail(String.format("[WebElement Helper] The wait for the web element to disappear <%s> has timed out.", webElementToWaitFor));
        }
    }

    protected void waitForWebElementAndClickIt(WebElement webElement) {
        waitForWebElement(webElement);
        try {
            webElement.click();
            WaitHelpers.wait(5);
            Reporter.log(String.format("Clicked on the listed element <%s>:", webElement.getTagName()));
        } catch (NoSuchElementException noSuchElementException) {
            softAsserts.fail(String.format("[WebElement Helper] No such element exist on screen: <%s>.", webElement));
        } catch (ElementClickInterceptedException elementClickInterceptedException) {
            softAsserts.fail(String.format("[WebElement Helper] Unable to click on element: <%s>.", webElement));
        } catch (Exception exception) {
            softAsserts.fail(String.format("[WebElement Helper] There are other exception for the web element <%s>.", webElement));
        }
    }

    protected void clickWebElement(WebElement webElement) {
        waitForWebElementToBeClickable(webElement);
        webElement.click();
        WaitHelpers.wait(5);
    }

    protected String waitForWebElementAndGetText(WebElement webElement) {
        String webElementText = "";
        waitForWebElement(webElement);
        try {
            webElementText = webElement.getText();
        } catch (NoSuchElementException noSuchElementException) {
            softAsserts.fail(String.format("[WebElement Helper] No such element exist on screen: <%s>.", webElement));
        } catch (Exception exception) {
            softAsserts.fail(String.format("[WebElement Helper] There are other exception for the web element <%s>.", webElement));
        }
        return webElementText;
    }

    protected String waitForWebElementAndGetTextWithoutAsserting(WebElement webElement) {
        String webElementText = "";
        waitForWebElement(webElement);
        try {
            webElementText = webElement.getText();
        } catch (NoSuchElementException noSuchElementException) {
            Reporter.log(String.format("[WebElement Helper] No such element exist on screen: <%s>.", webElement), true);
        } catch (Exception exception) {
            softAsserts.fail(String.format("[WebElement Helper] There are other exception for the web element <%s>.", webElement));
        }
        return webElementText;
    }

    protected void waitForWebElementAndEnterText(WebElement webElement, String textToEnter) {
        waitForWebElementToBeClickable(webElement);
        try {
            webElement.click();
            webElement.clear();
            webElement.sendKeys(textToEnter);
        } catch (NoSuchElementException noSuchElementException) {
            softAsserts.fail(String.format("[WebElement Helper] No such element exist on screen: <%s>.", webElement));
        } catch (Exception exception) {
            softAsserts.fail(String.format("[WebElement Helper] There are other exception for the web element <%s>.", webElement));
        }
    }

    protected void waitForWebElementAndEnterKeyboardKeys(WebElement webElement, Keys KeysToUse) {
        waitForWebElement(webElement);
        try {
            webElement.sendKeys(KeysToUse);
        } catch (NoSuchElementException noSuchElementException) {
            softAsserts.fail(String.format("[WebElement Helper] No such element exist on screen: <%s>.", webElement));
        } catch (Exception exception) {
            softAsserts.fail(String.format("[WebElement Helper] There are other exception for the web element <%s>.", webElement));
        }
    }

    protected void waitForWebElementAndEnterTextSlowly(WebElement webElement, String textToEnter) {
        waitForWebElementToBeClickable(webElement);
        try {
            webElement.click();
            webElement.clear();
            for (char character : textToEnter.toCharArray()) {
                webElement.sendKeys(String.valueOf(character));
                WaitHelpers.wait(20);
            }
        } catch (NoSuchElementException noSuchElementException) {
            softAsserts.fail(String.format("[WebElement Helper] No such element exist on screen: <%s>.", webElement));
        } catch (Exception exception) {
            softAsserts.fail(String.format("[WebElement Helper] There are other exception for the web element <%s>.", webElement));
        }
    }

    protected Boolean assertThatWebElementIsDisplayed(WebElement webElement) {
        WaitHelpers.wait(3);
        Boolean isDisplayed = false;
        waitForWebElement(webElement);
        try {
            isDisplayed = webElement.isDisplayed();
        } catch (Exception exception) {
            softAsserts.fail(String.format("[WebElement Helper] There are issues asserting if web element is displayed for <%s>.", webElement));
        }
        return isDisplayed;
    }

    protected Boolean assertThatWebElementIsDisplayed(WebElement webElement, Boolean assertWebElement) {
        Boolean isDisplayed = false;
        if (assertWebElement) {
            waitForWebElement(webElement);
        } else {
            waitForWebElementWithoutAsserts(webElement);
        }
        try {
            isDisplayed = webElement.isDisplayed();
        } catch (Exception exception) {
            if (assertWebElement) {
                softAsserts.fail(String.format("[WebElement Helper] There are issues asserting if web element is displayed for <%s>.", webElement));
            }
        }
        return isDisplayed;
    }

    protected void clickDropDown(WebElement element, String value) {
        try {
            waitForWebElement(element);
            WaitHelpers.wait(3);
            Select selectDropDown = new Select(element);
            selectDropDown.selectByVisibleText(value);
        } catch (NoSuchElementException noSuchElementException) {
            softAsserts.fail(String.format("[WebElement Helper] No such element exist on screen: <%s>.", element));
        } catch (Exception exception) {
            softAsserts.fail(String.format("[WebElement Helper] There are other exception for the web element <%s>.", element));
        }
    }

    protected void hoverOverandClick(WebElement hoverElement, WebElement clickElement) {
        try {
            waitForWebElement(hoverElement);
            WaitHelpers.wait(3);
            Actions actions = new Actions(driver);
            actions.moveToElement(hoverElement);
            actions.moveToElement(clickElement);
            actions.click().build().perform();
        } catch (NoSuchElementException noSuchElementException) {
            softAsserts.fail(String.format("[WebElement Helper] No such element exist on screen: <%s> <%s>. ", hoverElement ,clickElement));
        } catch (Exception exception) {
            softAsserts.fail(String.format("[WebElement Helper] There are other exception for the web element <%s> <%s>.", hoverElement,clickElement));
        }
    }

    protected Boolean getElementVisibilityStatus(WebElement webElement) {
        Boolean elementVisible = false;
        try {
            elementVisible = webElement.isDisplayed();
        } catch (Exception exception) {
            Reporter.log(String.format("[WebElement Helper] Web element <%s> is not found, hence, not visible.", webElement));
        }
        return elementVisible;
    }

    protected void printPageSource() {
        Reporter.log(String.format("[WebElement Helper] The current page source is:%n%s%n", driver.getPageSource()), true);
    }

    protected void printWebElementSource(WebElement webElement) {
        Reporter.log(String.format("[WebElement Helper] The web element <%s> source is:%n%s%n", webElement, webElement.getAttribute("innerHTML")), true);
    }
}
