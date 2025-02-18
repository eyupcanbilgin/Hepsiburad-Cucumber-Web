package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {

    private WebDriver driver;
    private Actions actions;
    private WebDriverWait wait;

    // Elektronik kategorisinin açıldığı element
    @FindBy(xpath = "//span[span[text()='Elektronik']]")
    private WebElement electronicsCategory;

    // Bilgisayar/Tablet kategorisi
    @FindBy(xpath = "//a[text()='Bilgisayar/Tablet']")
    private WebElement computerTabletCategory;

    // Tablet kategorisini temsil eden link
    @FindBy(xpath = "//a[text()='Tablet']")
    private WebElement tabletCategoryLink;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.actions = new Actions(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void acceptCookiesIfVisible() {
        try {
            By cookieAcceptButton = By.xpath("//button[@id='onetrust-accept-btn-handler' and contains(text(),'Kabul et')]");
            WebElement acceptButton = new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.elementToBeClickable(cookieAcceptButton));

            acceptButton.click();
            System.out.println("✅ Çerezler başarıyla kabul edildi.");
        } catch (Exception e) {
            System.out.println("⚠ Çerez kabul etme butonu bulunamadı, devam ediliyor.");
        }
    }



    // Tüm kategorilerden Tablet kategorisine yönlendiren metot
    public TabletCategoryPage goToTabletCategory() {

        // Elektronik kategorisinin üzerine geliyoruz
        WebElement electronics = wait.until(ExpectedConditions.visibilityOf(electronicsCategory));
        actions.moveToElement(electronics).perform();

        // Bilgisayar/Tablet kategorisinin üzerine geliyoruz
        WebElement computerTablet = wait.until(ExpectedConditions.visibilityOf(computerTabletCategory));
        actions.moveToElement(computerTablet).perform();

        // Tablet kategorisi linkine tıklıyoruz
        WebElement tablet = wait.until(ExpectedConditions.elementToBeClickable(tabletCategoryLink));
        tablet.click();

        // TabletCategoryPage'e yönlendiriyoruz
        return new TabletCategoryPage(driver);
    }
}
