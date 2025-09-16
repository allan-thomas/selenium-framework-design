package javaseleniumlearning.tests;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javaseleniumlearning.pageobjects.*;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javaseleniumlearning.TestComponents.BaseTest;

/**
 * Test class for submitting orders and verifying order history.
 * Uses data-driven approach with TestNG DataProvider.
 */
public class SubmitOrderTest extends BaseTest {

	/**
	 * Test to submit an order using data from a HashMap.
	 * Registers a user, logs in, adds a product to cart, checks out, and verifies confirmation.
	 *
	 * @param input HashMap containing test data (user details, product, country, etc.)
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Test(dataProvider = "getData", groups = "purchase")
	public void submitOrder(HashMap<String, String> input) throws InterruptedException, IOException {

		// Register a new user using data from input HashMap
		RegistrationPage registrationPage = new RegistrationPage(driver);
		registrationPage.registerUser(
				input.get("firstName"),
				input.get("lastName"),
				input.get("email"),
				input.get("phone"),
				input.get("occupation"),
				input.get("gender"),
				input.get("password"),
				input.get("confirmPassword")
		);

		// Login with provided credentials
		ProductCatalogue productcatalogue = landingpage.loginApplication(input.get("username"), input.get("password"));

		// Get product list and add specified product to cart
		List<WebElement> products = productcatalogue.getProductList();
		CartPage cart = productcatalogue.addProductToCart(input.get("productName"));

		// Verify product is present in cart
		List<WebElement> cartProducts = cart.CartProductsEle();
		boolean match = cart.checkForProductName(input.get("productName"));
		Assert.assertTrue(match);

		// Proceed to checkout
		CheckoutPage checkoutpage = cart.checkoutBtn();

		// Complete checkout and verify confirmation message
		ConfirmationPage confirmationpage = checkoutpage.cartCheckout(input.get("country"));
		String actualText = confirmationpage.getConfirmationMessage();
		Assert.assertTrue(actualText.equalsIgnoreCase("Thankyou for the order."));

		// Print order ID for reference
		System.out.println(confirmationpage.getorderID());
	}

	/**
	 * Test to verify that the ordered product appears in the order history.
	 * Depends on successful execution of submitOrder test.
	 */
	@Test(dependsOnMethods = "submitOrder", groups = "purchase")
	public void OrderHistoryTest() {

		// Login with static credentials
		ProductCatalogue productcatalogue = landingpage.loginApplication("atk@mail.com", "Atk.1881");

		// Navigate to orders page and verify product is listed
		OrderPage orderpage = productcatalogue.goToOrdersPage();
		boolean match = orderpage.checkForProductName("ADIDAS ORIGINAL");
		Assert.assertTrue(match);
	}

	/**
	 * DataProvider for test data.
	 * Reads JSON data and returns it as an array of HashMaps.
	 *
	 * @return Object[][] containing test data for each test run
	 * @throws IOException
	 */
	@DataProvider
	public Object[][] getData() throws IOException {
		// Read test data from JSON file and convert to List of HashMaps
		List<HashMap<String, String>> data = getJsonDataToMap(
				System.getProperty("user.dir") + "\\src\\test\\java\\javaseleniumlearning\\data\\PurchaseOrder.json");
		// Return each HashMap as a separate test input
		return new Object[][] { { data.get(0) }, { data.get(1) } };
	}
}
