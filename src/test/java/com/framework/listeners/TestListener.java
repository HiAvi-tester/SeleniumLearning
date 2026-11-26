package com.framework.listeners;

import com.framework.driver.DriverFactory;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.ByteArrayInputStream;

/** Attaches a screenshot and the page URL to the Allure report whenever a UI test fails. */
public class TestListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        if (!DriverFactory.hasDriver()) {
            return; // API test: nothing to capture
        }
        try {
            byte[] png = ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment("Screenshot - " + result.getMethod().getMethodName(),
                    "image/png", new ByteArrayInputStream(png), ".png");
            Allure.addAttachment("Page URL", DriverFactory.getDriver().getCurrentUrl());
        } catch (RuntimeException e) {
            System.err.println("Could not capture failure evidence: " + e.getMessage());
        }
    }
}
