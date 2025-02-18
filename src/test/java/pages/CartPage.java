package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class CartPage extends BasePage {
    @FindBy(xpath = "//h1[contains(text(), 'Sepetim')]")
    private WebElement cartHeader;

    @FindBy(xpath = "//div[@id='basket_payedPrice']/span")
    private WebElement cartPriceElement;

    public CartPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        waitForPageLoad();
        waitForVisibility(cartHeader);
        waitForVisibility(cartPriceElement);
    }

    public String getCartProductPrice() {
        waitForVisibility(cartPriceElement);
        return normalizePrice(cartPriceElement.getText().trim());
    }
    private String normalizePrice(String price) {
        return price.replaceAll("[^0-9,.]", "").trim();
    }
}
