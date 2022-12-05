package customObjects;

import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Reporter;
import org.testng.asserts.Assertion;
import org.testng.asserts.IAssert;
import threadObjects.ThreadLocalWebDriver;
import threadObjects.ThreadRemoteWebDriver;
import utilities.PropertiesHelper;

import java.io.ByteArrayInputStream;

public class CustomAssertion extends Assertion {
    @Override
    public void onAssertFailure(IAssert<?> assertCommand, AssertionError ex) {
        Reporter.log(String.format("[Failed Assert Collector] %s%n", ex), true);
        if (Boolean.parseBoolean(PropertiesHelper.getTestConfigs("remoteWebDriver"))) {
            Allure.addAttachment("screenshot", new ByteArrayInputStream(((TakesScreenshot) ThreadRemoteWebDriver.getThreadDriver()).getScreenshotAs(OutputType.BYTES)));
        } else {
            Allure.addAttachment("screenshot", new ByteArrayInputStream(((TakesScreenshot) ThreadLocalWebDriver.getThreadDriver()).getScreenshotAs(OutputType.BYTES)));
        }
    }
}
