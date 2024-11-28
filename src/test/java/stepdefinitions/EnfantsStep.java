package stepdefinitions;

import io.appium.java_client.AppiumBy;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pages.EnfantsPage;
import utils.Driver;
import utils.OS;

import static org.junit.Assert.assertEquals;
import static utils.Driver.getCurrentDriver;


public class EnfantsStep {
    EnfantsPage enfantsPage = new EnfantsPage();
    public EnfantsStep() {
    }

   /* @When("Je clique sur le lien {string} dans le menu principal")
    public void jeCliqueSurLeLienDansLeMenuPrincipal(String arg0) throws InterruptedException {
        // Elementi bulana kadar kaydır
        enfantsPage.scrollToBtnEnfants();
        enfantsPage.btnEnfants();
        Thread.sleep(3000);
    } */
   @When("Je clique sur le lien {string} dans le menu principal")
   public void jeCliqueSurLeLienDansLeMenuPrincipal(String text) {
       enfantsPage.btnEnfants();
   }



    @Then("Vérifier que l'URL contient {string}")
    public void vérifierQueLURLContient(String enfants) throws InterruptedException {
        // Actual URL'yi driver'dan alıyoruz
        String actualUrl = getCurrentDriver().getCurrentUrl();
        // Assertion ile URL'de "enfants" kelimesi geçtiğini doğruluyoruz
        Assert.assertTrue("URL 'enfants' kelimesini içermiyor: " + actualUrl, actualUrl.contains("enfants"));
    }

    @And("Voir le titre {string}")
    public void voirLeTitre(String arg0) {
        enfantsPage.textPodcastsHistoiresEnfants();
        String expectedText = "Podcasts et histoires pour les enfants";
        WebElement titleElement = getCurrentDriver().findElement(By.xpath("//h1[contains(@class,'CoverFocus-title') and text()='Podcasts et histoires pour les enfants']"));
        
        // Başlık metninin doğru olup olmadığını kontrol et
        assertEquals("Başlık metni uyumsuz: ", expectedText, titleElement.getText());

    }

    @When("Je clique sur {string} et sélectionne {string}")
    public void jeCliqueSurEtSélectionne(String arg0, String arg1) throws InterruptedException {
        enfantsPage.Categories();
        Thread.sleep(2000);
        enfantsPage.btnEnfants2();
    }

    @Then("Vérifier que l'URL est identique à celle obtenue via le menu enfants")
    public void vérifierQueLURLEstIdentiqueÀCelleObtenueViaLeMenuEnfants() {
        if (OS.isWeb()) {
            String expectedUrl = "https://example.com/enfants";
            String actualUrl = Driver.getCurrentDriver().getCurrentUrl();
            assertEquals(expectedUrl, actualUrl);
        } else if (OS.isAndroid()) {
            // Mobilde URL kontrolü yerine başka bir kontrol yapılabilir.
            System.out.println("Mobil platformda bu adım atlanıyor.");
        }
    }


}
