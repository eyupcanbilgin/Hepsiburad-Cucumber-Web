package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class TabletCategoryPage {

    private WebDriver driver;

    public TabletCategoryPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Belirtilen marka filtresine ait checkbox'ı bulup tıklar.
     *
     * @param brandName Örneğin "Apple"
     */
    public void filterByBrand(String brandName) {
        // Dinamik olarak marka checkbox'unu buluyoruz.
        // Burada kullanılan xpath, checkbox'un etiketinde marka adının geçtiğini varsayar.
        WebElement brandCheckbox = driver.findElement(By.xpath("//label[contains(translate(., 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'), '"
                + brandName.toUpperCase() + "')]"));
        brandCheckbox.click();
    }

    /**
     * Belirtilen ekran boyutu filtresine ait checkbox'ı bulup tıklar.
     *
     * @param screenSize Örneğin "13,2 inç"
     */
    public void filterByScreenSize(String screenSize) {
        // Dinamik olarak ekran boyutu checkbox'unu buluyoruz.
        WebElement screenSizeCheckbox = driver.findElement(By.xpath("//label[contains(.,'" + screenSize + "')]"));
        screenSizeCheckbox.click();
    }

    /**
     * Ürün listesindeki tüm ürünlerden fiyatları karşılaştırıp en yüksek fiyatlı ürünü seçer.
     *
     * @return Seçilen ürünün detay sayfasına yönlendiren ProductDetailPage nesnesi.
     */
    public ProductDetailPage selectHighestPricedProduct() {
        // Ürünlerin listelendiği container'ın locator'ünü güncellemeniz gerekebilir.
        List<WebElement> productItems = driver.findElements(By.cssSelector("div.product-item"));

        if (productItems.isEmpty()) {
            throw new RuntimeException("Ürün listesi bulunamadı veya boş!");
        }

        // Ürünler arasından en yüksek fiyatlı ürünü buluyoruz.
        WebElement highestPricedProduct = productItems.stream()
                .max(Comparator.comparingDouble(item -> {
                    try {
                        // Fiyat bilgisi içeren elementi buluyoruz (örneğin, span.price).
                        WebElement priceElement = item.findElement(By.cssSelector("span.price"));
                        String priceText = priceElement.getText();

                        // Fiyat metninden para birimi, nokta ve boşluk karakterlerini kaldırıyoruz.
                        // Bölge ayarlarını isteğe göre güncelleyebilirsin.
                        NumberFormat format = NumberFormat.getInstance(Locale.getDefault());
                        Number number = format.parse(priceText.replaceAll("[^0-9,\\.]", ""));
                        return number.doubleValue();
                    } catch (ParseException | NumberFormatException e) {
                        e.printStackTrace();
                        return 0.0;
                    }
                })).orElseThrow(() -> new RuntimeException("Hiçbir ürün fiyatı çözümlenemedi!"));

        // En yüksek fiyatlı ürüne tıklıyoruz.
        highestPricedProduct.click();
        return new ProductDetailPage(driver);
    }
}
