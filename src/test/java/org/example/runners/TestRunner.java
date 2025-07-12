package org.example.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Test(groups = "cucumber")
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"org.example.stepdefinitions", "org.example.hooks"},
        plugin = {
                "summary",
                "html:target/cucumber-reports/cucumber-html-report.html",
                "json:target/cucumber-reports/cucumber-json-report.json"
        },
        monochrome = true,
        dryRun = false,
        tags = "@smoke"
)
public class TestRunner extends AbstractTestNGCucumberTests {
    // Disable parallel execution to prevent duplicate runs
    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
