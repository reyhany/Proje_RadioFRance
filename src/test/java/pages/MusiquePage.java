package pages;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import utils.OS;

import static utils.Driver.getCurrentDriver;

public class MusiquePage {

    public void clickBtnMusique() {
        By btnMusique = OS.isAndroid() ?
                AppiumBy.xpath("") : //Android locate
                By.xpath("//span[text()='Musique']"); //Web locate
        getCurrentDriver().findElement(btnMusique).click();
    }
    public void clickEcouterButton(){
        By btnEcouter = OS.isAndroid() ?
                AppiumBy.xpath("") : //Android locate
                By.xpath("//*[@*='replay-button-aod']"); //Web locate
        getCurrentDriver().findElement(btnEcouter).click();
    }

    public void volumeButtonClick(){
        By btnVolume = OS.isAndroid() ?
                AppiumBy.xpath("") : //Android locate
                By.xpath("//*[@title='Volume']"); //Web locate
        getCurrentDriver().findElement(btnVolume).click();
    }

    // Ses kaydırıcısının lokatörü (Web için)
    private By volumeSlider = OS.isAndroid() ?
            AppiumBy.xpath("") : // Android lokatörü
            By.xpath("//*[@id='playerVolume']"); // Web lokatörü

    // Ses kaydırıcısını kullanarak değeri değiştirme (Dikey)
    public void changeVolume(int targetValue) {
        WebElement volumeSliderElement = getCurrentDriver().findElement(volumeSlider);

        // Slider'ı hareket ettirmek için Actions sınıfını kullanıyoruz
        Actions action = new Actions(getCurrentDriver());

        // Kaydırıcıyı istenen değere gitmesi için ayarlıyoruz
        // Burada targetValue kadar dikeyde kaydırma yapıyoruz.
        // Y ekseninde hedef değeri belirleyerek kaydırıyoruz (örneğin: targetValue * 2 px)
        action.dragAndDropBy(volumeSliderElement, 0, targetValue).build().perform();
    }

    // Ses seviyesini artırma (Yukarı kaydırma)
    public void increaseVolume() {
        WebElement volumeSliderElement = getCurrentDriver().findElement(volumeSlider);
        Actions action = new Actions(getCurrentDriver());

        // Kaydırıcıyı yukarı kaydırmak için, dikeyde 10px kaydırıyoruz
        action.dragAndDropBy(volumeSliderElement, 0, -10).build().perform();
    }

    // Ses seviyesini azaltma (Aşağı kaydırma)
    public void decreaseVolume() {
        WebElement volumeSliderElement = getCurrentDriver().findElement(volumeSlider);
        Actions action = new Actions(getCurrentDriver());

        // Kaydırıcıyı aşağı kaydırmak için, dikeyde 10px kaydırıyoruz
        action.dragAndDropBy(volumeSliderElement, 0, 10).build().perform();
    }
}