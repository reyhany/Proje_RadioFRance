package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.BasePage;
import pages.RadioPage;
import utils.Driver;

import java.time.Duration;

import static java.awt.SystemColor.text;
import static org.junit.Assert.assertEquals;

public class RadioStep {

    private final WebDriverWait wait;
    RadioPage radioPage = new RadioPage();
    private WebDriver driver;
    BasePage basePage = new BasePage(driver);
    Actions actions = new Actions(driver);
    public RadioStep(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.actions = new Actions(driver);
    }


    @Then("Ana sayfada olduğumu doğruluyorum")
    public void anaSayfayiDogrula() {

    }
    @When("{string} düğmesine tıklarsam")
    public void düğmesineTikla(String aramaButonu) {
        radioPage.clickBtnRechercher();
    }

    @When("Arama alanına {string} yazarsam")
    public void aramaYap(String histoire) {
        radioPage.aramaYap(histoire);

    }
    @Then("Histoire için sonuçlar gorunmeli")
    public void sonuclariDogrula() throws InterruptedException {

        String expectedUrl = "https://www.radiofrance.fr/recherche?term=Histoire";
        System.out.println("expectedUrl = " + expectedUrl);
        String actualUrl = Driver.getCurrentDriver().getCurrentUrl();
        System.out.println("actualUrl = " + actualUrl);
        Thread.sleep(3000);
        assertEquals(expectedUrl, actualUrl);
    }
    @Given("Click le button Se Connecter")
    public void click_le_button_se_connecter() {
        radioPage.clikBtnSeConnecter();
    }

    @Then("Click sur Inscription")
    public void click_sur_inscription() {
        radioPage.clikBtnInscription();
    }

    @Then("Ecrire {string} adress and {string} dans les champs")
    public void ecrire_adress_and_dans_les_champs(String email, String password) {
        radioPage.remplirLeFormPourInscription();
    }

    @Then("Click sur radiobox pour accepter les conditions")
    public void click_sur_radiobox_pour_accepter_les_conditions() {
        radioPage.clikRadioBtnPourCondition();
    }

    @When("Click sur le button S`inscrire")
    public void click_sur_le_button_s_inscrire() {
        radioPage.clikBtnInscrire();
    }

    @Then("Verifie que le message {string} apparait.")
    public void verifie_que_le_message_apparait(String message) {
      //  radioPage.verificationAvecLocationText(message,message);
    }

    @When("The user displays and clicks title of Catégories")
    public void the_user_displays_and_clicks_title_of_catégories() {
// WebDriverWait tanımlayın
       // WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // 10 saniyeye kadar bekler

// categories elementini tıklamadan önce bekleme ekleyin
       // WebElement categoriesElement = wait.until(ExpectedConditions.elementToBeClickable(radioPage.categories));

// Bekleme tamamlandıktan sonra tıklayın
      //  categoriesElement.click();
        radioPage.Categories();
    }
    @Then("The user displays and clicks all part of categoies")
    public void the_user_displays_and_clicks_all_part_of_categoies() {

    }

}
