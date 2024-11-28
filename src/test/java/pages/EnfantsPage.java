package pages;

import io.appium.java_client.AppiumBy;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import utils.OS;

import static utils.Driver.getCurrentDriver;

public class EnfantsPage extends BasePage {
    public EnfantsPage(){
        super(getCurrentDriver());
        PageFactory.initElements(getCurrentDriver(), this);
    }


    public void btnEnfants() {
        By btnEnfants = OS.isAndroid() ?
                AppiumBy.xpath("//android.widget.Button[@text='SEE MORE']") :  //Android locate
                By.xpath("//a[@class='Button light tertiary large svelte-1weqwpy' and @aria-label='Espace enfants']"); //Web locate
        getCurrentDriver().findElement(btnEnfants).click();
    }

    public void  textPodcastsHistoiresEnfants() {
        By textPodcastsHistoiresEnfants = OS.isAndroid() ?
                AppiumBy.xpath("//android.widget.TextView[@text='Podcasts pour les enfants']") : //Android locate
                By.xpath("//h1[contains(@class,'CoverFocus-title') and text()='Podcasts et histoires pour les enfants']"); //Web locate
        getCurrentDriver().findElement(textPodcastsHistoiresEnfants).click();
    }

    public void Categories() {
        By categories = OS.isAndroid()?
                AppiumBy.xpath("//android.widget.Button[@content-desc='Catégories']") : // Android locate
                By.xpath("//button[contains(@class,'Button') and text()='Catégories']"); // Web locate
        getCurrentDriver().findElement(categories).click();
    }

    public void btnEnfants2() {
        By btnEnfants2 = OS.isAndroid()?
                AppiumBy.xpath("") : // Android locate
                By.xpath("//a[@href='/podcasts/enfants' and text()='Enfants']"); // Web locate
        getCurrentDriver().findElement(btnEnfants2).click();
    }

    public String getCurrentPageUrl() {
        return getCurrentDriver().getCurrentUrl();
    }

    public void scrollToElementByText(String text) {
        String uiSelector = "new UiScrollable(new UiSelector().scrollable(true)).scrollTextIntoView(\"" + text + "\")";
        getCurrentDriver().findElement(AppiumBy.androidUIAutomator(uiSelector));
    }


}
