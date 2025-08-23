package org.Selenium;



import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;


public class Demo {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver=new ChromeDriver();
       // Actions actions = new Actions(driver);
        driver.get("https://www.google.com");
        String title=driver.getTitle();
        System.out.println(title);
        driver.manage().window().maximize();
        Thread.sleep(1000);
        driver.close();
System.out.println(driver.getTitle());

    }
}