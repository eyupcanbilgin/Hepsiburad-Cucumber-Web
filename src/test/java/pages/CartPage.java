package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CartPage {

    private WebDriver driver;

    // Sepetteki ürünün fiyatını temsil eden element.
    // Locator'ı, Hepsiburada'nın sepet sayfasına göre güncellemen gerekebilir.
    @FindBy(css = "div.cart-price, span.cart-price")
    private WebElement cartPriceElement;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String getCartProductPrice() {
        return cartPriceElement.getText().trim();
    }
}
