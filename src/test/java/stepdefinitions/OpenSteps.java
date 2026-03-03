package stepdefinitions;

import io.cucumber.java.en.Given;
import utils.AllureUtils;
import utils.BaseActions;


public class OpenSteps {

    @Given("el usuario abre la pagina")
    public void abro_la_url_principal() {
        BaseActions.openURL(null); // usa la URL de config.properties
        AllureUtils.attachScreenshot(BaseActions.getDriver(), "Página inicial abierta");
        BaseActions.closeDriver();
    }

}
