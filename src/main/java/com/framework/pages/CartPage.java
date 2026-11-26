package com.framework.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CartPage extends BasePage {

    private static final By CART_ITEM_NAMES = By.cssSelector("#cart_info_table td.cart_description h4 a");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    @Step("Open cart")
    public CartPage open() {
        open("/view_cart");
        return this;
    }

    public List<String> itemNames() {
        return allVisible(CART_ITEM_NAMES).stream().map(WebElement::getText).map(String::trim).toList();
    }
}
