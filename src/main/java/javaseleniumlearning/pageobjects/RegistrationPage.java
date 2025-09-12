package javaseleniumlearning.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import javaseleniumlearning.AbstractComponents.AbstractComponent;
import org.openqa.selenium.support.ui.Select;

public class RegistrationPage extends AbstractComponent {

    WebDriver driver;

    public RegistrationPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "firstName")
    WebElement firstNameField;

    @FindBy(id = "lastName")
    WebElement lastNameField;

    @FindBy(id = "userEmail")
    WebElement emailField;

    @FindBy(id = "userMobile")
    WebElement phoneField;

    @FindBy(css = "select[formcontrolname='occupation']")
    WebElement occupationDropdown;

    @FindBy(css = "input[type='radio'][formcontrolname='gender'][value='Male']")
    WebElement genderMaleRadio;

    @FindBy(css = "input[type='radio'][formcontrolname='gender'][value='Female']")
    WebElement genderFemaleRadio;

    @FindBy(id = "userPassword")
    WebElement passwordField;

    @FindBy(id = "confirmPassword")
    WebElement confirmPasswordField;

    @FindBy(css = "input[type='checkbox'][formcontrolname='required']")
    WebElement ageCheckbox;

    @FindBy(id = "login")
    WebElement registerButton;

    public void registerUser(
            String firstName, String lastName, String email, String phone,
            String occupation, String gender, String password, String confirmPassword
    ) {
        firstNameField.sendKeys(firstName);
        lastNameField.sendKeys(lastName);
        emailField.sendKeys(email);
        phoneField.sendKeys(phone);

        Select selectOccupation = new Select(occupationDropdown);
        selectOccupation.selectByVisibleText(occupation);

        if (gender.equalsIgnoreCase("Male")) {
            genderMaleRadio.click();
        } else if (gender.equalsIgnoreCase("Female")) {
            genderFemaleRadio.click();
        }

        passwordField.sendKeys(password);
        confirmPasswordField.sendKeys(confirmPassword);

        if (!ageCheckbox.isSelected()) {
            ageCheckbox.click();
        }

        registerButton.click();
    }
}
