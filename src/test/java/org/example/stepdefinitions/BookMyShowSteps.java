package org.example.stepdefinitions;

import io.cucumber.java.en.*;
import org.example.pages.BookMyShowPage;
import org.example.base.TestBase;
import org.testng.Assert;

/**
 * Step definitions for BookMyShow functionality
 * Handles the steps for city selection and sign-in process
 */
public class BookMyShowSteps extends TestBase {
    private BookMyShowPage bookMyShowPage;

    /**
     * Initializes the browser and navigates to BookMyShow homepage
     */
    @Given("I am on the BookMyShow homepage")
    public void iAmOnTheBookMyShowHomepage() {
        properties.setProperty("application.url", "https://in.bookmyshow.com/");
        initialization();
        bookMyShowPage = new BookMyShowPage();
    }

    /**
     * Searches and selects the specified city
     * @param cityName Name of the city to select
     */
    @When("I search for {string} in the city selection")
    public void iSearchForCity(String cityName) {
        bookMyShowPage.searchCity(cityName);
    }

    /**
     * Clicks on the SignIn button
     */
    @And("I click on the SignIn button")
    public void iClickOnSignInButton() {
        bookMyShowPage.clickSignIn();
    }

    /**
     * Verifies that the login modal is displayed
     */
    @Then("I should see the login options")
    public void iShouldSeeLoginOptions() {
        Assert.assertTrue(bookMyShowPage.isLoginModalDisplayed(), "Login modal is not displayed");
    }
}
