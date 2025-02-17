package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.testng.Assert;
import pages.HomePage;
import pages.TabletCategoryPage;
import pages.ProductDetailPage;
import pages.CartPage;
import utils.DriverFactory;

public class TabletSteps {

    private HomePage homePage;
    private TabletCategoryPage tabletCategoryPage;
    private ProductDetailPage productDetailPage;
    private CartPage cartPage;
    private String productPrice;

    @Given("kullanıcı {string} sayfasına gider")
    public void kullanıcı_sayfasına_gider(String url) {
        DriverFactory.getDriver().get(url);
        homePage = new HomePage(DriverFactory.getDriver());
    }

    @When("kullanıcı {string} kategorisine geçer")
    public void kullanıcı_kategorisine_geçer(String categoryPath) {
        // Örneğin "Tüm Kategoriler -> Elektronik -> Tablet" stringi gönderiliyor.
        // Burada kategori yolunu parse edip ilgili metodu çağırabilirsiniz.
        // Şimdilik HomePage içerisindeki direkt TabletCategoryPage'a yönlendirme metodunu kullanıyoruz.
        tabletCategoryPage = homePage.goToTabletCategory();
    }

    @When("kullanıcı Marka filtresinden {string} seçimini yapar")
    public void kullanıcı_marka_filtresinden_seçimini_yapar(String brand) {
        tabletCategoryPage.filterByBrand(brand);
    }

    @When("kullanıcı Ekran Boyutu filtresinden {string} seçimini yapar")
    public void kullanıcı_ekran_boyutu_filtresinden_seçimini_yapar(String screenSize) {
        tabletCategoryPage.filterByScreenSize(screenSize);
    }

    @When("kullanıcı sıralama filtresi kullanılmadan çıkan sonuçlardan en yüksek fiyatlı ürünü seçer")
    public void kullanıcı_sıralama_filtresi_kullanılmadan_çıkan_sonuçlardan_en_yüksek_fiyatlı_ürünü_seçer() {
        productDetailPage = tabletCategoryPage.selectHighestPricedProduct();
        productPrice = productDetailPage.getProductPrice();
    }

    @When("kullanıcı ürün detay sayfasındaki {string} butonuna tıklar")
    public void kullanıcı_ürün_detay_sayfasındaki_butonuna_tıklar(String buttonText) {
        // buttonText örneğin "Sepete Ekle" olacak.
        // Ürün detay sayfasında butonun text'i veya locator'ı sabit kabul edilerek metodumuz çalışıyor.
        cartPage = productDetailPage.addToCart();
    }

    @Then("sepetteki ürün, ürün detay sayfasındaki fiyat ile aynı olmalıdır")
    public void sepetteki_ürün_ürün_detay_sayfasındaki_fiyat_ile_aynı_olmalıdır() {
        String cartPrice = cartPage.getCartProductPrice();
        Assert.assertEquals(cartPrice, productPrice, "Fiyatlar uyuşmuyor!");
    }
}
