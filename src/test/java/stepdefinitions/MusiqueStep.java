package stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.MusiquePage;
import static org.junit.Assert.assertTrue;


public class MusiqueStep {
    MusiquePage musiquePage = new MusiquePage();


    @Then("Musique başlığına tıklıyorum")
    public void musique_başlığına_tıklıyorum() {
        musiquePage.clickBtnMusique();
        musiquePage.waitForUrl("https://www.radiofrance.fr/ecouter-musique");
    }

    @Then("Sunulan ilk eseri dinlemek için Écouter butonuna tıklarım")
    public void sunulan_ilk_eseri_dinlemek_icin_ecouter_butonuna_tiklarim() {
        musiquePage.clickEcouterButton();
    }

    @Then("Müziğin çalmaya başladığını doğrularım")
    public void müziğin_çalmaya_başladığını_doğrularım() throws InterruptedException {
        // MusiquePage içindeki metodu çağırıyoruz
        musiquePage.verifyMusicIsPlaying();
    }

    @When("Müzik ayar çubuğundaki ses açma-kapama butonuna tıklarım")
    public void müzik_ayar_çubuğundaki_ses_açma_butonuna_tıklarım() throws InterruptedException {
        musiquePage.toggleVolumeButton();
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
        int initialVolume = musiquePage.getCurrentVolume();
        musiquePage.increaseVolume();
        int increasedVolume = musiquePage.getCurrentVolume();
        assertTrue("Ses artırma butonu çalışmadı", increasedVolume > initialVolume);

        musiquePage.decreaseVolume();
        int decreasedVolume = musiquePage.getCurrentVolume();
        assertTrue("Ses azaltma butonu çalışmadı", decreasedVolume < increasedVolume);

        musiquePage.increaseVolume();
        int finalVolume = musiquePage.getCurrentVolume();
        assertTrue("Ses artırma butonu tekrar çalışmadı", finalVolume > decreasedVolume);
    }


    @When("Kullanıcı Vitesse de lecture butonuna tıklar")
    public void kullanıcı_vitesse_de_lecture_butonuna_tıklar(){
        musiquePage.vitesseDeLectureClick();}

    @Then("Kullanıcı hız ayarlarını sırayla seçer ve doğrular")
    public void kullanici_hiz_ayarlarini_sirayla_secer_ve_dogrular() throws InterruptedException {
        musiquePage.selectAndVerifySpeedOptions();


    }
    @When("Kullanıcı Reduire Player butonuna tıklar ve ekran küçülür")
    public void kullanici_reduire_player_butonuna_tiklar_ve_ekran_kuculur() {
        // player ekranini küçült
        musiquePage.togglePlayerSize("reduire");
    }

    @And("Kullanıcı Agrandir Player butonuna tıklar ve ekran büyür")
    public void kullanici_agrandir_player_butonuna_tiklar_ve_ekran_buyur() {
        // player ekranini büyüt
        musiquePage.togglePlayerSize("agrandir");
    }

    @Then("Müzik ayar çubuğunu kapatır ve işlemi doğrular")
    public void muzik_ayar_cubugunu_kapatir_ve_islemi_dogrular() {
        // ayar çubuğunu kapat
        musiquePage.fermerPlayer();
    }


    @When("Kullanıcı 15 saniye geri düğmesine tıklar")
    public void kullanici_15_saniye_geri_dugmesine_tiklar() {
        musiquePage.click15SecondsBack();
    }

    @Then("Müzik süresinin 15 saniye geri alındığını doğrular")
    public void muzik_suresinin_15_saniye_geri_alindigini_dogrular() {
        musiquePage.verifyTimeChanged("15 saniye geri alma");
    }
    @When("Kullanıcı 30 saniye ileri düğmesine tıklar")
    public void kullanici_30_saniye_ileri_dugmesine_tiklar() {
        musiquePage.click30SecondsForward();
    }

    @Then("Müzik süresinin 30 saniye ileri alındığını doğrular")
    public void muzik_suresinin_30_saniye_ileri_alindigini_dogrular() {
        musiquePage.verifyTimeChanged("30 saniye ileri alma");
    }


}
