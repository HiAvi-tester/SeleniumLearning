package com.framework.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class ProductsPage extends BasePage {

    private static final By SEARCH_INPUT = By.id("search_product");
    private static final By SEARCH_BUTTON = By.id("submit_search");
    private static final By SECTION_TITLE = By.cssSelector(".features_items h2.title");
    private static final By PRODUCT_NAMES = By.cssSelector(".features_items .productinfo p");
    private static final By FIRST_ADD_TO_CART = By.cssSelector(".features_items .productinfo a.add-to-cart");
    private static final By CART_MODAL = By.id("cartModal");
    private static final By CONTINUE_SHOPPING = By.cssSelector("#cartModal button.close-modal");

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    @Step("Open products page")
    public ProductsPage open() {
        open("/products");
        return this;
    }

    @Step("Search for '{term}'")
    public ProductsPage search(String term) {
        type(SEARCH_INPUT, term);
        click(SEARCH_BUTTON);
        wait.until(ExpectedConditions.textMatches(SECTION_TITLE, java.util.regex.Pattern.compile("(?i)searched products")));
        return this;
    }

    public List<String> productNames() {
        return allVisible(PRODUCT_NAMES).stream().map(WebElement::getText).map(String::trim).toList();
    }

    @Step("Add first search result to cart")
    public ProductsPage addFirstResultToCart() {
        click(FIRST_ADD_TO_CART);
        visible(CART_MODAL);
        click(CONTINUE_SHOPPING);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(CART_MODAL));
        return this;
    }
}
