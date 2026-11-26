package com.framework.tests;

import com.framework.api.AccountApiClient;
import com.framework.api.ApiResult;
import com.framework.model.TestUser;
import com.framework.utils.TestDataFactory;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

@Epic("E-commerce")
@Feature("Account API")
public class AccountApiTest {

    private final AccountApiClient accountApi = new AccountApiClient();

    @Test(groups = {"smoke", "api"}, description = "Account lifecycle: create, verify login, delete, verify gone")
    public void accountLifecycle() {
        TestUser user = TestDataFactory.newUser();

        assertEquals(accountApi.createAccount(user).responseCode(), 201);
        assertEquals(accountApi.verifyLogin(user.email(), user.password()).responseCode(), 200);

        ApiResult deleted = accountApi.deleteAccount(user);
        assertEquals(deleted.responseCode(), 200);
        assertEquals(deleted.message(), "Account deleted!");

        assertEquals(accountApi.verifyLogin(user.email(), user.password()).responseCode(), 404);
    }

    @Test(groups = {"regression", "api"}, description = "Duplicate email is rejected")
    public void duplicateEmailIsRejected() {
        TestUser user = TestDataFactory.newUser();
        accountApi.createAccount(user);
        try {
            ApiResult duplicate = accountApi.createAccount(user);
            assertEquals(duplicate.responseCode(), 400);
            assertEquals(duplicate.message(), "Email already exists!");
        } finally {
            accountApi.deleteAccount(user);
        }
    }
}
