package com.framework.tests;

import com.framework.base.BaseUiTest;
import com.framework.pages.LoginPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

@Epic("E-commerce")
@Feature("Login validation")
public class LoginNegativeTest extends BaseUiTest {

    @DataProvider(name = "invalidCredentials", parallel = true)
    public Object[][] invalidCredentials() {
        return new Object[][]{
                {"unknown.user.qa@example.com", "AnyPass@123", "unregistered email"},
                {"qa.auto.nobody@example.com", "wrong", "wrong password"},
        };
    }

    @Test(dataProvider = "invalidCredentials", groups = {"regression", "ui"},
            description = "Invalid credentials show an error and do not log in")
    public void invalidCredentialsAreRejected(String email, String password, String scenario) {
        LoginPage loginPage = new LoginPage(driver()).open().loginExpectingError(email, password);

        assertEquals(loginPage.errorMessage(), "Your email or password is incorrect!", scenario);
    }
}
