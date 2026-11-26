package com.framework.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private static final By EMAIL = By.cssSelector("input[data-qa='login-email']");
    private static final By PASSWORD = By.cssSelector("input[data-qa='login-password']");
    private static final By LOGIN_BUTTON = By.cssSelector("button[data-qa='login-button']");
    private static final By LOGIN_ERROR = By.cssSelector("form[action='/login'] p");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("Open login page")
    public LoginPage open() {
        open("/login");
        return this;
    }

    @Step("Log in as {email}")
    public HeaderComponent loginAs(String email, String password) {
        submit(email, password);
        return new HeaderComponent(driver);
    }

    @Step("Submit login form expecting failure")
    public LoginPage loginExpectingError(String email, String password) {
        submit(email, password);
        return this;
    }

    public String errorMessage() {
        return text(LOGIN_ERROR);
    }

    private void submit(String email, String password) {
        type(EMAIL, email);
        type(PASSWORD, password);
        click(LOGIN_BUTTON);
    }
}
