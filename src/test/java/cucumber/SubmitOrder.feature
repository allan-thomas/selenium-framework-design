Feature: Purchase the order from Ecommerce Website

Background:
Given I landed on Ecommerce Page

@Regression
Scenario Outline: Positive Test of submitting the order

Given Logged in with username <name> and password <password>
When I add product <productname> from cart
And checkout <productname> and submit the order
Then "Thankyou for the order." message is displayed on Confirmationpage

Examples:
|name          |password  |productname|
|atk@mail.com  |Atk.1881  |ZARA COAT 3|