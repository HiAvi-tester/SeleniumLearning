package com.framework.api;

import io.qameta.allure.Step;

public class ProductApiClient extends BaseApiClient {

    @Step("API: GET products list")
    public ApiResult getAllProducts() {
        return ApiResult.from(request().get("/productsList"));
    }

    @Step("API: POST products list (unsupported method)")
    public ApiResult postToProductsList() {
        return ApiResult.from(request().post("/productsList"));
    }

    @Step("API: search products for '{term}'")
    public ApiResult searchProduct(String term) {
        return ApiResult.from(request().formParam("search_product", term).post("/searchProduct"));
    }

    @Step("API: search products without parameter")
    public ApiResult searchProductWithoutParam() {
        return ApiResult.from(request().post("/searchProduct"));
    }

    @Step("API: GET brands list")
    public ApiResult getAllBrands() {
        return ApiResult.from(request().get("/brandsList"));
    }
}
