package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.Assert.*;
import static utils.Driver.getCurrentDriver;

public class MusiquePage {

    public MusiquePage() {
        PageFactory.initElements(getCurrentDriver(), this);
    }

    @FindBy(xpath = "//span[text()='Musique']")
    public WebElement btnMusique;

    @FindBy(xpath = "//*[@*='replay-button-aod']")
    public WebElement btnEcouter;

    @FindBy(xpath = "//*[@title='Volume']")
    public WebElement btnVolume;

    @FindBy(xpath = "//*[@id='playerVolume']")
    public WebElement volumeSlider;

    @FindBy(xpath = "//*[@title='Vitesse de lecture']")
    public WebElement btnVitesseDeLecture;

    @FindBy(xpath = "//*[@*='Réduire le player']")
    public WebElement btnReduirePlayer;

    @FindBy(xpath = "//*[@*='Agrandir le player']")
    public WebElement btnAgrandirPlayer;

    @FindBy(xpath = "//*[@*='Fermer le player']")
    public WebElement btnFermerPlayer;

    @FindBy(xpath = "//button[@title='Vitesse de lecture']")
    private WebElement currentSpeedButton;

    @FindBy(xpath = "//*[@*='current']")
    private WebElement timeSpan;

    @FindBy(xpath = "//*[@id='playerVolume']")
    private WebElement volumeSliderElement;

    public void clickBtnMusique() {
        btnMusique.click();
    }

    public void clickEcouterButton() {
        btnEcouter.click();
    }

    public void volumeButtonClick() {
        btnVolume.click();
    }

    public void increaseVolume() {
        Actions action = new Actions(getCurrentDriver());
        action.dragAndDropBy(volumeSlider, 0, -10).build().perform();
    }

    public void decreaseVolume() {
        Actions action = new Actions(getCurrentDriver());
        action.dragAndDropBy(volumeSlider, 0, 10).build().perform();
    }

    public void vitesseDeLectureClick() {
        btnVitesseDeLecture.click();
    }

    public void reduirePlayerClick() {
        btnReduirePlayer.click();
    }

    public void agrandirPlayerClick() {
        btnAgrandirPlayer.click();
    }

    public void fermerPlayerClick() {
        btnFermerPlayer.click();
    }

    // Hız seçeneklerini sırayla seç ve doğrula
    public void selectAndVerifySpeedOptions() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(getCurrentDriver(), Duration.ofSeconds(10));

        // Hız ayar butonunu açmak için méthode çağır
        vitesseDeLectureClick();
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
            WebElement currentSpeed = currentSpeedButton;
            String appliedSpeed = currentSpeed.getText();

            // Doğrulama
            if (!appliedSpeed.contains(expectedSpeed)) {
                throw new AssertionError("Hız doğru uygulanmadı: Beklenen = " + expectedSpeed + ", Mevcut = " + appliedSpeed);
            }
            System.out.println("Hız başarıyla değiştirildi ve doğrulandı: " + expectedSpeed);

            // Dropdown menüsünü tekrar aç
            if (i < 6) { // Son seçenekte tekrar açmaya gerek yok
                vitesseDeLectureClick();
                Thread.sleep(1000); // Dropdown menüsünün tekrar açılması için kısa bekleme
            }
        }
    }


    // Müzik çalmaya başladığını doğrulayan metot
    public void verifyMusicIsPlaying() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(getCurrentDriver(), Duration.ofSeconds(15));

        // Zaman göstergesi (current time) öğesinin görünür olmasını bekleyin
        String initialTime = wait.until(ExpectedConditions.visibilityOf(timeSpan)).getText();

        // 5 saniye bekleme
        Thread.sleep(5000); // Alternatif olarak explicit wait kullanabilirsiniz

        String updatedTime = timeSpan.getText();

        // Müzik zamanının değişip değişmediğini kontrol et
        if (initialTime.equals(updatedTime)) {
            throw new AssertionError("Müzik oynatılmaya başlamadı.");
        }

        System.out.println("Müzik başarıyla çalmaya başladı.");
    }


    // Ses seviyesini alacak metod
    public int getCurrentVolume() {
        String volumeValue = volumeSliderElement.getAttribute("aria-valuenow");
        return (int) Double.parseDouble(volumeValue);
    }

    // URL beklemek için metod
    public void waitForUrl(String expectedUrl) {
        WebDriverWait wait = new WebDriverWait(getCurrentDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlToBe(expectedUrl));
        String actualUrl = getCurrentDriver().getCurrentUrl();
        if (!actualUrl.equals(expectedUrl)) {
            throw new AssertionError("Beklenen URL: " + expectedUrl + ", fakat mevcut URL: " + actualUrl);
        }
    }
    public void toggleVolumeButton() throws InterruptedException {
        volumeButtonClick();
        Thread.sleep(5000); // 5 saniye bekle
        volumeButtonClick();
    }

    public void togglePlayerSize(String action) {
        WebDriverWait wait = new WebDriverWait(getCurrentDriver(), Duration.ofSeconds(10));

        if ("reduire".equalsIgnoreCase(action)) {
            // "Réduire le player" butonuna tıkla
            btnReduirePlayer.click();
            System.out.println("Reduire Player butonuna tıklandı.");

            // Player küçültme durumunun doğrulanması
            wait.until(ExpectedConditions.visibilityOf(btnAgrandirPlayer));
            boolean isDisplayed = btnAgrandirPlayer.isDisplayed();
            if (!isDisplayed) {
                throw new AssertionError("Reduire Player butonu çalışmadı!");
            }
            System.out.println("Player ekranı küçültme işlemi başarıyla gerçekleşti.");

        } else if ("agrandir".equalsIgnoreCase(action)) {
            // "Agrandir le player" butonuna tıkla
            btnAgrandirPlayer.click();
            System.out.println("Agrandir Player butonuna tıklandı.");

            // Player büyütme durumunun doğrulanması
            wait.until(ExpectedConditions.visibilityOf(btnReduirePlayer));
            boolean isDisplayed = btnReduirePlayer.isDisplayed();
            if (!isDisplayed) {
                throw new AssertionError("Agrandir Player butonu çalışmadı!");
            }
            System.out.println("Player ekranı büyütme işlemi başarıyla gerçekleşti.");
        } else {
            throw new IllegalArgumentException("Geçersiz işlem türü: " + action);
        }
    }

    public void fermerPlayer() {
        WebDriverWait wait = new WebDriverWait(getCurrentDriver(), Duration.ofSeconds(10));

        // "Fermer le player" butonuna tıklama
        btnFermerPlayer.click();
        System.out.println("Müzik ayar çubuğu kapatma butonuna tıklandı.");

        // Ayar çubuğunun kapandığını doğrulama
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@*='Fermer le player']")));
            System.out.println("Müzik ayar çubuğu başarıyla kapatıldı.");
        } catch (TimeoutException e) {
            throw new AssertionError("Müzik ayar çubuğu kapanmadı!");
        }
    }
    // 15 saniye geri düğmesinin WebElement'i
    @FindBy(xpath = "//*[@*='Reculer la lecture de 15 secondes']")
    private WebElement btn15SaniyeGeri;

    // 30 saniye ileri düğmesinin WebElement'i
    @FindBy(xpath = "//*[@*='Avancer la lecture de 30 secondes']")
    private WebElement btn30SaniyeIleri;

    // "current" süreyi gösteren element
    @FindBy(xpath = "//*[@*='current']")
    public WebElement currentTimeElement;


    // Zaman geri alma işlemi (15 saniye)
    public void rewindTime(int seconds) {
        WebDriverWait wait = new WebDriverWait(getCurrentDriver(), Duration.ofSeconds(10));

        // 15 saniye geri butonuna tıklama
        WebElement btn = seconds == 15 ? btn15SaniyeGeri : btn30SaniyeIleri;
        btn.click();
        System.out.println(seconds + " saniye geri düğmesine tıklandı.");

        // Süreyi kontrol etmeden önce kısa bir bekleme
        try {
            Thread.sleep(3000); // 3 saniye bekleme
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Zamanı doğrulama işlemi
    public boolean verifyTimeChange(String timeBefore) {
        // Süreyi tekrar kontrol et
        String timeAfter = currentTimeElement.getText();
        // Doğrulama: Sürenin geri alınmadığını kontrol et
        return !timeBefore.equals(timeAfter);
    }
    // 15 saniye geri alma butonuna tıklama
    public void click15SecondsBack() {
        btnReduirePlayer.click();
        System.out.println("Kullanıcı 15 saniye geri düğmesine tıkladı.");
    }

    // 30 saniye ileri alma butonuna tıklama
    public void click30SecondsForward() {
        btnAgrandirPlayer.click();
        System.out.println("Kullanıcı 30 saniye ileri düğmesine tıkladı.");
    }

    // Sürenin değişip değişmediğini doğrulama
    public void verifyTimeChanged(String actionDescription) {
        String timeBefore = timeSpan.getText();
        try {
            Thread.sleep(3000); // 3 saniye bekleme
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        String timeAfter = timeSpan.getText();
        assertNotEquals(actionDescription + " başarısız oldu!", timeBefore, timeAfter);
        System.out.println(actionDescription + " başarıyla doğrulandı.");
    }

    // Musique anasayfadaki pause button
    @FindBy(xpath = "//*[@*='Arrêter']")
    public WebElement pauseBtn;

    // pause yaptiktan sonra geri baslatmak icin buton, pause'la ayni aslinda
    @FindBy(xpath = "(//*[@*='Lecture'])[1]")
    public WebElement reprendreBtn;

    // Ecouter plus tard  button
    @FindBy(xpath = "//*[@*='BookmarkButton']")
    public WebElement ecouterPlusTard;

    // sayfanin saginda acilan se connecter menusu
    @FindBy(xpath = "//*[@*='Modal-internalContainer svelte-1qn888q']")
    public WebElement seConnecterMenu;

    // sayfanin saginda acilan se connecter menudeki se connecter butonu
    @FindBy(xpath = "(//span[text()='Se connecter'])[2]")
    public WebElement seConnecterBtn2;

    public void clickPauseButton() throws InterruptedException {
        Thread.sleep(3000);
        pauseBtn.click();
        System.out.println("Pause butonuna basıldı ve müzik durduruldu.");
         // Müzik durdurulma işlemi için bekleme
        Thread.sleep(3000);
    }

    public void verifyMusicIsPaused() {
        String currentTimeBefore = currentTimeElement.getText();
        try {
            Thread.sleep(3000); // Zaman ilerleyip ilerlemediğini kontrol etmek için bekleme
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        String currentTimeAfter = currentTimeElement.getText();
        if (!currentTimeBefore.equals(currentTimeAfter)) {
            throw new AssertionError("Müzik durdurulmadı!");
        }
        System.out.println("Müziğin durdurulduğu başarıyla doğrulandı.");
    }

    public void clickResumeButton() throws InterruptedException {
        reprendreBtn.click();
        System.out.println("Tekrar aynı düğmeye basıldı ve müzik yeniden başlatıldı.");
        Thread.sleep(3000); // Müzik başlatma işlemi için bekleme
    }

    public void verifyMusicIsResumed() {
        String currentTimeBefore = currentTimeElement.getText();
        try {
            Thread.sleep(3000); // Zaman ilerleyip ilerlemediğini kontrol etmek için bekleme
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        String currentTimeAfter = currentTimeElement.getText();
        if (currentTimeBefore.equals(currentTimeAfter)) {
            throw new AssertionError("Müzik yeniden başlamadı!");
        }
        System.out.println("Müziğin yeniden başladığı başarıyla doğrulandı.");
    }

    public void clickEcouterPlusTardButton() throws InterruptedException {
        ecouterPlusTard.click();
        System.out.println("Ecouter Plus Tard butonuna basıldı.");
        Thread.sleep(3000); // Menü açılma işlemi için bekleme
    }

    public void verifySeConnecterMenuOpened() {
        if (!seConnecterMenu.isDisplayed()) {
            throw new AssertionError("Se connecter menüsü açılmadı!");
        }
        System.out.println("Se connecter menüsü başarıyla açıldı.");

        // Se connecter butonunun menü içinde görüntülendiğini doğrulama
        boolean isSeConnecterBtnVisible = seConnecterBtn2.isDisplayed();
        if (!isSeConnecterBtnVisible) {
            throw new AssertionError("Se connecter butonu menüde görüntülenmedi!");
        }
        System.out.println("Se connecter butonu menüde başarıyla görüntülendi.");
    }

    // sayfanin saginda acilan menuyu kapatma butonu
    @FindBy(xpath = "//*[@*='ModalHeader-close svelte-xwavtz']")
    public WebElement menuCloseBtn;

    // sayfanin saginda acilan menudeki baslik
    @FindBy(xpath = "//*[@*='DrawerPlaylist-text qg-tt4 svelte-11xinew']")
    public WebElement textConnectezVous;

    // sayfanin saginda acilan menudeki yazilar
    @FindBy(xpath = "//*[@*='DrawerPlaylist-list g-unstyled-list svelte-11xinew']")
    public WebElement textIn3line;

    // sayfanin saginda acilan menudeki s'inscrire butonu
    @FindBy(xpath = "//*[@*='DrawerPlaylist-signup']")
    public WebElement sinscrireBtn;



    public void closeSeConnecterMenu() {
        if (menuCloseBtn.isDisplayed()) {
            menuCloseBtn.click();
            System.out.println("Se connecter menüsü başarıyla kapatıldı.");
        } else {
            throw new AssertionError("Se connecter menüsü kapatılamadı çünkü menü kapatma butonu görünmüyor.");
        }
    }
    public void verifyInformationTextPresence() {
        if (!textConnectezVous.isDisplayed() || !textIn3line.isDisplayed()) {
            throw new AssertionError("Bilgilendirici yazılar görünmüyor.");
        }
        System.out.println("Bilgilendirici yazılar başarıyla görüntülendi.");
    }
    public void verifyLoginFormDisplayed() {
        WebDriverWait wait = new WebDriverWait(getCurrentDriver(), Duration.ofSeconds(10));
        try {
            // Connexion butonunun aktif modda olduğunu bekle ve doğrula
            WebElement connectionActive = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//button[contains(@class, 'active') and @aria-current='true' and text()='Connexion']")
            ));
            System.out.println("Giriş ekranı başarıyla açıldı: Connexion butonu aktif.");
        } catch (TimeoutException e) {
            throw new AssertionError("Giriş ekranı açılmadı: Connexion butonu aktif değil.", e);
        }

    }
    public void clickSinscrireButton() {
        if (sinscrireBtn.isDisplayed()) {
            sinscrireBtn.click();
            System.out.println("Kayıt ol butonuna tıklandı.");
        } else {
            throw new AssertionError("S'inscrire butonu görünmüyor.");
        }
    }

    public void clickSeConnecterButton() {
        seConnecterBtn2.click();
    }
    public void clickInscrireButton() {
        WebDriverWait wait = new WebDriverWait(getCurrentDriver(), Duration.ofSeconds(10));
        try {
            wait.until(ExpectedConditions.elementToBeClickable(sinscrireBtn)).click();
            System.out.println("S'inscrire butonuna başarıyla tıklandı.");
        } catch (TimeoutException e) {
            throw new AssertionError("S'inscrire butonuna tıklanamadı, buton bulunamadı veya tıklanabilir değil.", e);
        }
    }
    public void verifyRegisterFormDisplayed() {
        WebDriverWait wait = new WebDriverWait(getCurrentDriver(), Duration.ofSeconds(10));
        try {
            // Kayıt ekranı açıldığını doğrulamak için gerekli bir locator veya text kontrol edilir
            WebElement registerForm = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//button[contains(@class, 'active') and @aria-current='true' and text()='Inscription']")
            ));
            System.out.println("Kayıt ekranı başarıyla açıldı: Inscription butonu aktif.");
        } catch (TimeoutException e) {
            throw new AssertionError("Kayıt ekranı açılmadı: Inscription butonu aktif değil.", e);
        }
    }

}

