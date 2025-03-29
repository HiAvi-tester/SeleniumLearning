package org.Selenium;



import org.openqa.selenium.WebDriver;

import org.openqa.selenium.firefox.FirefoxDriver;


public class Demo {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver=new FirefoxDriver();
        driver.get("https://www.google.com");
        String title=driver.getTitle();
        System.out.println(title);

        driver.manage().window().maximize();
        Thread.sleep(1000);
        driver.close();
System.out.println(driver.getTitle());

    }
}