package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    private WebDriver driver;

    // "Tüm Kategoriler" menüsünü temsil eden element
    @FindBy(css = "a[title='Tüm Kategoriler']")
    private WebElement allCategoriesMenu;

    // Elektronik kategorisinin açıldığı element (örnek locator, güncellenebilir)
    @FindBy(xpath = "//span[contains(text(),'Elektronik')]")
    private WebElement electronicsCategory;

    // Tablet kategorisini temsil eden link (örnek locator, güncellenebilir)
    @FindBy(xpath = "//a[contains(text(),'Tablet')]")
    private WebElement tabletCategoryLink;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Tüm kategorilerden Tablet kategorisine yönlendiren metot
    public TabletCategoryPage goToTabletCategory() {
        // "Tüm Kategoriler" menüsüne tıklıyoruz
        allCategoriesMenu.click();

        // Elektronik kategorisini seçiyoruz (gerekirse uygun beklemeler ekleyin)
        electronicsCategory.click();

        // Tablet kategorisi linkine tıklıyoruz
        tabletCategoryLink.click();

        // TabletCategoryPage'e yönlendiriyoruz
        return new TabletCategoryPage(driver);
    }
}
