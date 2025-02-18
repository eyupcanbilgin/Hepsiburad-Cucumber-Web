package factory;

import org.openqa.selenium.WebDriver;
import pages.CartPage;
import pages.HomePage;
import pages.ProductDetailPage;
import pages.TabletCategoryPage;

public class PageObjectFactory {
    private WebDriver driver;

    public PageObjectFactory(WebDriver driver) {
        this.driver = driver;
    }

    public HomePage getHomePage() {
        return new HomePage(driver);
    }

    public TabletCategoryPage getTabletCategoryPage() {
        return new TabletCategoryPage(driver);
    }

    public ProductDetailPage getProductDetailPage() {
        return new ProductDetailPage(driver);
    }

    public CartPage getCartPage() {
        return new CartPage(driver);
    }
}
