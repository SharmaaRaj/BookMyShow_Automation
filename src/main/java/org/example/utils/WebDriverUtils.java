package org.example.utils;

import org.example.base.TestBase;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class WebDriverUtils extends TestBase {

    public static WebDriverWait wait;
    private static final int MAX_RETRIES = 3;
    private static final Duration INITIAL_TIMEOUT = Duration.ofSeconds(10);

    /**
     * Waits for element to be visible with retry logic
     * @param element WebElement to wait for
     * @throws TimeoutException if element is not visible after all retries
     */
    public static void waitForElementVisible(WebElement element) {
        int retryCount = 0;
        Duration timeout = INITIAL_TIMEOUT;
        Exception lastException = null;

        while (retryCount < MAX_RETRIES) {
            try {
                wait = new WebDriverWait(driver, timeout);
                wait.until(ExpectedConditions.visibilityOf(element));
                return;
            } catch (Exception e) {
                lastException = e;
                retryCount++;
                timeout = timeout.multipliedBy(2); // Exponential backoff
                System.out.println("Retry " + retryCount + " for element visibility");
            }
        }
        throw new TimeoutException("Element not visible after " + MAX_RETRIES + " retries. Last error: " + lastException.getMessage());
    }

    /**
     * Waits for element to be clickable with retry logic
     * @param element WebElement to wait for
     * @throws TimeoutException if element is not clickable after all retries
     */
    public static void waitForElementClickable(WebElement element) {
        int retryCount = 0;
        Duration timeout = INITIAL_TIMEOUT;
        Exception lastException = null;

        while (retryCount < MAX_RETRIES) {
            try {
                wait = new WebDriverWait(driver, timeout);
                wait.until(ExpectedConditions.elementToBeClickable(element));
                return;
            } catch (Exception e) {
                lastException = e;
                retryCount++;
                timeout = timeout.multipliedBy(2); // Exponential backoff
                System.out.println("Retry " + retryCount + " for element clickable");
            }
        }
        throw new TimeoutException("Element not clickable after " + MAX_RETRIES + " retries. Last error: " + lastException.getMessage());
    }

    /**
     * Safely clicks on an element with retry logic
     * @param element WebElement to click
     */
    public static void safeClick(WebElement element) {
        int retryCount = 0;
        Exception lastException = null;

        while (retryCount < MAX_RETRIES) {
            try {
                waitForElementClickable(element);
                element.click();
                return;
            } catch (ElementClickInterceptedException e) {
                try {
                    JavascriptExecutor js = (JavascriptExecutor) driver;
                    js.executeScript("arguments[0].click();", element);
                    return;
                } catch (Exception jsException) {
                    lastException = jsException;
                }
            } catch (Exception e) {
                lastException = e;
            }
            retryCount++;
            System.out.println("Retry " + retryCount + " for clicking element");
        }
        throw new RuntimeException("Failed to click element after " + MAX_RETRIES + " retries. Last error: " + lastException.getMessage());
    }

    /**
     * Safely sends keys to an element with retry logic
     * @param element WebElement to send keys to
     * @param text Text to enter
     */
    public static void safeSendKeys(WebElement element, String text) {
        int retryCount = 0;
        Exception lastException = null;

        while (retryCount < MAX_RETRIES) {
            try {
                waitForElementVisible(element);
                element.clear();
                element.sendKeys(text);
                return;
            } catch (Exception e) {
                lastException = e;
                retryCount++;
                System.out.println("Retry " + retryCount + " for sending keys");
            }
        }
        throw new RuntimeException("Failed to send keys after " + MAX_RETRIES + " retries. Last error: " + lastException.getMessage());
    }

    public static String safeGetText(WebElement element) {
        waitForElementVisible(element);
        return element.getText();
    }

    public static boolean isElementDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    public static void scrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public static void waitForDynamicElement(By locator, int timeoutInSeconds) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public static void waitAndClickDynamicElement(String xpath, int timeoutInSeconds) {
        try {
            By locator = By.xpath(xpath);
            wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", element);
        } catch (TimeoutException e) {
            throw new RuntimeException("Element not clickable: " + xpath, e);
        }
    }

    public static void refreshPage() {
        driver.navigate().refresh();
    }

    public static void waitForPageLoad() {
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(webDriver -> ((JavascriptExecutor) webDriver)
            .executeScript("return document.readyState").equals("complete"));
    }
}
