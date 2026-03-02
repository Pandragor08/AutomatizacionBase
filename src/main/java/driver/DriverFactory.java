package driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import utils.ConfigReader;

import java.time.Duration;

public class DriverFactory {

    private static WebDriver driver;

    public static WebDriver getDriver() {

        if (driver == null) {

            System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");

            driver = new ChromeDriver();

            driver.manage().timeouts()
                    .implicitlyWait(Duration.ofSeconds(
                            Integer.parseInt(ConfigReader.get("timeout"))
                    ));

            driver.manage().window().maximize();
        }

        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
