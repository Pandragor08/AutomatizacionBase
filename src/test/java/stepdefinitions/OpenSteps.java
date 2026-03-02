package stepdefinitions;

import driver.DriverFactory;
import io.cucumber.java.en.Given;
import org.openqa.selenium.WebDriver;
import utils.ConfigReader;

public class OpenSteps {

    WebDriver driver;

    @Given("el usuario abre la pagina")
    public void abrirPagina() {

        driver = DriverFactory.getDriver();

        driver.get(ConfigReader.get("baseUrl"));

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        DriverFactory.quitDriver();
    }
}
