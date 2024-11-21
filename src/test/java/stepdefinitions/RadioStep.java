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
import static org.junit.Assert.assertTrue;

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

    public RadioStep(){

    }


    @Then("Ana sayfada olduğumu doğruluyorum")
    public void anaSayfayiDogrula() {
    }

    @When("{string} düğmesine tıklarsam")
    public void düğmesineTikla(String aramaButonu) {
        radioPage.clickBtnRechercher();
        radioPage.clickBtnRechercher2();
    }

    @When("Arama alanına {string} yazarsam")
    public void aramaYap(String histoire) throws InterruptedException {
        radioPage.aramaYap(histoire);
        radioPage.clickBtnRechercher2();

    }

   /* @Then("{string} için sonuçlar gorunmeli")
    public void sonuclariDogrula(String motDeRecherche) throws InterruptedException {
        String expectedUrl = "https://www.radiofrance.fr/recherche?term=" + motDeRecherche;

        System.out.println("expectedUrl = " + expectedUrl);

        // Dinamik olarak doğru URL’nin yüklenmesini bekleyin
        WebDriverWait wait = new WebDriverWait(Driver.getCurrentDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("term=" + motDeRecherche));

        String actualUrl = Driver.getCurrentDriver().getCurrentUrl();
        System.out.println("actualUrl = " + actualUrl);

        // URL'in arama kelimesini içerdiğini doğruluyoruz
        assertTrue("Actual URL does not contain the search term: " + motDeRecherche,
                actualUrl.contains("term=" + motDeRecherche));
    }*/

    @Then("{string} için sonuçlar gorunmeli")
    public void sonuclariDogrula(String motDeRecherche) throws InterruptedException {
        // Beklenen URL'yi oluşturuyoruz
        String expectedUrl = "https://www.radiofrance.fr/recherche?term=" + motDeRecherche;
        System.out.println("expectedUrl = " + expectedUrl);

        // Dinamik olarak doğru URL’nin yüklenmesini bekliyoruz
        WebDriverWait wait = new WebDriverWait(Driver.getCurrentDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("term=" + motDeRecherche));

        // Gerçek URL'yi alıyoruz
        String actualUrl = Driver.getCurrentDriver().getCurrentUrl();
        System.out.println("actualUrl = " + actualUrl);

        // URL'in doğru olduğunu doğruluyoruz
        assertEquals("Expected URL does not match the actual URL", expectedUrl, actualUrl);
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
