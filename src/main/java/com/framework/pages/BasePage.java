package com.framework.pages;

import com.framework.config.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/** Explicit-wait helpers shared by every page. No Thread.sleep anywhere in the framework. */
public abstract class BasePage {

    private static final By CONSENT_BUTTON = By.cssSelector("button.fc-cta-consent");

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getInt("explicit.wait.seconds")));
    }

    /** Navigates by URL rather than nav-link clicks, which avoids the site's interstitial ads. */
    protected void open(String path) {
        driver.get(ConfigReader.baseUrl() + path);
        dismissConsentIfShown();
    }

    protected WebElement visible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected List<WebElement> allVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    protected void type(By locator, String text) {
        WebElement element = visible(locator);
        element.clear();
        element.sendKeys(text);
    }

    /** Clicks normally; falls back to a JS click if an overlay (ad, sticky banner) intercepts it. */
    protected void click(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        try {
            element.click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }

    protected String text(By locator) {
        return visible(locator).getText().trim();
    }

    /** Region-specific cookie dialog; absent on most CI runners, so it is a no-op there. */
    private void dismissConsentIfShown() {
        List<WebElement> buttons = driver.findElements(CONSENT_BUTTON);
        if (!buttons.isEmpty() && buttons.get(0).isDisplayed()) {
            buttons.get(0).click();
        }
    }
}
