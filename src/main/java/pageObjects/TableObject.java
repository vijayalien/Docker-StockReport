package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import testBase.PageObjectBase;
import utilities.TestingTools;

import java.util.ArrayList;
import java.util.List;

public class TableObject extends PageObjectBase {
    protected WebElement anchorWebElement;

    public TableObject(RemoteWebDriver driver, TestingTools testingTools, WebElement anchorWebElement) {
        super(driver, testingTools);
        this.anchorWebElement = anchorWebElement;
    }

    public String getCellText(String expectedColumn, Integer expectedRow) {
        Integer actualRow = expectedRow - 1;
        Integer columnIndex = getHeaderIndex(expectedColumn);
        String cellText = "";
        waitForWebElement(anchorWebElement);
        try {
            cellText = getBodyRow(actualRow).findElements(By.xpath(".//td|.//td")).get(columnIndex).getText();
        } catch (Exception exception) {
            softAsserts.fail(String.format("[Table Helper] There are issues attempting to get the cell text <%s> for the table web element <%s>.%n", exception, anchorWebElement));
        }
        return cellText;
    }

    public String getCellTextAtColumnIndex(Integer columnIndex, Integer expectedIndex) {
        Integer actualRow = expectedIndex - 1;
        String cellText = "";
        waitForWebElement(anchorWebElement);
        try {
            cellText = getBodyRow(actualRow).findElements(By.xpath(".//td|.//td/a")).get(columnIndex).getText();
        } catch (Exception exception) {
            softAsserts.fail(String.format("[Table Helper] There are issues attempting to get the cell text <%s> for the table web element <%s>.%n", exception, anchorWebElement));
        }
        return cellText;
    }

    public WebElement getCellWebElement(String expectedColumn, Integer expectedRow) {
        Integer actualRow = expectedRow - 1;
        Integer columnIndex = getHeaderIndex(expectedColumn);
        WebElement cellWebElement = null;
        waitForWebElement(anchorWebElement);
        try {
            cellWebElement = getBodyRow(actualRow).findElements(By.xpath(".//td|.//td/a")).get(columnIndex);
        } catch (Exception exception) {
            softAsserts.fail(String.format("[Table Helper] There are issues attempting to get the cell web element <%s> for the table web element <%s>.%n", exception, anchorWebElement));
        }
        return cellWebElement;
    }

    public void clickOnCell(String expectedColumn, Integer expectedRow) {
        Integer actualRow = expectedRow - 1;
        Integer columnIndex = getHeaderIndex(expectedColumn);
        ;
        waitForWebElement(anchorWebElement);
        try {
            getBodyRow(actualRow).findElements(By.xpath(".//td|.//td/a")).get(columnIndex).click();
        } catch (Exception exception) {
            softAsserts.fail(String.format("[Table Helper] There are issues attempting to click the cell <%s> for the table web element <%s>.%n", exception, anchorWebElement));
        }
    }

    public void clickOnCellAsNewTab(String expectedColumn, Integer expectedRow) {
        Integer actualRow = expectedRow - 1;
        Integer columnIndex = getHeaderIndex(expectedColumn);
        waitForWebElement(anchorWebElement);
        try {
            Actions actions = new Actions(driver);
            actions.keyDown(Keys.CONTROL)
                    .click(getBodyRow(actualRow).findElements(By.xpath(".//td|.//td/a")).get(columnIndex))
                    .keyUp(Keys.CONTROL)
                    .build()
                    .perform();
        } catch (Exception exception) {
            softAsserts.fail(String.format("[Table Helper] There are issues attempting to click the cell <%s> for the table web element <%s>.%n", exception, anchorWebElement));
        }
    }

    public boolean isCellALink(String expectedColumn, Integer expectedRow) {
        Integer actualRow = expectedRow - 1;
        Integer columnIndex = getHeaderIndex(expectedColumn);
        Boolean isLink;
        waitForWebElement(anchorWebElement);
        try {
            getBodyRow(actualRow).findElements(By.xpath(".//td")).get(columnIndex).findElement(By.xpath(".//a")).getAttribute("href");
            isLink = true;
        } catch (Exception exception) {
            isLink = false;
        }
        return isLink;
    }

    public boolean clickOnTextInCell(String expectedColumn, Integer expectedRow, String textToClick) {
        Integer actualRow = expectedRow - 1;
        Integer columnIndex = getHeaderIndex(expectedColumn);
        Boolean clicked = false;
        waitForWebElement(anchorWebElement);
        String textXpathString = String.format(".//*[text()='%s']", textToClick);
        try {
            getBodyRow(actualRow).findElements(By.xpath(".//td")).get(columnIndex).findElement(By.xpath(textXpathString)).click();
            clicked = true;
        } catch (Exception exception) {
            clicked = false;
        }
        return clicked;
    }

    public Integer getNumberOfDataRows() {
        return getBodyRows().size();
    }

    protected Integer getHeaderIndex(String expectedHeader) {
        Integer index = -1;
        Boolean headerFound = false;
        waitForWebElement(anchorWebElement);
        try {
            Integer counter = 0;
            for (WebElement header : getHeaders()) {
                String headerText = header.getText();
                if (headerText.equals(expectedHeader)) {
                    index = counter;
                    headerFound = true;
                }
                if (headerFound) {
                    break;
                } else {
                    counter++;
                }
            }
        } catch (Exception exception) {
            softAsserts.fail(String.format("[Table Helper] There are issues attempting to get the header index <%s> for the table web element <%s>.%n", expectedHeader, anchorWebElement));
        }
        return index;
    }

    protected List<WebElement> getHeaders() {
        List<WebElement> headers = new ArrayList<>();
        waitForWebElement(anchorWebElement);
        try {
            headers = getHeaderRow().findElements(By.xpath(".//th"));
        } catch (Exception exception) {
            softAsserts.fail(String.format("[Table Helper] There are issues attempting to get the list of headers for the table web element <%s>.%n", anchorWebElement));
        }
        return headers;
    }

    protected WebElement getHeader(String expectedHeader) {
        WebElement header = null;
        try {
            header = getHeaderRow().findElement(By.xpath(".//th/span[text()='" + expectedHeader + "']/.."));
        } catch (Exception exception) {
            softAsserts.fail(String.format("[Table Helper] There are issues attempting to get the header <%s> for the table web element <%s>.%n", expectedHeader, anchorWebElement));
        }
        return header;
    }

    protected WebElement getHeaderRow() {
        WebElement headerRow = null;
        waitForWebElement(anchorWebElement);
        try {
            headerRow = anchorWebElement.findElement(By.xpath(".//thead/tr"));
        } catch (Exception exception) {
            softAsserts.fail(String.format("[Table Helper] There are issues attempting to get the header row for the table web element <%s>.%n", anchorWebElement));
        }
        return headerRow;
    }

    protected List<WebElement> getBodyRows() {
        List<WebElement> bodyRows = new ArrayList<>();
        waitForWebElement(anchorWebElement);
        try {
            bodyRows = anchorWebElement.findElements(By.xpath(".//tbody/tr"));
        } catch (Exception exception) {
            softAsserts.fail(String.format("[Table Helper] There are issues attempting to get the header row for the table web element <%s>.%n", anchorWebElement));
        }
        return bodyRows;
    }

    protected WebElement getBodyRow(Integer expectedRow) {
        WebElement bodyRow = null;
        waitForWebElement(anchorWebElement);
        try {
            bodyRow = anchorWebElement.findElements(By.xpath(".//tbody/tr")).get(expectedRow);
        } catch (Exception exception) {
            softAsserts.fail(String.format("[Table Helper] There are issues attempting to get the header row for the table web element <%s>.%n", anchorWebElement));
        }
        return bodyRow;
    }
}
