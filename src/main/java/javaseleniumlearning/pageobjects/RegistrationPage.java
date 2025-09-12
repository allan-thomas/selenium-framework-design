package javaseleniumlearning.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import javaseleniumlearning.AbstractComponents.AbstractComponent;

public class RegistrationPage extends AbstractComponent {

    WebDriver driver;

    public RegistrationPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "#userName")
    WebElement nameField;

    @FindBy(css = "#userEmail")
    WebElement emailField;

    @FindBy(css = "#userPassword")
    WebElement passwordField;

    @FindBy(css = "#register")
    WebElement registerButton;

    @FindBy(css = ".toast-bottom-right")
    WebElement messageElement;

    public void goTo() {
        driver.get("https://rahulshettyacademy.com/client/register");
    }

    public void registerUser(String name, String email, String password) {
        nameField.sendKeys(name);
        emailField.sendKeys(email);
        passwordField.sendKeys(password);
        registerButton.click();
    }

    public String getMessage() {
        waitForWebElementToAppear(messageElement);
        return messageElement.getText();
    }
}
