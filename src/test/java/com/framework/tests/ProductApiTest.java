package com.framework.tests;

import com.framework.api.ApiResult;
import com.framework.api.ProductApiClient;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

@Epic("E-commerce")
@Feature("Product API")
public class ProductApiTest {

    private final ProductApiClient productApi = new ProductApiClient();

    @Test(groups = {"smoke", "api"}, description = "GET /productsList returns products with the expected fields")
    public void productsListReturnsWellFormedProducts() {
        ApiResult result = productApi.getAllProducts();

        assertEquals(result.httpStatus(), 200);
        assertEquals(result.responseCode(), 200);
        List<Map<String, Object>> products = result.json().getList("products");
        assertFalse(products.isEmpty(), "Product list is empty");
        for (Map<String, Object> p : products) {
            assertNotNull(p.get("id"), "Missing id: " + p);
            assertNotNull(p.get("name"), "Missing name: " + p);
            assertTrue(String.valueOf(p.get("price")).startsWith("Rs."), "Unexpected price format: " + p);
        }
    }

    @Test(groups = {"regression", "api"}, description = "POST /productsList is rejected with 405")
    public void postToProductsListIsNotAllowed() {
        ApiResult result = productApi.postToProductsList();

        assertEquals(result.responseCode(), 405);
        assertEquals(result.message(), "This request method is not supported.");
    }

    @Test(groups = {"regression", "api"}, description = "Search returns only matching products")
    public void searchReturnsMatchingProducts() {
        ApiResult result = productApi.searchProduct("jean");

        assertEquals(result.responseCode(), 200);
        List<String> names = result.json().getList("products.name");
        assertFalse(names.isEmpty(), "No products found for 'jean'");
        names.forEach(n -> assertTrue(n.toLowerCase().contains("jean"), "Unrelated product: " + n));
    }

    @Test(groups = {"regression", "api"}, description = "Search without the required parameter returns 400")
    public void searchWithoutParameterIsBadRequest() {
        ApiResult result = productApi.searchProductWithoutParam();

        assertEquals(result.responseCode(), 400);
        assertTrue(result.message().contains("search_product parameter is missing"), result.message());
    }

    @Test(groups = {"regression", "api"}, description = "GET /brandsList returns brands")
    public void brandsListIsNotEmpty() {
        ApiResult result = productApi.getAllBrands();

        assertEquals(result.responseCode(), 200);
        assertFalse(result.json().getList("brands").isEmpty());
    }
}
