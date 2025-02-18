package steps;

import factory.PageObjectFactory;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pages.CartPage;
import pages.HomePage;
import pages.ProductDetailPage;
import pages.TabletCategoryPage;
import utils.DriverFactory;
import java.time.Duration;

public class TabletSteps {
    private WebDriver driver;
    private PageObjectFactory pageFactory;
    private HomePage homePage;
    private TabletCategoryPage tabletCategoryPage;
    private ProductDetailPage productDetailPage;
    private CartPage cartPage;
    private String productPrice;

    @Before
    public void setUp() {
        if (DriverFactory.getDriver() == null) {
            DriverFactory.setDriver("chrome"); // Varsayılan olarak Chrome başlat
        }
        this.driver = DriverFactory.getDriver();
        this.pageFactory = new PageObjectFactory(driver);
    }

    @Given("kullanıcı {string} sayfasına gider")
    public void kullanıcı_sayfasına_gider(String url) {
        driver.get(url);
        homePage = pageFactory.getHomePage();
    }

    @When("tüm çerezleri kabul eder")
    public void cerezleri_kabul_eder() {
        homePage.acceptCookiesIfVisible();
    }

    @When("kullanıcı Tablet kategorisine geçer")
    public void kullanıcı_kategorisine_geçer() {
        tabletCategoryPage = homePage.goToTabletCategory();
    }

    @When("kullanıcı Marka filtresinden Apple seçimini yapar")
    public void kullanıcı_marka_filtresinden_seçimini_yapar() {
        tabletCategoryPage.filterByBrand();
    }

    @When("kullanıcı Ekran Boyutu filtresinden 13,2 inç seçimini yapar")
    public void kullanıcı_ekran_boyutu_filtresinden_seçimini_yapar() {
        tabletCategoryPage.filterByScreenSize();
    }

    @When("kullanıcı sıralama filtresi kullanılmadan çıkan sonuçlardan en yüksek fiyatlı ürünü seçer")
    public void kullanıcı_sıralama_filtresi_kullanılmadan_çıkan_sonuçlardan_en_yüksek_fiyatlı_ürünü_seçer() {
        productDetailPage = tabletCategoryPage.selectHighestPricedProduct();
        productPrice = productDetailPage.getProductPrice();
    }

    @When("kullanıcı ürün detay sayfasındaki sepete ekle butonuna tıklar")
    public void kullanıcı_ürün_detay_sayfasındaki_butonuna_tıklar() {
        cartPage = productDetailPage.addToCart();
    }

    @Then("sepetteki ürün, ürün detay sayfasındaki fiyat ile aynı olmalıdır")
    public void sepetteki_ürün_ürün_detay_sayfasındaki_fiyat_ile_aynı_olmalıdır() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.urlContains("checkout.hepsiburada.com"));
        String cartPrice = cartPage.getCartProductPrice();
        Assert.assertEquals(cartPrice, productPrice, "🚨 Fiyatlar uyuşmuyor!");
    }
}
