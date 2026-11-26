package com.framework.tests;

import com.framework.api.AccountApiClient;
import com.framework.api.ApiResult;
import com.framework.base.BaseUiTest;
import com.framework.model.TestUser;
import com.framework.pages.CartPage;
import com.framework.pages.HeaderComponent;
import com.framework.pages.LoginPage;
import com.framework.pages.ProductsPage;
import com.framework.utils.TestDataFactory;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * Hybrid pattern: the API creates a fresh user before each test and deletes it afterwards,
 * so the UI test only exercises what it is meant to verify. No shared accounts, no leftover data,
 * and each run is independent of the previous one.
 */
@Epic("E-commerce")
@Feature("Hybrid API + UI flows")
public class HybridCheckoutTest extends BaseUiTest {

    // ThreadLocal because parallel="methods" runs these tests on the same instance concurrently.
    private final ThreadLocal<TestUser> user = new ThreadLocal<>();
    private final AccountApiClient accountApi = new AccountApiClient();

    @BeforeMethod(alwaysRun = true) // runs after BaseUiTest.startBrowser (superclass config runs first)
    public void seedUserViaApi() {
        TestUser newUser = TestDataFactory.newUser();
        ApiResult result = accountApi.createAccount(newUser);
        assertEquals(result.responseCode(), 201, "User seeding failed: " + result.rawBody());
        user.set(newUser);
    }

    @AfterMethod(alwaysRun = true)
    public void cleanUpUserViaApi() {
        TestUser seeded = user.get();
        if (seeded != null) {
            accountApi.deleteAccount(seeded);
            user.remove();
        }
    }

    @Test(groups = {"smoke", "ui"}, description = "API-seeded user can log in through the UI")
    @Description("Creates a user via API, logs in via UI, verifies the header greets that user")
    public void seededUserCanLogIn() {
        TestUser u = user.get();

        HeaderComponent header = new LoginPage(driver()).open().loginAs(u.email(), u.password());

        assertEquals(header.loggedInUserName(), u.name());
    }

    @Test(groups = {"regression", "ui"}, description = "Logged-in user can search and add a product to the cart")
    public void loggedInUserCanSearchAndAddToCart() {
        TestUser u = user.get();
        String searchTerm = "Blue Top";
        new LoginPage(driver()).open().loginAs(u.email(), u.password());

        ProductsPage products = new ProductsPage(driver()).open().search(searchTerm);
        List<String> results = products.productNames();
        assertFalse(results.isEmpty(), "Search returned no products");
        assertTrue(results.stream().allMatch(n -> n.toLowerCase().contains("top")),
                "Unrelated products in results: " + results);

        products.addFirstResultToCart();
        List<String> cartItems = new CartPage(driver()).open().itemNames();

        assertEquals(cartItems, List.of(results.get(0)));
    }

    @Test(groups = {"regression", "ui"}, description = "User can log out and log back in")
    public void userCanLogOutAndLogBackIn() {
        TestUser u = user.get();
        LoginPage loginPage = new LoginPage(driver()).open()
                .loginAs(u.email(), u.password())
                .logout();

        HeaderComponent header = loginPage.loginAs(u.email(), u.password());

        assertEquals(header.loggedInUserName(), u.name());
    }
}
