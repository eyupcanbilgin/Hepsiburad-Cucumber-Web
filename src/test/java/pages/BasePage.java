package pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

/**
 * BasePage serves as the foundation for all page classes.
 * It provides commonly used WebDriver utilities to enhance code reusability.
 */
public abstract class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    /**
     * Constructor initializes WebDriver and WebDriverWait for child classes.
     * It also initializes all WebElements using PageFactory.
     *
     * @param driver The WebDriver instance that will interact with the webpage.
     */
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    /**
     * Waits for an element to be visible and returns it.
     *
     * @param element The WebElement to wait for.
     * @return The visible WebElement.
     */
    protected WebElement waitForVisibility(WebElement element) {
        try {
            return wait.until(ExpectedConditions.visibilityOf(element));
        } catch (TimeoutException e) {
            System.out.println("❌ Element did not become visible: " + element);
            throw e;
        }
    }

    /**
     * Waits for an element to be clickable and then clicks on it.
     *
     * @param element The WebElement to click.
     */
    protected void waitForClickabilityAndClick(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    /**
     * Waits for the entire page to fully load.
     */
    protected void waitForPageLoad() {
        wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }

    /**
     * Retrieves the current page title.
     *
     * @return The title of the page as a String.
     */
    public String getPageTitle() {
        return driver.getTitle();
    }

    /**
     * Scrolls the page to make a specific element visible in the viewport.
     *
     * @param element The WebElement to scroll to.
     */
    protected void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }
}
