package stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.MusiquePage;
import java.time.Duration;
import java.util.List;

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

    @When("Kullanıcı Vitesse de lecture butonuna tıklar")
    public void kullanıcı_vitesse_de_lecture_butonuna_tıklar() {
        musiquePage.vitesseDeLectureClick();
    }
    @Then("Kullanıcı hız ayarlarını sırayla seçer ve doğrular")
    public void kullanici_hiz_ayarlarini_sirayla_secer_ve_dogrular() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(getCurrentDriver(), Duration.ofSeconds(10));

        // Hız ayar butonunu açmak için musiquePage metodunu kullan
        musiquePage.vitesseDeLectureClick();
        System.out.println("Vitesse de lecture butonuna tıklandı.");

        // Hız seçeneklerini sırayla seçme ve doğrulama
        for (int i = 1; i <= 6; i++) {
            // XPath ile sıradaki seçeneği bulma
            WebElement option = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@class='RemoteSelectOption qg-st6 svelte-v7tpv8'])[" + i + "]")));

            // Seçeneğin metnini alma (örneğin: x1, x1.2, x1.5, vb.)
            String expectedSpeed = option.getText().replace("x", ""); // "x" kısmını kaldır
            if (expectedSpeed.contains(" ")) {
                expectedSpeed = expectedSpeed.split(" ")[0]; // "par défaut" gibi metinleri temizle
            }
            System.out.println("Seçilen hız: " + expectedSpeed);

            // Seçeneği tıklama
            try {
                option.click();
            } catch (ElementNotInteractableException e) {
                System.out.println("Option click with JavaScript due to non-interactable issue.");
                JavascriptExecutor js = (JavascriptExecutor) getCurrentDriver();
                js.executeScript("arguments[0].click();", option);
            }
            Thread.sleep(5000); // Alternatif olarak Explicit Wait kullanılabilir

            // Hız butonundaki değeri kontrol etme
            WebElement currentSpeed = getCurrentDriver().findElement(By.xpath("//button[@title='Vitesse de lecture']"));
            String appliedSpeed = currentSpeed.getText();

            // Doğrulama
            assertTrue("Hız doğru uygulanmadı: Beklenen = " + expectedSpeed + ", Mevcut = " + appliedSpeed,
                    appliedSpeed.contains(expectedSpeed));
            System.out.println("Hız başarıyla değiştirildi ve doğrulandı: " + expectedSpeed);

            // Dropdown menüsünü tekrar aç
            if (i < 6) { // Son seçenekte tekrar açmaya gerek yok
                musiquePage.vitesseDeLectureClick();
                Thread.sleep(1000); // Dropdown menüsünün tekrar açılması için kısa bekleme
            }

        }
    }
    @When("Kullanıcı Reduire Player butonuna tıklar ve ekran küçülür")
    public void kullanici_reduire_player_butonuna_tiklar_ve_ekran_kuculur() throws InterruptedException {

        // Reduire Player butonuna tıklama işlemi
        musiquePage.reduirePlayerClick();
        System.out.println("Reduire Player butonuna tıklandı.");

        // Player küçültme durumunun doğrulanması
        WebElement agrandirPlayer = getCurrentDriver().findElement(By.xpath("//*[@*='Agrandir le player']"));
        Assert.assertTrue("Reduire Player butonu çalışmadı!", agrandirPlayer.isDisplayed());
        System.out.println("Player ekranı küçültme işlemi başarıyla gerçekleşti.");
        Thread.sleep(5);
    }

    @And("Kullanıcı Agrandir Player butonuna tıklar ve ekran büyür")
    public void kullanici_agrandir_player_butonuna_tiklar_ve_ekran_buyur() throws InterruptedException {

        // Agrandir Player butonuna tıklama işlemi
        musiquePage.agrandirPlayerClick();
        System.out.println("Agrandir Player butonuna tıklandı.");

        // Player büyütme durumunun doğrulanması
        WebElement reduirePlayer = getCurrentDriver().findElement(By.xpath("//*[@*='Réduire le player']"));
        Assert.assertTrue("Agrandir Player butonu çalışmadı!", reduirePlayer.isDisplayed());
        System.out.println("Player ekranı büyütme işlemi başarıyla gerçekleşti.");
        Thread.sleep(5);
    }
    @Then("Müzik ayar çubuğunu kapatır ve işlemi doğrular")
    public void muzik_ayar_cubugunu_kapatir_ve_islemi_dogrular() throws InterruptedException {
        // Ayar çubuğunu kapatma butonuna tıklama
        musiquePage.fermerPlayerClick();
        System.out.println("Müzik ayar çubuğu kapatma butonuna tıklandı.");

        // Ayar çubuğunun kapandığını doğrulama
        boolean isClosed = getCurrentDriver().findElements(By.xpath("//*[@*='Ayar Çubuğu']")).isEmpty();
        Assert.assertTrue("Müzik ayar çubuğu kapanmadı!", isClosed);
        System.out.println("Müzik ayar çubuğu başarıyla kapatıldı ve doğrulandı.");
        Thread.sleep(5);
    }



}
