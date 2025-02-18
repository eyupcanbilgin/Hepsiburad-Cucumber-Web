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

public class ProductDetailPage extends BasePage {
    private Actions actions;

    @FindBy(xpath = "//button[@data-test-id='addToCart']")
    private WebElement addToCartButton;

    // Ürünün fiyat bilgisini içeren element
    @FindBy(xpath = "(//div[@data-test-id='default-price']//span)[1]")
    private WebElement priceElement;

    @FindBy(xpath = "//button[contains(text(),'Sepete git')]")
    private WebElement goToCartButton;

    public ProductDetailPage(WebDriver driver) {
        super(driver);
        this.actions = new Actions(driver);
        PageFactory.initElements(driver, this);
    }

    public String getProductPrice() {
        waitForVisibility(priceElement);
        return normalizePrice(priceElement.getText().trim());
    }

    public CartPage addToCart() {
        waitForClickabilityAndClick(addToCartButton);
        waitForVisibility(goToCartButton);
        waitForClickabilityAndClick(goToCartButton);
        waitForPageLoad();
        return new CartPage(driver);
    }

    private String normalizePrice(String price) {
        return price.replaceAll("[^0-9,.]", "").trim();
    }
}
