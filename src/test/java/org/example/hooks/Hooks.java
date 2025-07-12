package org.example.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.example.base.TestBase;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Hooks extends TestBase {
    private static final Logger logger = LoggerFactory.getLogger(Hooks.class);

    @Before
    public void setUp(Scenario scenario) {
        logger.info("Starting scenario: {}", scenario.getName());
        initialization();
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            // Take screenshot
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "Failure Screenshot");

            // Log failure information
            logger.error("Scenario failed: {}", scenario.getName());
            String failureMessage = scenario.getStatus().name() + " - Check screenshot for details";
            System.err.println("\n[FAILED] " + scenario.getName() + "\nReason: " + failureMessage + "\n");
        } else {
            System.out.println("\n[PASSED] " + scenario.getName() + "\n");
        }

        if (driver != null) {
            driver.quit();
        }
    }
}
