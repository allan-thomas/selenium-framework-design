package javaseleniumlearning.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import javaseleniumlearning.AbstractComponents.AbstractComponent;

public class ProductCatalogue extends AbstractComponent {

	WebDriver driver;

	public ProductCatalogue(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));

	@FindBy(css = ".mb-3")
	List<WebElement> products;

	

	By addToCart = By.cssSelector("button.w-10");
	By toasterMsg = By.cssSelector("#toast-container");
	By loadingScreen = By.cssSelector(".ng-animating");

	public List<WebElement> getProductList() {
		return products;
	}

	public WebElement getProductByName(String productName) {
		WebElement prod = products.stream()
				.filter(product -> product.findElement(By.cssSelector("B")).getText().equals(productName)).findFirst()
				.orElse(null);
		return prod;
	}

	public CartPage addProductToCart(String productName) {
		// prod.findElement(By.cssSelector("button.w-10")).click();
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
		WebElement prod = getProductByName(productName);
		prod.findElement(addToCart).click();
//		waitForElementToAppear(toasterMsg); // commented due to flakiness.
		waitForElementToDisappear(loadingScreen);
		return goToCartPage();
//		CartPage cart = new CartPage(driver);

//		driver.findElement(By.xpath("//button[@routerlink='/dashboard/cart']")).click();
//		goToCartPage();
	}
	

}
