package org.example.pages;

import org.example.base.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base class for all Page Objects
 * Provides common functionality and WebDriver instance
 */
public class BasePage extends TestBase {
    protected static final Logger logger = LoggerFactory.getLogger(BasePage.class);
    protected static WebDriver driver;

    public BasePage() {
        BasePage.driver = TestBase.driver;
        PageFactory.initElements(driver, this);
    }
}
