package org.example.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

/**
 * Base class for test automation framework
 * Handles driver initialization and configuration loading
 */
public class TestBase {
    private static final Logger logger = LoggerFactory.getLogger(TestBase.class);
    public static WebDriver driver;
    public static Properties properties;

    public TestBase() {
        try {
            properties = new Properties();
            FileInputStream inputStream = new FileInputStream(
                System.getProperty("user.dir") + "/src/main/resources/application.properties"
            );
            properties.load(inputStream);
        } catch (IOException e) {
            logger.error("Failed to load properties file: {}", e.getMessage());
            throw new RuntimeException("Failed to initialize TestBase", e);
        }
    }

    public static void initialization() {
        String browserName = properties.getProperty("browser.name", "chrome");
        logger.info("Initializing {} browser", browserName);

        try {
            switch (browserName.toLowerCase()) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions options = new ChromeOptions();
                    options.addArguments("--remote-allow-origins=*");
                    options.addArguments("--start-maximized");
                    driver = new ChromeDriver(options);
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                    break;
                default:
                    logger.error("Unsupported browser: {}", browserName);
                    throw new IllegalArgumentException("Unsupported browser: " + browserName);
            }

            driver.manage().window().maximize();
            driver.manage().deleteAllCookies();
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(
                Integer.parseInt(properties.getProperty("explicit.wait", "20"))
            ));
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(
                Integer.parseInt(properties.getProperty("implicit.wait", "10"))
            ));

            String url = properties.getProperty("application.url");
            logger.info("Navigating to URL: {}", url);
            driver.get(url);

        } catch (Exception e) {
            logger.error("Failed to initialize WebDriver: {}", e.getMessage());
            throw new RuntimeException("WebDriver initialization failed", e);
        }
    }
}
