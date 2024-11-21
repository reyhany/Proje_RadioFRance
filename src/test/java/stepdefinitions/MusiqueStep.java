package stepdefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.MusiquePage;
import java.time.Duration;
import static org.junit.Assert.assertTrue;
import static utils.Driver.getCurrentDriver;

public class MusiqueStep {
    MusiquePage musiquePage = new MusiquePage();

    @Then("Musique başlığına tıklıyorum")
    public void musique_başlığına_tıklıyorum() {
        musiquePage.clickBtnMusique();
        waitForUrl("https://www.radiofrance.fr/ecouter-musique");
    }

    @Then("Sunulan ilk eseri dinlemek için Écouter butonuna tıklarım")
    public void sunulan_ilk_eseri_dinlemek_icin_ecouter_butonuna_tiklarim() {
        musiquePage.clickEcouterButton();
    }

    @Then("Müziğin çalmaya başladığını doğrularım")
    public void müziğin_çalmaya_başladığını_doğrularım() {
        WebDriverWait wait = new WebDriverWait(getCurrentDriver(), Duration.ofSeconds(15));
        WebElement timeSpan = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@*='current']")));
        String initialTime = timeSpan.getText();

        try {
            Thread.sleep(5000); // 5 saniye bekle
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        String updatedTime = timeSpan.getText();
        assertTrue("Müzik oynatılmaya başlamadı.", !initialTime.equals(updatedTime));
    }

    @When("Müzik ayar çubuğundaki ses açma-kapama butonuna tıklarım")
    public void müzik_ayar_çubuğundaki_ses_açma_butonuna_tıklarım() throws InterruptedException {
        toggleVolumeButton();
    }

    @When("Müzik ayar çubuğundaki ses kapama butonuna tıklarım")
    public void müzik_ayar_çubuğundaki_ses_kapama_butonuna_tıklarım() {
        musiquePage.volumeButtonClick();
    }

    @When("Müzik ayar çubuğundaki ses seviyesini artırırım")
    public void müzik_ayar_çubuğundaki_ses_seviyesini_artırırım() {
        musiquePage.increaseVolume();
    }

    @When("Müzik ayar çubuğundaki ses seviyesini azaltırım")
    public void müzik_ayar_çubuğundaki_ses_seviyesini_azaltırım() {
        musiquePage.decreaseVolume();
    }

    @Then("Butonların doğru şekilde çalıştığını doğrularım")
    public void butonlarin_dogru_sekilde_calistigini_dogrularim() {
        int initialVolume = getCurrentVolume();
        musiquePage.increaseVolume();
        int increasedVolume = getCurrentVolume();
        assertTrue("Ses artırma butonu çalışmadı", increasedVolume > initialVolume);

        musiquePage.decreaseVolume();
        int decreasedVolume = getCurrentVolume();
        assertTrue("Ses azaltma butonu çalışmadı", decreasedVolume < increasedVolume);

        musiquePage.increaseVolume();
        int finalVolume = getCurrentVolume();
        assertTrue("Ses artırma butonu tekrar çalışmadı", finalVolume > decreasedVolume);
    }

    private int getCurrentVolume() {
        WebElement volumeSliderElement = getCurrentDriver().findElement(By.xpath("//*[@id='playerVolume']"));
        String volumeValue = volumeSliderElement.getAttribute("aria-valuenow");
        return (int) Double.parseDouble(volumeValue);
    }

    private void waitForUrl(String expectedUrl) {
        WebDriverWait wait = new WebDriverWait(getCurrentDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlToBe(expectedUrl));
        String actualUrl = getCurrentDriver().getCurrentUrl();
        assertTrue("Beklenen URL: " + expectedUrl + ", fakat mevcut URL: " + actualUrl, actualUrl.equals(expectedUrl));
    }

    private void toggleVolumeButton() throws InterruptedException {
        musiquePage.volumeButtonClick();
        Thread.sleep(5000); // 5 saniye bekle
        musiquePage.volumeButtonClick();
    }
}
