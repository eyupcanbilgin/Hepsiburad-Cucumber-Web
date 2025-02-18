package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class HomePage extends BasePage {
    private Actions actions;

    @FindBy(xpath = "//span[span[text()='Elektronik']]")
    private WebElement electronicsCategory;

    @FindBy(xpath = "//a[text()='Bilgisayar/Tablet']")
    private WebElement computerTabletCategory;

    @FindBy(xpath = "//a[text()='Tablet']")
    private WebElement tabletCategoryLink;

    public HomePage(WebDriver driver) {
        super(driver);
        this.actions = new Actions(driver);
        PageFactory.initElements(driver, this);
    }

    public void acceptCookiesIfVisible() {
        try {
            WebElement acceptButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[@id='onetrust-accept-btn-handler' and contains(text(),'Kabul et')]")
            ));
            acceptButton.click();
            System.out.println("✅ Çerezler başarıyla kabul edildi.");
        } catch (Exception e) {
            System.out.println("⚠ Çerez kabul etme butonu bulunamadı, devam ediliyor.");
        }
    }

    public TabletCategoryPage goToTabletCategory() {
        waitForVisibility(electronicsCategory);
        actions.moveToElement(electronicsCategory).perform();

        waitForVisibility(computerTabletCategory);
        actions.moveToElement(computerTabletCategory).perform();

        waitForClickabilityAndClick(tabletCategoryLink);

        return new TabletCategoryPage(driver);
    }
}
