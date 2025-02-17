package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.LogHelper;

public class ProductDetailPage {

    private WebDriver driver;

    // Ürün detay sayfasındaki "Sepete Ekle" butonunu temsil eden element.
    @FindBy(css = "button[id='addToCart']")
    private WebElement addToCartButton;

    // Ürünün fiyat bilgisini içeren element.
    @FindBy(css = "span.price")
    private WebElement priceElement;

    public ProductDetailPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Ürün fiyatını döndüren metot.
    public String getProductPrice() {
        LogHelper.info("Ürün detay sayfasından fiyat bilgisi alınıyor.");
        return priceElement.getText().trim();
    }

    // "Sepete Ekle" butonuna tıklayıp CartPage'e yönlendirme yapan metot.
    public CartPage addToCart() {
        LogHelper.info("Sepete Ekle butonuna tıklanıyor.");
        addToCartButton.click();
        return new CartPage(driver);
    }
}
