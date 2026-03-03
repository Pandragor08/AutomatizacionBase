package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BaseActions {

    private static ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();
    private static ThreadLocal<WebDriverWait> waitThread = new ThreadLocal<>();


    public static void initializeDriver() {
        if (driverThread.get() == null) {

            String browser = ConfigReader.getProperty("browser");
            int timeout = ConfigReader.getIntProperty("timeout");

            if (browser.equalsIgnoreCase("chrome")) {

                ChromeOptions options = new ChromeOptions();

                String headless = System.getProperty("headless");

                if ("true".equalsIgnoreCase(headless)) {
                    options.addArguments("--headless=new");
                    options.addArguments("--window-size=1920,1080");
                    options.addArguments("--no-sandbox");
                    options.addArguments("--disable-dev-shm-usage");
                }

                WebDriver driver = new ChromeDriver(options);
                driverThread.set(driver);
                waitThread.set(new WebDriverWait(driver, Duration.ofSeconds(timeout)));

                if (!"true".equalsIgnoreCase(headless)) {
                    driver.manage().window().maximize();
                }

            } else {
                throw new RuntimeException("Browser no soportado: " + browser);
            }
        }
    }

    /**
     * Retorna el driver actual
     */
    public static WebDriver getDriver() {
        initializeDriver();
        return driverThread.get();
    }

    /**
     * Retorna el WebDriverWait actual
     */
    public static WebDriverWait getWait() {
        initializeDriver();
        return waitThread.get();
    }


    public static void openURL(String url) {
        if (url == null || url.isEmpty()) {
            url = ConfigReader.getProperty("base.url");
        }
        getDriver().get(url);
        AllureUtils.attachScreenshot(getDriver(), "Página abierta: " + url);
    }


    public static void click(By locator) {
        getWait().until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    public static void waitAndSendKeys(By locator, String text) {
        WebElement element = getWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(text);
    }

    public static WebElement waitForElement(By locator) {
        return getWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static void scrollUntilElementVisible(By locator) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        int maxScrolls = 20;
        int currentScroll = 0;

        while (currentScroll < maxScrolls) {
            try {
                WebElement element = getDriver().findElement(locator);
                if (element.isDisplayed()) {
                    js.executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
                    return;
                }
            } catch (NoSuchElementException ignored) {
            }

            js.executeScript("window.scrollBy(0, 500);");
            currentScroll++;

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        throw new RuntimeException("Elemento no encontrado después de scroll.");
    }

    /**
     * Captura screenshot en Allure
     */
    public static void addScreenshotToAllureReport(String evidenceDescription) {
        AllureUtils.attachScreenshot(getDriver(), evidenceDescription);
    }

    /**
     * Cierra el driver y limpia ThreadLocal
     */
    public static void closeDriver() {
        if (driverThread.get() != null) {
            driverThread.get().quit();
            driverThread.remove();
            waitThread.remove();
        }
    }
}