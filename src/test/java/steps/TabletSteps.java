package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pages.HomePage;
import pages.TabletCategoryPage;
import pages.ProductDetailPage;
import pages.CartPage;
import utils.DriverFactory;

import java.time.Duration;

public class TabletSteps {

    private HomePage homePage;
    private TabletCategoryPage tabletCategoryPage;
    private ProductDetailPage productDetailPage;
    private CartPage cartPage;
    private String productPrice;

    @Given("kullanıcı {string} sayfasına gider")
    public void kullanıcı_sayfasına_gider(String url) {
        if (DriverFactory.getDriver() == null) {
            System.out.println("🚀 Driver NULL, yeni başlatılıyor...");
            DriverFactory.setDriver("chrome");
        }

        DriverFactory.getDriver().get(url);
        homePage = new HomePage(DriverFactory.getDriver());
        System.out.println("✅ HomePage başarıyla başlatıldı: " + (homePage != null));
    }

    @When("tüm çerezleri kabul eder")
    public void cerezleri_kabul_eder() {
        homePage.acceptCookiesIfVisible();
    }

    @When("kullanıcı Tablet kategorisine geçer")
    public void kullanıcı_kategorisine_geçer() {
        if (homePage == null) {
            throw new RuntimeException("🚨 HomePage NULL! Sayfa yüklenmemiş olabilir.");
        }
        tabletCategoryPage = homePage.goToTabletCategory();
    }

    @When("kullanıcı Marka filtresinden Apple seçimini yapar")
    public void kullanıcı_marka_filtresinden_seçimini_yapar() {
        if (tabletCategoryPage == null) {
            throw new RuntimeException("🚨 TabletCategoryPage NULL! Kategoriye geçişte hata olabilir.");
        }
        tabletCategoryPage.filterByBrand();
    }

    @When("kullanıcı Ekran Boyutu filtresinden 13,2inç seçimini yapar")
    public void kullanıcı_ekran_boyutu_filtresinden_seçimini_yapar() {
        if (tabletCategoryPage == null) {
            throw new RuntimeException("🚨 TabletCategoryPage NULL! Kategoriye geçişte hata olabilir.");
        }
        tabletCategoryPage.filterByScreenSize();
    }

    @When("kullanıcı sıralama filtresi kullanılmadan çıkan sonuçlardan en yüksek fiyatlı ürünü seçer")
    public void kullanıcı_sıralama_filtresi_kullanılmadan_çıkan_sonuçlardan_en_yüksek_fiyatlı_ürünü_seçer() {
        if (tabletCategoryPage == null) {
            throw new RuntimeException("🚨 TabletCategoryPage NULL! Kategoriye geçişte hata olabilir.");
        }

        productDetailPage = tabletCategoryPage.selectHighestPricedProduct();
        productPrice = productDetailPage.getProductPrice();

        System.out.println("📌 Seçilen ürün fiyatı: " + productPrice);
    }

    @When("kullanıcı ürün detay sayfasındaki sepete ekle butonuna tıklar")
    public void kullanıcı_ürün_detay_sayfasındaki_butonuna_tıklar() {
        if (productDetailPage == null) {
            throw new RuntimeException("🚨 ProductDetailPage NULL! Ürün detay sayfası açılmamış olabilir.");
        }

        // Sepete ekleme işlemi
        cartPage = productDetailPage.addToCart();

        if (cartPage == null) {
            throw new RuntimeException("🚨 Sepete ekleme başarısız oldu, CartPage NULL döndü!");
        }

        System.out.println("✅ Ürün sepete başarıyla eklendi ve CartPage açıldı.");
    }

    @Then("sepetteki ürün, ürün detay sayfasındaki fiyat ile aynı olmalıdır")
    public void sepetteki_ürün_ürün_detay_sayfasındaki_fiyat_ile_aynı_olmalıdır() {
        try {
            // Sepet sayfasının açıldığını doğrula
            if (cartPage == null) {
                System.out.println("⚠ CartPage NULL geldi, tekrar oluşturuluyor...");
                cartPage = new CartPage(DriverFactory.getDriver());
            }

            // Bekleme ekleyelim, sayfanın tam yüklendiğinden emin olalım
            WebDriverWait wait = new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(15));
            wait.until(ExpectedConditions.urlContains("checkout.hepsiburada.com"));

            // Sepetteki ürünün fiyatını al
            String cartPrice = cartPage.getCartProductPrice();
            System.out.println("📌 Sepetteki ürün fiyatı: " + cartPrice);

            // **Fiyatları normalleştirelim**
            String formattedCartPrice = normalizePrice(cartPrice);
            String formattedProductPrice = normalizePrice(productPrice);

            System.out.println("📌 Normalleştirilmiş Sepet Fiyatı: " + formattedCartPrice);
            System.out.println("📌 Normalleştirilmiş Ürün Detay Fiyatı: " + formattedProductPrice);

            // Ürün fiyatıyla karşılaştır
            Assert.assertEquals(formattedCartPrice, formattedProductPrice, "🚨 Fiyatlar uyuşmuyor!");
            System.out.println("✅ Sepetteki ürün fiyatı doğrulandı.");

        } catch (Exception e) {
            System.out.println("🚨 CartPage kontrol edilirken hata oluştu: " + e.getMessage());
            throw new RuntimeException("CartPage yüklenirken hata oluştu!");
        }
    }

    // **Fiyatları normalize eden metot**
    private String normalizePrice(String price) {
        if (price == null || price.isEmpty()) {
            return "0";
        }
        return price.replaceAll("[^0-9,]", "") // TL, boşluk vb. kaldır
                .replace(",", ".") // Virgülü noktaya çevir
                .trim();
    }
}
