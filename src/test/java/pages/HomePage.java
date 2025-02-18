package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.LogHelper;

/**
 * The HomePage class represents the main landing page of the website.
 * It contains methods to interact with categories and navigate to specific sections.
 */
public class HomePage extends BasePage {
    private Actions actions;

    /**
     * Represents the "Electronics" category on the homepage.
     */
    @FindBy(xpath = "//span[span[text()='Elektronik']]")
    private WebElement electronicsCategory;

    /**
     * Represents the "Computer/Tablet" subcategory under Electronics.
     */
    @FindBy(xpath = "//a[text()='Bilgisayar/Tablet']")
    private WebElement computerTabletCategory;

    /**
     * Represents the "Tablet" category link, which leads to the tablet product listings.
     */
    @FindBy(xpath = "//a[text()='Tablet']")
    private WebElement tabletCategoryLink;

    /**
     * Constructor for HomePage.
     * Initializes the WebDriver and sets up interactions.
     *
     * @param driver The WebDriver instance used to interact with the page.
     */
    public HomePage(WebDriver driver) {
        super(driver);
        this.actions = new Actions(driver);
        PageFactory.initElements(driver, this);
    }

    /**
     * Accepts cookies if the cookie consent popup is displayed.
     * This prevents any blocking popups from interfering with test execution.
     */
    public void acceptCookiesIfVisible() {
        try {
            WebElement acceptButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[@id='onetrust-accept-btn-handler' and contains(text(),'Kabul et')]")
            ));
            acceptButton.click();
            System.out.println("✅ Cookies have been accepted successfully.");
        } catch (Exception e) {
            System.out.println("⚠ No cookie accept button found, proceeding without action.");
        }
    }

    /**
     * Navigates to the Tablet category by hovering over menu items.
     *
     * @return TabletCategoryPage instance, representing the tablet product listings page.
     */
    public TabletCategoryPage goToTabletCategory() {
        waitForVisibility(electronicsCategory);
        LogHelper.info("Hovering over the Electronics category...");
        actions.moveToElement(electronicsCategory).perform();

        waitForVisibility(computerTabletCategory);
        LogHelper.info("Hovering over the Computer/Tablet category...");
        actions.moveToElement(computerTabletCategory).perform();

        LogHelper.info("Clicking on the Tablet category...");
        waitForClickabilityAndClick(tabletCategoryLink);

        return new TabletCategoryPage(driver);
    }
}
