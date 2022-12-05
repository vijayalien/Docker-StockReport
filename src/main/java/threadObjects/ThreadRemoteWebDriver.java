package threadObjects;

import org.openqa.selenium.remote.RemoteWebDriver;

public class ThreadRemoteWebDriver {

    private static ThreadLocal<RemoteWebDriver> threadDriver = new ThreadLocal<>();

    public static synchronized void setThreadDriver(RemoteWebDriver driver) {
        threadDriver.set(driver);
    }

    public static synchronized RemoteWebDriver getThreadDriver() {
        return threadDriver.get();
    }
}
