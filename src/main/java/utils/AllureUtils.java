package utils;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.ByteArrayInputStream;

public class AllureUtils {

    @Attachment(value = "Screenshot", type = "image/png")
    public static byte[] takeScreenshot(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "Log", type = "text/plain")
    public static String saveTextLog(String message) {
        return message;
    }

    public static void addDescription(String description) {
        Allure.description(description);
    }

    public static void attachScreenshot(WebDriver driver, String name) {
        Allure.addAttachment(name, new ByteArrayInputStream(
                ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)
        ));
    }
}
