package factory;

import org.openqa.selenium.WebDriver;
import pages.CartPage;
import pages.HomePage;
import pages.ProductDetailPage;
import pages.TabletCategoryPage;

/**
 * PageObjectFactory is responsible for creating instances of page objects.
 * It helps in maintaining a single entry point for initializing different pages
 * and ensures a cleaner, more organized test automation framework.
 */
public class PageObjectFactory {
    private WebDriver driver;

    /**
     * Constructor to initialize the WebDriver instance.
     *
     * @param driver The WebDriver instance that will be shared across pages.
     */
    public PageObjectFactory(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Returns an instance of the HomePage.
     * This page represents the main landing page of the website.
     *
     * @return HomePage object.
     */
    public HomePage getHomePage() {
        return new HomePage(driver);
    }

    /**
     * Returns an instance of the TabletCategoryPage.
     * This page contains a list of available tablets, allowing users to browse and filter them.
     *
     * @return TabletCategoryPage object.
     */
    public TabletCategoryPage getTabletCategoryPage() {
        return new TabletCategoryPage(driver);
    }

    /**
     * Returns an instance of the ProductDetailPage.
     * This page provides detailed information about a selected product, including pricing and availability.
     *
     * @return ProductDetailPage object.
     */
    public ProductDetailPage getProductDetailPage() {
        return new ProductDetailPage(driver);
    }

    /**
     * Returns an instance of the CartPage.
     * This page manages the shopping cart, displaying selected items and total prices.
     *
     * @return CartPage object.
     */
    public CartPage getCartPage() {
        return new CartPage(driver);
    }
}
