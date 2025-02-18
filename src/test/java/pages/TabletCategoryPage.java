package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.NumberFormat;
import java.text.ParseException;
import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class TabletCategoryPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;

    @FindBy(xpath = "//span[text()='Apple']")
    private WebElement appleBrandFilter;

    @FindBy(xpath = "//span[text()='13,2 inç']")
    private WebElement screenSizeFilter;

    public TabletCategoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.actions = new Actions(driver);
        PageFactory.initElements(driver, this);
    }

    private void scrollToElement(WebElement element) {
        actions.moveToElement(element).perform();
    }

    private void waitForElementToBeVisibleAndClick(WebElement element) {
        WebElement visibleElement = wait.until(ExpectedConditions.visibilityOf(element));
        scrollToElement(visibleElement);
        wait.until(ExpectedConditions.elementToBeClickable(visibleElement)).click();
    }

    public void filterByBrand() {
        System.out.println("Apple filtresi tıklanıyor...");
        waitForElementToBeVisibleAndClick(appleBrandFilter);
    }



    public void filterByScreenSize() {
        try {
            Thread.sleep(2000); // 2 saniye bekleme
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("13.2 inç filtresi tıklanıyor...");
        waitForElementToBeVisibleAndClick(screenSizeFilter);

        // 13.2 inç filtresine tıkladıktan sonra sayfayı yenile
        System.out.println("Filtre uygulandı, sayfa yenileniyor...");
        driver.navigate().refresh();

        // Sayfa tamamen yüklenene kadar bekle
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-test-id='filterbox-item-display-name' and text()='13,2 inç']")));

        System.out.println("13.2 inç filtresi başarıyla uygulandı ve ekranda görünüyor.");
    }



    public ProductDetailPage selectHighestPricedProduct() {
        List<WebElement> productItems = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("ul[class*='productListContent'] > li")));

        if (productItems.isEmpty()) {
            throw new RuntimeException("Ürün listesi bulunamadı veya boş!");
        }

        System.out.println("Toplam ürün sayısı: " + productItems.size());

        WebElement highestPricedProduct = null;
        double highestPrice = 0.0;

        for (WebElement item : productItems) {
            try {
                WebElement priceElement = item.findElement(By.xpath(".//div[@data-test-id='price-current-price']"));
                String priceText = priceElement.getText();
                NumberFormat format = NumberFormat.getInstance(Locale.getDefault());
                Number number = format.parse(priceText.replaceAll("[^0-9,.]", ""));
                double price = number.doubleValue();

                System.out.println("Ürün fiyatı: " + price); // Tüm ürün fiyatlarını loglayalım.

                if (price > highestPrice) {
                    highestPrice = price;
                    highestPricedProduct = item;
                }

            } catch (ParseException | NumberFormatException e) {
                e.printStackTrace();
                System.out.println("Fiyat okunamadı, devam ediliyor...");
            } catch (NoSuchElementException e) {
                System.out.println("Fiyat elementi bulunamadı, ürün atlanıyor...");
            }
        }

        if (highestPricedProduct == null) {
            throw new RuntimeException("Hiçbir ürün fiyatı çözümlenemedi!");
        }

        System.out.println("En yüksek fiyatlı ürün: " + highestPrice);

        // Ürünü görünene kadar kaydır
        actions.moveToElement(highestPricedProduct).perform();
        System.out.println("En yüksek fiyatlı ürün sayfada görüntülendi.");

        // Ürünün bilgilerini logla
        try {
            WebElement productTitle = highestPricedProduct.findElement(By.cssSelector("h3")); // Ürün ismi için
            System.out.println("Seçilen ürün adı: " + productTitle.getText());
        } catch (Exception e) {
            System.out.println("Ürün adı alınamadı.");
        }

        System.out.println("Tıklama işlemi başlıyor...");
        highestPricedProduct.click();
        System.out.println("En yüksek fiyatlı ürüne başarıyla tıklandı.");

        // Yeni sekmeye geçiş yap
        Set<String> windowHandles = driver.getWindowHandles();
        String currentWindow = driver.getWindowHandle();

        for (String window : windowHandles) {
            if (!window.equals(currentWindow)) {
                driver.switchTo().window(window);
                System.out.println("Yeni sekmeye geçildi, test devam ediyor...");
                break;
            }
        }

        return new ProductDetailPage(driver);
    }

}
