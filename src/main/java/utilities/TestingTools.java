package utilities;

import customObjects.CustomSoftAsserts;

public class TestingTools {
    private CustomSoftAsserts softAssert;

    public TestingTools() {
        softAssert = new CustomSoftAsserts();
    }

    public CustomSoftAsserts getSoftAssertions() {
        return softAssert;
    }

    public void setSoftAssertions(CustomSoftAsserts softAssert) {
        this.softAssert = softAssert;
    }
}
