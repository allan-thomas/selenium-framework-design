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

public class SubmitOrderTest extends BaseTest {

	@Test(dataProvider = "getData", groups = "purchase")
	public void submitOrder(HashMap<String, String> input) throws InterruptedException, IOException {

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

		ProductCatalogue productcatalogue = landingpage.loginApplication(input.get("username"), input.get("password"));

		List<WebElement> products = productcatalogue.getProductList();
		CartPage cart = productcatalogue.addProductToCart(input.get("productName"));

		List<WebElement> cartProducts = cart.CartProductsEle();
		boolean match = cart.checkForProductName(input.get("productName"));
		Assert.assertTrue(match);
		CheckoutPage checkoutpage = cart.checkoutBtn();

		ConfirmationPage confirmationpage = checkoutpage.cartCheckout(input.get("country"));
		String actualText = confirmationpage.getConfirmationMessage();
		Assert.assertTrue(actualText.equalsIgnoreCase("Thankyou for the order."));
		System.out.println(confirmationpage.getorderID());

	}

	@Test(dependsOnMethods = "submitOrder", groups = "purchase")
	public void OrderHistoryTest() {

		ProductCatalogue productcatalogue = landingpage.loginApplication("atk@mail.com", "Atk.1881");
		// boolean match =
		// productcatalogue.goToOrdersPage().checkForProductName(productName); <- this
		// is also correct
		OrderPage orderpage = productcatalogue.goToOrdersPage();
		boolean match = orderpage.checkForProductName("ADIDAS ORIGINAL");
		Assert.assertTrue(match);

	}

	@DataProvider
	public Object[][] getData() throws IOException {

//		HashMap<String, String> map = new HashMap<String, String>();
//		map.put("username", "atk@mail.com");
//		map.put("password", "Atk.1881");
//		map.put("productName", "ZARA COAT 3");
//		map.put("country", "indi");
		List<HashMap<String, String>> data = getJsonDataToMap(
				System.getProperty("user.dir") + "\\src\\test\\java\\javaseleniumlearning\\data\\PurchaseOrder.json");
		return new Object[][] { { data.get(0) }, {data.get(1) } }; // <- the
																												// second
																												// set
																												// of
																												// input
																												// can
																												// be
																												// the
																												// data
																												// of
																												// another
																												// user
																												// login
	}
	
	

}
