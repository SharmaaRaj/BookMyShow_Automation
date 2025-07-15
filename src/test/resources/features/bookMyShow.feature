@bookmyshow @regression
Feature: BookMyShow City Selection and SignIn

  @smoke @city-selection
  Scenario: Select City and Navigate to SignIn
    Given I am on the BookMyShow homepage
    When I search for "Coimbatore" in the city selection
    And I click on the SignIn button
    Then I should see the login options
