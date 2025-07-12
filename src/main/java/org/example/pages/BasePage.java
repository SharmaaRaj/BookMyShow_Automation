package org.example.pages;

import org.example.base.TestBase;
import org.example.utils.WebDriverUtils;
import org.openqa.selenium.support.PageFactory;

public class BasePage extends TestBase {
    protected WebDriverUtils webDriverUtils;

    public BasePage() {
        PageFactory.initElements(driver, this);
        webDriverUtils = new WebDriverUtils();
    }
}
