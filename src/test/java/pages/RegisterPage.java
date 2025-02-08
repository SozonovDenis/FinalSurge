package pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class RegisterPage {
    static SelenideElement firstNameField = $(By.id("create_first"));
    static SelenideElement lastNameField = $(By.id("create_last"));
    static SelenideElement emailField = $(By.id("create_email"));
    static SelenideElement timeZoneDropdown = $(By.id("create_timezone"));
    static SelenideElement passwordField = $(By.id("password_meter"));
    static SelenideElement secondPasswordField = $(By.id("create_passwordmatch"));
    static SelenideElement createNewAccountButton = $x("//button[@type='submit']");

    public static void openRegisterPage() {
        open("https://log.finalsurge.com/register.cshtml");
    }

    public static void fillRegisterForm(String firstName, String lastName, String email, String timeZone, String password, String secondPassword) {
        firstNameField.setValue(firstName);
        lastNameField.setValue(lastName);
        emailField.setValue(email);
        timeZoneDropdown.click();
        timeZoneDropdown.find(withText(timeZone)).click();
        passwordField.setValue(password);
        secondPasswordField.setValue(secondPassword);
    }

    public static void clickOnCreateNewAccountButton() {
        createNewAccountButton.click();
    }

    public static int countOfRequiredFieldErrors() {
        int count = $$(byXpath("//*[not(contains(@style, 'display: none;')) and contains(text(), 'This field is required.')]")).size();
        return count;
    }

    public static boolean isEmailErrorDisplayed() {
        boolean isEmailErrorDisplayed = $(byXpath("//*[not(contains(@style, 'display: none;')) and contains(text(), 'Please enter a valid email address.')]")).exists();
        return isEmailErrorDisplayed;
    }

    public static String getPasswordErrorMessage() {
        String passwordErrorMessage = $(byXpath("//div[@class='alert alert-error']")).getText();
        return passwordErrorMessage;
    }
}
