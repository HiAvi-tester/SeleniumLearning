package org.Selenium;

//import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Link {
    public static void main(String[] args) throws InterruptedException {
        ChromeOptions chromeOptions = new ChromeOptions();
        // WebDriverManager.chromedriver().setup();
       // WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver(chromeOptions);
        // Navigate to the demoqa website
      //  driver.get("https://www.google.com");
        driver.get("https://adactin.com/HotelApp/index.php");
        driver.manage().window().maximize();
        WebElement x = driver.findElement(By.id("username"));
        x.sendKeys("vengatram");
        WebElement x1 = driver.findElement(By.name("password"));
        x1.sendKeys("vengat@123445");
        WebElement x2 = driver.findElement(By.id("login"));
        x2.click();

    }
}
