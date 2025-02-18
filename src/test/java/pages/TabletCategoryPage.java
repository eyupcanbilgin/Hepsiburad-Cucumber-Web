package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.text.NumberFormat;
import java.text.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

public class TabletCategoryPage extends BasePage {
    private Actions actions;

    @FindBy(xpath = "//span[text()='Apple']")
    private WebElement appleBrandFilter;

    @FindBy(xpath = "//span[text()='13,2 inç']")
    private WebElement screenSizeFilter;

    @FindBy(css = "ul[class*='productListContent'] > li")
    private List<WebElement> productItems;

    public TabletCategoryPage(WebDriver driver) {
        super(driver);
        this.actions = new Actions(driver);
        PageFactory.initElements(driver, this);
    }

    public void filterByBrand() {
        waitForVisibility(appleBrandFilter);
        waitForClickabilityAndClick(appleBrandFilter);
    }

    public void filterByScreenSize() {
        waitForVisibility(screenSizeFilter);
        waitForClickabilityAndClick(screenSizeFilter);
        driver.navigate().refresh();
        waitForPageLoad();
    }

    public ProductDetailPage selectHighestPricedProduct() {
        waitForVisibility(productItems.get(0));

        WebElement highestPricedProduct = null;
        double highestPrice = 0.0;

        for (WebElement item : productItems) {
            try {
                WebElement priceElement = item.findElement(By.xpath(".//div[@data-test-id='price-current-price']"));
                waitForVisibility(priceElement); // Element görünür olana kadar bekle
                String priceText = priceElement.getText();
                NumberFormat format = NumberFormat.getInstance(Locale.getDefault());
                Number number = format.parse(priceText.replaceAll("[^0-9,.]", ""));
                double price = number.doubleValue();

                if (price > highestPrice) {
                    highestPrice = price;
                    highestPricedProduct = item;
                }
            } catch (ParseException | NoSuchElementException e) {
                System.out.println("❌ Fiyat okunamadı, ürün atlanıyor...");
            }
        }

        if (highestPricedProduct == null) {
            throw new RuntimeException("⚠️ Hiçbir ürün fiyatı çözümlenemedi!");
        }

        waitForVisibility(highestPricedProduct);
        waitForClickabilityAndClick(highestPricedProduct);

        Set<String> windowHandles = driver.getWindowHandles();
        for (String window : windowHandles) {
            driver.switchTo().window(window);
        }

        return new ProductDetailPage(driver);
    }
}
