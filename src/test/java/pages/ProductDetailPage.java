package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.LogHelper;
import java.time.Duration;
import org.openqa.selenium.JavascriptExecutor;

public class ProductDetailPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Ürün detay sayfasındaki "Sepete Ekle" butonu
    @FindBy(xpath = "//button[@data-test-id='addToCart']")
    private WebElement addToCartButton;

    // Ürünün fiyat bilgisini içeren element
    @FindBy(xpath = "//div[@data-test-id='default-price']//span")
    private WebElement priceElement;

    // Pop-up içinde görünen "Sepete Git" butonu
    @FindBy(xpath = "//button[contains(text(),'Sepete git')]")
    private WebElement goToCartButton;

    public ProductDetailPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    // Ürün fiyatını döndüren metot.
    public String getProductPrice() {
        LogHelper.info("Ürün detay sayfasından fiyat bilgisi alınıyor.");
        return priceElement.getText().trim();
    }

    public CartPage addToCart() {
        LogHelper.info("📌 Sepete Ekle butonuna tıklanıyor.");
        addToCartButton.click();

        try {
            LogHelper.info("📌 'Sepete Git' butonunun görünmesi bekleniyor.");
            wait.until(ExpectedConditions.visibilityOf(goToCartButton));

            LogHelper.info("📌 'Sepete Git' butonuna başarıyla tıklandı.");
            goToCartButton.click();

            // **Sepet sayfasının yüklenmesini garanti altına al**
            LogHelper.info("📌 Sepet sayfasına yönlendiriliyor, sayfa yüklenmesi bekleniyor...");
            WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(30));
            longWait.until(ExpectedConditions.urlContains("checkout.hepsiburada.com"));

            // **Sayfanın tamamen render olup olmadığını kontrol et**
            new WebDriverWait(driver, Duration.ofSeconds(15))
                    .until(webDriver -> ((JavascriptExecutor) webDriver)
                            .executeScript("return document.readyState").equals("complete"));

            LogHelper.info("✅ Sepet sayfası tamamen yüklendi.");
            return new CartPage(driver);
        } catch (Exception e) {
            LogHelper.error("❌ Sepete Git butonuna tıklanırken hata oluştu: " + e.getMessage());
            throw new RuntimeException("Sepete Git butonu görüntülenemedi veya tıklanamadı.");
        }
    }
}
