package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.LogHelper;

import java.time.Duration;

public class CartPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // **Sayfa başlığını temsil eden element**
    @FindBy(xpath = "//h1[contains(text(), 'Sepetim')]")
    private WebElement cartHeader;

    // **Sepetteki ürün fiyatını temsil eden element**
    @FindBy(xpath = "//div[@id='basket_payedPrice']/span")
    private WebElement cartPriceElement;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // 20 saniye bekleme süresi

        PageFactory.initElements(driver, this);

        LogHelper.info("📌 Sepet sayfasına geçiş bekleniyor...");

        try {
            // **URL'nin "checkout" içerdiğini bekle**
            wait.until(ExpectedConditions.urlContains("checkout.hepsiburada.com"));

            // **"Sepetim" başlığının yüklenmesini bekle**
            wait.until(ExpectedConditions.visibilityOf(cartHeader));

            // **Fiyat bilgisinin yüklenmesini bekle**
            wait.until(ExpectedConditions.visibilityOf(cartPriceElement));

            // **Ekstra bekleme ekleyelim**
            Thread.sleep(2000); // 2 saniye bekletelim

            LogHelper.info("✅ CartPage başarıyla yüklendi. URL: " + driver.getCurrentUrl());

        } catch (Exception e) {
            LogHelper.error("❌ CartPage açılırken hata oluştu: " + e.getMessage());
            throw new RuntimeException("🚨 CartPage NULL! Sepet sayfası tam yüklenmemiş olabilir.");
        }
    }

    // **Sepetteki fiyatı al ve doğrula**
    public String getCartProductPrice() {
        try {
            LogHelper.info("📌 Sepet sayfasındaki fiyat elementi bekleniyor...");
            wait.until(ExpectedConditions.visibilityOf(cartPriceElement));

            String cartPrice = cartPriceElement.getText().trim();
            LogHelper.info("✅ Sepetteki ürün fiyatı: " + cartPrice);

            return cartPrice;
        } catch (Exception e) {
            LogHelper.error("❌ Sepet sayfasında fiyat okunamadı! Hata: " + e.getMessage());
            throw new RuntimeException("🚨 Sepetteki fiyat elementi bulunamadı veya yüklenemedi.");
        }
    }
}
