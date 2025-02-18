package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.text.NumberFormat;
import java.text.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

/**
 * The TabletCategoryPage class represents the tablet category section,
 * where users can filter products and select the most expensive one.
 */
public class TabletCategoryPage extends BasePage {
    private Actions actions;

    /**
     * WebElement for selecting the Apple brand filter.
     */
    @FindBy(xpath = "//span[text()='Apple']")
    private WebElement appleBrandFilter;

    /**
     * WebElement for selecting the 13.2-inch screen size filter.
     */
    @FindBy(xpath = "//span[text()='13,2 inç']")
    private WebElement screenSizeFilter;

    /**
     * List of all product items displayed in the category page.
     */
    @FindBy(css = "ul[class*='productListContent'] > li")
    private List<WebElement> productItems;

    /**
     * Constructor for TabletCategoryPage.
     * Initializes the WebDriver instance and page elements.
     *
     * @param driver The WebDriver instance used to interact with the page.
     */
    public TabletCategoryPage(WebDriver driver) {
        super(driver);
        this.actions = new Actions(driver);
        PageFactory.initElements(driver, this);
    }

    /**
     * Applies the brand filter to display only Apple tablets.
     */
    public void filterByBrand() {
        waitForVisibility(appleBrandFilter);
        waitForClickabilityAndClick(appleBrandFilter);
    }

    /**
     * Applies the screen size filter to display only 13.2-inch tablets.
     * The page is refreshed after applying the filter.
     */
    public void filterByScreenSize() {
        waitForVisibility(screenSizeFilter);
        waitForClickabilityAndClick(screenSizeFilter);
        driver.navigate().refresh();
        waitForPageLoad();
    }

    /**
     * Selects the most expensive tablet available in the filtered product list.
     * If no product prices can be retrieved, an exception is thrown.
     *
     * @return ProductDetailPage instance representing the selected tablet.
     */
    public ProductDetailPage selectHighestPricedProduct() {
        waitForVisibility(productItems.get(0));

        WebElement highestPricedProduct = null;
        double highestPrice = 0.0;

        for (WebElement item : productItems) {
            try {
                WebElement priceElement = item.findElement(By.xpath(".//div[@data-test-id='price-current-price']"));
                waitForVisibility(priceElement); // Wait until the price element is visible
                String priceText = priceElement.getText();
                NumberFormat format = NumberFormat.getInstance(Locale.getDefault());
                Number number = format.parse(priceText.replaceAll("[^0-9,.]", ""));
                double price = number.doubleValue();

                if (price > highestPrice) {
                    highestPrice = price;
                    highestPricedProduct = item;
                }
            } catch (ParseException | NoSuchElementException e) {
                System.out.println("❌ Unable to retrieve price, skipping product...");
            }
        }

        if (highestPricedProduct == null) {
            throw new RuntimeException("⚠️ No product price could be resolved!");
        }

        waitForVisibility(highestPricedProduct);
        waitForClickabilityAndClick(highestPricedProduct);

        // Switch to the new window where the product detail page is opened
        Set<String> windowHandles = driver.getWindowHandles();
        for (String window : windowHandles) {
            driver.switchTo().window(window);
        }

        return new ProductDetailPage(driver);
    }
}
