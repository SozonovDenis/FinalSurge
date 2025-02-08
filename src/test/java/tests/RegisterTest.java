package tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.RegisterPage;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


public class RegisterTest extends BaseTest {

    @DataProvider(name = "UnfilledRegisterData")
    public Object[][] getUnfilledRegisterData() {
        return new Object[][]{
                {"", "Sozonov", "ab@cd.ru", "(GMT+04:00) Moscow, St. Petersburg, Volgograd", "53523", "53523", 1},
                {"", "", "", "Select...", "", "", 6},
        };
    }

    @Test(testName = "Проверка вывода ошибки незаполненых полей при регистрации", dataProvider = "UnfilledRegisterData")
    public void unfilledRegister(String firstName, String lastName, String email, String timeZone, String password,
                                 String secondPassword, int expectedCountOfRequiredFieldErrors) {
        RegisterPage.openRegisterPage();
        RegisterPage.fillRegisterForm(firstName, lastName, email, timeZone, password, secondPassword);
        RegisterPage.clickOnCreateNewAccountButton();
        assertEquals(RegisterPage.countOfRequiredFieldErrors(), expectedCountOfRequiredFieldErrors);
    }

    @DataProvider(name = "IncorrectEmailRegisterData")
    public Object[][] getIncorrectEmailRegisterData() {
        return new Object[][]{
                {"Denis", "Sozonov", "ab@@cd.ru", "(GMT+04:00) Moscow, St. Petersburg, Volgograd", "53523", "53523"},
                {"Denis", "Sozonov", "ab@.ru", "(GMT+04:00) Moscow, St. Petersburg, Volgograd", "53523", "53523"},
                {"Denis", "Sozonov", "a", "(GMT+04:00) Moscow, St. Petersburg, Volgograd", "53523", "53523"},
                {"Denis", "Sozonov", "@cd.ru", "(GMT+04:00) Moscow, St. Petersburg, Volgograd", "53523", "53523"},
        };
    }

    @Test(testName = "Проверка вывода ошибки некорректного емейла при регистрации", dataProvider = "IncorrectEmailRegisterData")
    public void incorrectEmailRegisterTest(String firstName, String lastName, String email, String timeZone,
                                           String password, String secondPassword) {
        RegisterPage.openRegisterPage();
        RegisterPage.fillRegisterForm(firstName, lastName, email, timeZone, password, secondPassword);
        RegisterPage.clickOnCreateNewAccountButton();
        assertTrue(RegisterPage.isEmailErrorDisplayed());
    }

    @DataProvider(name = "WeakPasswordRegisterData")
    public Object[][] weakPasswordRegisterData() {
        return new Object[][]{
                {"Denis", "Sozonov", "ab@cd.ru", "(GMT+04:00) Moscow, St. Petersburg, Volgograd", "ABcd56", "ABcd56"},
                {"Denis", "Sozonov", "ab@cd.ru", "(GMT+04:00) Moscow, St. Petersburg, Volgograd", "ABCD5678", "ABCD5678"},
                {"Denis", "Sozonov", "ab@cd.ru", "(GMT+04:00) Moscow, St. Petersburg, Volgograd", "abvd5678", "abvd5678"},
                {"Denis", "Sozonov", "ab@cd.ru", "(GMT+04:00) Moscow, St. Petersburg, Volgograd", "abvdABCD", "abvdABCD"},
        };
    }

    @Test(testName = "Проверка вывода ошибки некорректного емейла при регистрации", dataProvider = "WeakPasswordRegisterData")
    public void weakPasswordRegisterTest(String firstName, String lastName, String email, String timeZone,
                                         String password, String secondPassword) {
        RegisterPage.openRegisterPage();
        RegisterPage.fillRegisterForm(firstName, lastName, email, timeZone, password, secondPassword);
        RegisterPage.clickOnCreateNewAccountButton();
        assertEquals(RegisterPage.getPasswordErrorMessage(),
                "Error: *Please enter a Password value with at least one number, lower-case letter, and upper-case letter between 7 and 15 characters in length.");
    }

    @DataProvider(name = "DifferentPasswordsRegisterData")
    public Object[][] differentPasswordRegisterData() {
        return new Object[][]{
                {"Denis", "Sozonov", "ab@cd.ru", "(GMT+04:00) Moscow, St. Petersburg, Volgograd", "abCDE2345", "1111abCDE"},
                {"Denis", "Sozonov", "ab@cd.ru", "(GMT+04:00) Moscow, St. Petersburg, Volgograd", "abCDE2345", "1"},
        };
    }

    @Test(testName = "Проверка вывода ошибки не совпадающих паролей при регистрации", dataProvider = "DifferentPasswordsRegisterData")
    public void differentPasswordRegisterTest(String firstName, String lastName, String email, String timeZone,
                                              String password, String secondPassword) {
        RegisterPage.openRegisterPage();
        RegisterPage.fillRegisterForm(firstName, lastName, email, timeZone, password, secondPassword);
        RegisterPage.clickOnCreateNewAccountButton();
        assertEquals(RegisterPage.getPasswordErrorMessage(), "Error: The passwords you entered did not match.");
    }
}
