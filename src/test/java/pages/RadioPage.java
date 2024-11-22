package pages;

import com.github.javafaker.Faker;
import io.appium.java_client.AppiumBy;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import utils.Driver;
import utils.OS;

import static utils.Driver.getCurrentDriver;

public class RadioPage extends BasePage {

    public RadioPage() {
        super(getCurrentDriver());
        PageFactory.initElements(getCurrentDriver(), this);
    }

    public void aramaYap(String searchTerm) {
        By ara = OS.isAndroid() ?
                AppiumBy.id("com.radiofrance.radio.radiofrance.android:id/search_toolbar_search_edittext") :
                By.xpath("//input[@aria-label='Rechercher un podcast, un épisode, une personnalité...']");
        // Arama kutusuna metin yazma işlemi
        WebElement searchBox = getCurrentDriver().findElement(ara);
        searchBox.clear(); // Eğer kutuda eski bir metin varsa temizle
        searchBox.sendKeys(searchTerm); // "histoire" veya istediğin metni yaz
        searchBox.submit(); // Arama kutusuna yazdıktan sonra, arama yapmak için submit işlemi yapabilirsiniz.

    }

    public void clickBtnRechercher() {
        By btnRecherche = OS.isAndroid() ?
                AppiumBy.xpath("//android.widget.FrameLayout[@content-desc='Search']") : //Android locate
                By.xpath("//span[normalize-space()='Rechercher']"); //Web locate
        getCurrentDriver().findElement(btnRecherche).click();
    }

    public void clickBtnRechercher2() {
        By btnRechercher2 = OS.isAndroid() ?
                AppiumBy.xpath("//android.widget.Button[@content-desc='Search podcasts']") : //Android locate
                By.xpath("//button[normalize-space()='Search podcasts']"); //Web locate
        getCurrentDriver().findElement(btnRechercher2).click();
    }

    public void waitForSearchResults() {
        By resultsLocator = By.xpath(""); // Gerçek locator'ı kullanın.
        waitForElementVisible(resultsLocator); // Sonuçları bekliyoruz.
    }


    public void clikBtnSeConnecter() {
        By btnSeConnecter = OS.isAndroid() ?
                AppiumBy.accessibilityId("Se Connecter") :        //duzeltilecek
                By.xpath("//button[@data-testid='header-login-button']");
        getCurrentDriver().findElement(btnSeConnecter).click();
    }
    public void clikBtnInscription(){
        By btnInscription = OS.isAndroid() ?
                AppiumBy.accessibilityId("Inscription") :        //duzeltilecek
                By.xpath("/html/body/div[2]/footer/div[2]/div[2]/div/div/div/div[2]/div/div/nav/ul/li[2]");
        waitForElementClickable(btnInscription).click();
    }

    public void remplirLeFormPourInscription() {
        Faker faker = new Faker();
        String emailfaker = faker.internet().emailAddress();
        String passwordfaker=faker.internet().password(12,20,true);
        By inputEmail = OS.isAndroid() ?
                AppiumBy.accessibilityId("Inscription") :        //duzeltilecek
                By.xpath("//input[@name='mail']");
        By inputPassword = OS.isAndroid() ?
                AppiumBy.accessibilityId("Inscription") :        //duzeltilecek
                By.xpath("//input[@name='password']");
        sendKeys(inputEmail, emailfaker);
        sendKeys(inputPassword, passwordfaker);
    }

    public void clikRadioBtnPourCondition() {
        By radioButton = OS.isAndroid() ?
                AppiumBy.accessibilityId("Inscription") :        //duzeltilecek
                By.xpath("//input[@name='cgu']");
        waitForElementClickable(radioButton).click();
    }
    public void clikBtnInscrire() {
        By btnSinscrire = OS.isAndroid() ?
                AppiumBy.accessibilityId("Se Connecter") :
                //duzeltilecek
                By.xpath("//button[@data-testid='AccountForm-submit']");
        getCurrentDriver().findElement(btnSinscrire).click();
    }
    public void verificationAvecLocationText(String value,String expectedMessage) {
        String actualMessage = Driver.getCurrentDriver().findElement(By.xpath("//*[text()='" + value + "']")).getText();
        Assert.assertEquals(expectedMessage,actualMessage);
    }



}

