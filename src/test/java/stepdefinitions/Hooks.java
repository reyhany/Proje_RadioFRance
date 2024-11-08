package stepdefinitions;

import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import java.net.MalformedURLException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.Driver;
import utils.OS;

public class Hooks {
    public static final String APK_NAME = "radio-france.apk";
    public static final String WEB_URL = "https://www.radiofrance.fr/franceculture";

    protected WebDriverWait wait;
    private utils.ConfigReader ConfigReader = new utils.ConfigReader();

    @Given("Uygulamayı başlatıyorum")
    public void lanceApp() throws MalformedURLException {
        if (OS.isAndroid()) {
            System.out.println("Launching Android app: " + APK_NAME);
            Driver.Android = Driver.getAndroidDriver(Driver.getAndroidApps());
        } else if (OS.isWeb()) {
            System.out.println("Launching web app: " + WEB_URL);
            Driver.Web = Driver.getWebDriver(ConfigReader.getProperty("browser"));
            Driver.Web.get(WEB_URL);
            this.wait = new WebDriverWait(Driver.Web, Duration.ofSeconds(1));
            handlePopupsAndCookies();
        } else {
            throw new IllegalStateException("Unsupported platform: " + OS.OS);
        }
    }

    private void handlePopupsAndCookies() {
        try {
            WebElement closeButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Tout refuser']")));
            closeButton.click();
            System.out.println("Pop-up successfully closed.");
        } catch (Exception e) {
            System.out.println("Pop-up 'Tout refuser' not found or already handled.");
        }

        try {
            WebElement cookiesAccept = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Tout accepter']")));
            cookiesAccept.click();
            System.out.println("Cookies successfully accepted.");
        } catch (Exception e) {
            System.out.println("Cookie accept button not found or already accepted.");
        }
    }

    @Before
    public void beforeAll(Scenario scenario) throws MalformedURLException {
        OS.OS = ConfigReader.getProperty("platformName");
        if (OS.isWeb()) {
            System.out.println("Starting WebDriver for Web platform.");
        } else if (OS.isAndroid()) {
            System.out.println("Starting AndroidDriver for Android platform.");
        }
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            WebDriver driver = Driver.getCurrentDriver();
            if (driver instanceof TakesScreenshot) {
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "screenshot");
            }
        }
        quitDriver();
    }

    public static String getAppPackage() {
        return "com.radiofrance.radio.radiofrance.android";
    }

    public void killApplication(AndroidDriver driver) {
        driver.terminateApp(getAppPackage());
    }

    public void quitDriver() {
        WebDriver driver = Driver.getCurrentDriver();
        if (driver != null) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (OS.isAndroid()) {
                killApplication((AndroidDriver) driver);
            }
            driver.quit();
        }
    }
}
