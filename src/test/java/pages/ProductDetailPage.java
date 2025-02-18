package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.Locale;
import java.util.Set;
import java.text.NumberFormat;
import java.text.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

/**
 * The ProductDetailPage class represents the product detail page where users can view product information
 * and add the product to their shopping cart.
 */
public class ProductDetailPage extends BasePage {
    private Actions actions;

    /**
     * The button used to add the product to the shopping cart.
     */
    @FindBy(xpath = "//button[@data-test-id='addToCart']")
    private WebElement addToCartButton;

    /**
     * The element that displays the product price.
     */
    @FindBy(xpath = "(//div[@data-test-id='default-price']//span)[1]")
    private WebElement priceElement;

    /**
     * The button that redirects the user to the shopping cart after adding a product.
     */
    @FindBy(xpath = "//button[contains(text(),'Sepete git')]")
    private WebElement goToCartButton;

    /**
     * Constructor for ProductDetailPage.
     * It initializes the WebDriver and prepares the page elements.
     *
     * @param driver The WebDriver instance used to interact with the page.
     */
    public ProductDetailPage(WebDriver driver) {
        super(driver);
        this.actions = new Actions(driver);
        PageFactory.initElements(driver, this);
    }

    /**
     * Retrieves the price of the product displayed on the page.
     *
     * @return The cleaned product price as a String.
     */
    public String getProductPrice() {
        waitForVisibility(priceElement);
        return normalizePrice(priceElement.getText().trim());
    }

    /**
     * Adds the product to the shopping cart and navigates to the cart page.
     *
     * @return CartPage instance representing the shopping cart.
     */
    public CartPage addToCart() {
        waitForClickabilityAndClick(addToCartButton);
        waitForVisibility(goToCartButton);
        waitForClickabilityAndClick(goToCartButton);
        waitForPageLoad();
        return new CartPage(driver);
    }

    /**
     * Cleans the price string by removing any non-numeric characters.
     *
     * @param price The raw price string that may contain currency symbols.
     * @return The cleaned price string containing only numbers and decimal separators.
     */
    private String normalizePrice(String price) {
        return price.replaceAll("[^0-9,.]", "").trim();
    }
}
