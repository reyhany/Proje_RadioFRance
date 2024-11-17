package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.RadioPage;
import utils.Driver;

import static org.junit.Assert.assertEquals;

public class RadioStep {

    RadioPage radioPage = new RadioPage();
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
    @Then("Histoire için sonuçlar gorunmeli")
    public void sonuclariDogrula() throws InterruptedException {
        //radioPage.waitForSearchResults(); // Sonuçların görünmesini bekliyoruz.
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
}
