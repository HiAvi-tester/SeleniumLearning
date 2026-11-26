package com.framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/** Site header shared by all pages: login state and logout. */
public class HeaderComponent extends BasePage {

    private static final By LOGGED_IN_AS = By.xpath("//a[contains(normalize-space(.), 'Logged in as')]");
    private static final By LOGOUT = By.cssSelector("a[href='/logout']");

    public HeaderComponent(WebDriver driver) {
        super(driver);
    }

    /** Returns the user name shown after "Logged in as". */
    public String loggedInUserName() {
        return text(LOGGED_IN_AS).replace("Logged in as", "").trim();
    }

    public LoginPage logout() {
        click(LOGOUT);
        return new LoginPage(driver);
    }
}
