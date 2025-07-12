package org.example.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.example.utils.WebDriverUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

/**
 * Page Object class for BookMyShow homepage functionality
 * Handles city selection and sign-in operations
 */
public class BookMyShowPage extends BasePage {

    // City Selection Elements
    @FindBy(css = "input[placeholder='Search for your city']")
    private WebElement citySearchInput;

    @FindBy(css = "span.cXklvo")
    private WebElement citySearchResult;

    // Sign In Elements
    @FindBy(xpath = "//div[text()='Sign in']")
    private WebElement signInButton;

    public BookMyShowPage() {
        PageFactory.initElements(driver, this);
    }

    /**
     * Searches for and selects a city from the city selection modal
     * @param cityName Name of the city to search and select
     */
    public void searchCity(String cityName) {
        WebDriverUtils.waitForPageLoad();
        WebDriverUtils.waitForElementVisible(citySearchInput);
        WebDriverUtils.safeSendKeys(citySearchInput, cityName);

        try {
            Thread.sleep(1000); // Wait for search results to populate
            WebDriverUtils.waitForElementVisible(citySearchResult);
            WebDriverUtils.safeClick(citySearchResult);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Clicks the Sign In button
     */
    public void clickSignIn() {
        try {
            Thread.sleep(1500); // Wait for page to stabilize after city selection
            WebDriverUtils.waitForElementClickable(signInButton);
            WebDriverUtils.safeClick(signInButton);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Verifies if the login modal is displayed
     * @return true if login modal is displayed, false otherwise
     */
    public boolean isLoginModalDisplayed() {
        try {
            Thread.sleep(1000); // Wait for modal animation
            By modalLocator = By.cssSelector("div[class*='Modal'], div[class*='modal']");
            WebDriverUtils.waitForDynamicElement(modalLocator, 5);
            return driver.findElement(modalLocator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
