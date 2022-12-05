package threadObjects;

import org.openqa.selenium.WebDriver;

public class ThreadLocalWebDriver {

    private static ThreadLocal<WebDriver> threadDriver = new ThreadLocal<>();

    public static synchronized void setThreadDriver(WebDriver driver) {
        threadDriver.set(driver);
    }

    public static synchronized WebDriver getThreadDriver() {
        return threadDriver.get();
    }
}
