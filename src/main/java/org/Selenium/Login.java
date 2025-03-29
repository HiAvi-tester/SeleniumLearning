package org.Selenium;



import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;


public class Login {
    public static void main(String[] args) throws InterruptedException, IOException {
        WebDriver driver=new ChromeDriver();
        driver.get("https://www.google.com");
        String title=driver.getTitle();
        System.out.println(title);

    }
}

