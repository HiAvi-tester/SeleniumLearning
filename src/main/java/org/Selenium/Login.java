package org.Selenium;



import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class Login {
    public static void main(String[] args) throws InterruptedException, IOException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.youtube.com");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        //Launch the URL and maximize
        driver.get("https://www.youtube.com");

        // Wait 5 seconds for site to get loaded
        Thread.sleep(5000);

        // Find the element where Search Word is to be entered, 
        // populate the search bar
        driver.findElement(By.xpath("//input[@name='search_query']")).clear();
        CharSequence searchWord = "movies";
        driver.findElement(By.xpath("//input[@name='search_query']")).sendKeys(searchWord);
        Thread.sleep(3000);
        driver.findElement(By.xpath("(//button[@aria-label='Search'])[1]")).click();

        // Wait 5 seconds
        Thread.sleep(5000);

        //Print the first element card (title, price, latest delivery time, etc)
        String videoOne = driver.findElement(By.xpath("(//a[@id='video-title'])[1]")).getText();
        String videoTwo = driver.findElement(By.xpath("(//a[@id='video-title'])[2]")).getText();
        String videoThree = driver.findElement(By.xpath("(//a[@id='video-title'])[3]")).getText();
        String videoFour = driver.findElement(By.xpath("(//a[@id='video-title'])[4]")).getText();

        System.out.println("-------------------------------------------------\n\n");
        System.out.println("The first 4 " + searchWord + " videos available are: \n 1. " + videoOne + "\n 2. " + videoTwo + "\n 3. " + videoThree+ "\n 4. " + videoFour);
        System.out.println("-------------------------------------------------\n\n");

        // Wait 5 seconds
        Thread.sleep(5000);

        //Close and quit the driver
        driver.close();
        driver.quit();
    }
}