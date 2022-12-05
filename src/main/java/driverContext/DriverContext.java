package driverContext;

import io.github.bonigarcia.wdm.WebDriverManager;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.Browser;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Reporter;
import utilities.PropertiesHelper;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class DriverContext {
    public RemoteWebDriver getRemoteWebDriver(String currentTestSessionName) {
        RemoteWebDriver driver = null;
        switch (new PropertiesHelper().getTestConfigs("browser")) {
            case "firefox":
                driver = getFirefoxRemoteWebDriver(currentTestSessionName);
                break;
            default:
                driver = getChromeRemoteWebDriver(currentTestSessionName);
        }
        return driver;
    }

    public WebDriver getLocalWebDriver(String currentTestSessionName) {
        WebDriver driver = null;
        switch (new PropertiesHelper().getTestConfigs("browser")) {
            case "firefox":
                driver = getFirefoxLocalWebDriver(currentTestSessionName);
                break;
            default:
                driver = getChromeLocalWebDriver(currentTestSessionName);
        }
        return driver;
    }

    public WebDriver getChromeLocalWebDriver(String currentTestSessionName) {
        WebDriver driver;
        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--no-sandbox");
//        options.addArguments("--disable-dev-shm-usage");
//        options.addArguments("--disable-blink-features=AutomationControlled");
//        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
//        options.setExperimentalOption("useAutomationExtension", null);
//        options.addArguments("window-size=1920,1080");
//        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36");
        driver = WebDriverManager.chromedriver().create();
        driver.manage().window().maximize();
        return driver;
    }

    public RemoteWebDriver getChromeRemoteWebDriver(String currentTestSessionName) {
        RemoteWebDriver driver = null;
        ChromeOptions options = new ChromeOptions();
        options.setCapability("browserVersion", "103.0");
        options.setCapability("selenoid:options", new HashMap<String, Object>() {{
            put("name", currentTestSessionName);
            put("sessionTimeout", "15m");
            put("env", new ArrayList<String>() {{
                add("TZ=UTC");
            }});
            put("labels", new HashMap<String, Object>() {{
                put("manual", "true");
            }});
            put("enableVideo", true);
            put("videoName", currentTestSessionName + ".mp4");
            put("enableVNC", true);
        }});
        options.setCapability("chromeOptions", new HashMap<String, Object>() {{
            put("args", "--user-agent=Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.0.0 Safari/537.36");
        }});
        try {
            driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);
        } catch (MalformedURLException e) {
            Reporter.log(String.format("The URL to create Chrome driver is malformed with error message: %s%n", e), true);
        }
        driver.manage().window().maximize();
        return driver;
    }

    public WebDriver getFirefoxLocalWebDriver(String currentTestSessionName) {
        WebDriver driver = null;
        FirefoxOptions options = new FirefoxOptions();
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36 OPR/60.0.3255.170";
        options.addPreference("general.useragent.override", userAgent);
        options.addPreference("browser.privatebrowsing.autostart", true);
        options.setCapability("useAutomationExtension", false);
        options.addArguments(Arrays.asList("--no-sandbox", "--ignore-certificate-errors", "--homepage=about:blank", "--no-first-run", "--disable-notifications"));
        driver = WebDriverManager.firefoxdriver().capabilities(options).create();
        driver.manage().window().maximize();

        return driver;
    }

    public RemoteWebDriver getFirefoxRemoteWebDriver(String currentTestSessionName) {
        RemoteWebDriver driver = null;
        FirefoxOptions options = new FirefoxOptions();
        options.setCapability("browserVersion", "103.0");
        options.setCapability("selenoid:options", new HashMap<String, Object>() {{
            put("name", currentTestSessionName);
            put("sessionTimeout", "3m");
            put("env", new ArrayList<String>() {{
                add("TZ=America/Chicago");
            }});
            put("labels", new HashMap<String, Object>() {{
                put("manual", "true");
            }});
            if (Boolean.parseBoolean(PropertiesHelper.getTestConfigs("recordTestSession"))) {
                put("enableVideo", true);
                put("videoName", currentTestSessionName + ".mp4");
            }
            put("enableVNC", true);
        }});
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36 OPR/60.0.3255.170";
        options.addPreference("general.useragent.override", userAgent);
        try {
            String url = Boolean.parseBoolean(PropertiesHelper.getTestConfigs("useGgr")) ?
                    String.format("http://%s:%s@localhost:4444/wd/hub", PropertiesHelper.getTestConfigs("ggrUsername"), PropertiesHelper.getTestConfigs("ggrPassword")) :
                    "http://localhost:4444/wd/hub";
            driver = new RemoteWebDriver(new URL(url), options);
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(250));

        } catch (MalformedURLException e) {
            Reporter.log(String.format("The URL to create Firefox driver is malformed with error message: %s%n", e), true);
        }
        return driver;
    }

    public void waitUntilVideoIsReady(String currentTestSession) {
        Boolean videoIsReady = false;
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("http://localhost:4444/video/" + currentTestSession + ".mp4")
                .method("GET", null)
                .build();
        do {
            try {
                Response response = client.newCall(request).execute();
                if (response.code() == 200) {
                    videoIsReady = true;
                } else {
                    Thread.sleep(1000);
                }
                response.close();
            } catch (IOException e) {
                Reporter.log("[Selenoid Video Waiter] Unable to wait for selenoid video to be ready.", true);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } while (!videoIsReady);
    }

    public void deleteVideo(String currentTestSession) {
        String expectedVideoPath = System.getProperty("user.home") + "/selenoid/screenRecordings/" + currentTestSession + ".mp4";
        File file = new File(expectedVideoPath);
        if (file.delete()) {
            Reporter.log(String.format("[Video File Deleter] Video for test session <%s> has been deleted.%n", currentTestSession), true);
        }
    }
}
