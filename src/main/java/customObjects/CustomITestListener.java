package customObjects;

import io.qameta.allure.Attachment;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import java.util.List;

public class CustomITestListener implements ITestListener {
    @Override
    public void onTestSuccess(ITestResult testResult) {
        logReporterOutputToAllure(Reporter.getOutput(testResult));
        ITestListener.super.onTestSuccess(testResult);
    }

    @Override
    public void onTestFailure(ITestResult testResult) {
        logReporterOutputToAllure(Reporter.getOutput(testResult));
        ITestListener.super.onTestFailure(testResult);
    }

    @Attachment
    public String logReporterOutputToAllure(List<String> reporterOutput) {
        String output = "";
        for (String stringLine : reporterOutput) {
            output += stringLine + "<br/>";
        }
        return output;
    }
}
