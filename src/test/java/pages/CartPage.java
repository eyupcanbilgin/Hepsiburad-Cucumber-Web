package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

/**
 * The CartPage class represents the shopping cart page.
 * It provides methods to retrieve cart details such as total price.
 */
public class CartPage extends BasePage {

    /**
     * The header element that confirms the user is on the cart page.
     */
    @FindBy(xpath = "//h1[contains(text(), 'Sepetim')]")
    private WebElement cartHeader;

    /**
     * The element that displays the total price of the items in the cart.
     */
    @FindBy(xpath = "//div[@id='basket_payedPrice']/span")
    private WebElement cartPriceElement;

    /**
     * Constructor for CartPage.
     * It initializes the WebElements and ensures the page has fully loaded.
     *
     * @param driver The WebDriver instance used for interacting with the page.
     */
    public CartPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        waitForPageLoad();
        waitForVisibility(cartHeader); // Ensure the cart page is loaded
        waitForVisibility(cartPriceElement); // Ensure the price element is visible
    }

    /**
     * Retrieves the total price of products in the cart.
     *
     * @return The total price as a String, with unnecessary characters removed.
     */
    public String getCartProductPrice() {
        waitForVisibility(cartPriceElement);
        return normalizePrice(cartPriceElement.getText().trim());
    }

    /**
     * Normalizes the price string by removing non-numeric characters.
     *
     * @param price The raw price string (e.g., "$1,299.99" or "₺15.999,00").
     * @return The cleaned price string containing only numbers and decimal separators.
     */
    private String normalizePrice(String price) {
        return price.replaceAll("[^0-9,.]", "").trim();
    }
}
