
Feature: Error Validation

@ErrorValidation
Scenario Outline: Negative Test of Logging in

Given I landed on Ecommerce Page
When  Logged in with username <name> and password <password>
Then "Incorrect email password." message is displayed on Landingpage

Examples:
|name          |password  |
|atk@mail.com  |At.1881   |