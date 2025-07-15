package org.example.utils;

import org.example.base.TestBase;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Duration;

/**
 * Utility class for common WebDriver operations with built-in waits and error handling
 */
public class WebDriverUtils extends TestBase {
    private static final Logger logger = LoggerFactory.getLogger(WebDriverUtils.class);
    private static final int DEFAULT_TIMEOUT = 60;

    /**
     * Waits for page to complete loading
     */
    public static void waitForPageLoad() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT))
                    .until(webDriver -> ((JavascriptExecutor) webDriver)
                            .executeScript("return document.readyState").equals("complete"));
        } catch (Exception e) {
            logger.error("Page load timeout: {}", e.getMessage());
        }
    }

    /**
     * Waits for element to be visible
     * @param element WebElement to wait for
     */
    public static void waitForElementVisible(WebElement element) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT))
                    .until(ExpectedConditions.visibilityOf(element));
        } catch (Exception e) {
            logger.error("Element not visible: {}", e.getMessage());
        }
    }

    /**
     * Waits for element to be clickable
     * @param element WebElement to wait for
     */
    public static void waitForElementClickable(WebElement element) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT))
                    .until(ExpectedConditions.elementToBeClickable(element));
        } catch (Exception e) {
            logger.error("Element not clickable: {}", e.getMessage());
        }
    }

    /**
     * Safely clicks an element with retries and JavaScript fallback
     * @param element WebElement to click
     */
    public static void safeClick(WebElement element) {
        try {
            waitForElementClickable(element);
            element.click();
        } catch (ElementClickInterceptedException e) {
            logger.warn("Click intercepted, trying JavaScript click");
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        } catch (Exception e) {
            logger.error("Failed to click element: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Safely sends keys to an element with proper waits
     * @param element WebElement to send keys to
     * @param text Text to enter
     */
    public static void safeSendKeys(WebElement element, String text) {
        try {
            waitForElementVisible(element);
            element.clear();
            element.sendKeys(text);
        } catch (Exception e) {
            logger.error("Failed to send keys: {}", e.getMessage());
            throw e;
        }
    }
}
