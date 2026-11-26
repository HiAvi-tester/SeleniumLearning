package com.framework.api;

import com.framework.model.TestUser;
import io.qameta.allure.Step;

/** Creates, verifies and deletes user accounts through the API: fast test setup and teardown. */
public class AccountApiClient extends BaseApiClient {

    @Step("API: create account {user.email}")
    public ApiResult createAccount(TestUser user) {
        return ApiResult.from(request()
                .formParam("name", user.name())
                .formParam("email", user.email())
                .formParam("password", user.password())
                .formParam("title", "Mr")
                .formParam("birth_date", "15")
                .formParam("birth_month", "6")
                .formParam("birth_year", "1990")
                .formParam("firstname", "QA")
                .formParam("lastname", "Automation")
                .formParam("company", "Test Co")
                .formParam("address1", "Sector 62")
                .formParam("address2", "Block A")
                .formParam("country", "India")
                .formParam("zipcode", "201301")
                .formParam("state", "Uttar Pradesh")
                .formParam("city", "Noida")
                .formParam("mobile_number", "9999999999")
                .post("/createAccount"));
    }

    @Step("API: verify login {email}")
    public ApiResult verifyLogin(String email, String password) {
        return ApiResult.from(request()
                .formParam("email", email)
                .formParam("password", password)
                .post("/verifyLogin"));
    }

    @Step("API: delete account {user.email}")
    public ApiResult deleteAccount(TestUser user) {
        return ApiResult.from(request()
                .formParam("email", user.email())
                .formParam("password", user.password())
                .delete("/deleteAccount"));
    }
}
