package com.enterprise.automation.samplecases.api;

import com.enterprise.automation.annotations.ApiTest;
import com.enterprise.automation.api.ApiAssertions;
import com.enterprise.automation.api.ApiClient;
import com.enterprise.automation.api.ApiFactory;
import com.enterprise.automation.api.ApiRequestBuilder;
import com.enterprise.automation.api.HttpMethod;
import com.enterprise.automation.api.validation.SchemaValidator;
import com.enterprise.automation.ui.PlaywrightManager;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ApiTest
@Tag("sample-reference")
class SimpleGroceryApiSampleCases {
    private static final String SAMPLE_API_BASE_URL =
            System.getProperty("sample.api.baseUrl", "https://simple-grocery-store-api.click");
    private static final ObjectMapper JSON = new ObjectMapper();

    @Test
    void apiStatusIsUp() throws Exception {
        try (PlaywrightManager manager = PlaywrightManager.create()) {
            ApiClient client = new ApiFactory().create(manager.playwright(), SAMPLE_API_BASE_URL);

            var response = client.execute(ApiRequestBuilder.request(HttpMethod.GET, "/status").build());

            ApiAssertions.status(response, 200);
            new SchemaValidator().assertValidJson(response.text());
            assertTrue(JSON.readTree(response.text()).path("status").asText().equalsIgnoreCase("UP"),
                    "API status should be UP");
        }
    }

    @Test
    void userCanCreateCartAndAddAvailableProduct() throws Exception {
        try (PlaywrightManager manager = PlaywrightManager.create()) {
            ApiClient client = new ApiFactory().create(manager.playwright(), SAMPLE_API_BASE_URL);

            var productsResponse = client.execute(ApiRequestBuilder.request(HttpMethod.GET, "/products")
                    .query("available", true)
                    .query("results", 1)
                    .build());
            ApiAssertions.status(productsResponse, 200);

            JsonNode products = JSON.readTree(productsResponse.text());
            assertTrue(products.isArray(), "Products response should be an array");
            assertFalse(products.isEmpty(), "At least one available product should be returned");
            int productId = products.get(0).path("id").asInt();

            var cartResponse = client.execute(ApiRequestBuilder.request(HttpMethod.POST, "/carts").build());
            ApiAssertions.status(cartResponse, 201);
            String cartId = JSON.readTree(cartResponse.text()).path("cartId").asText();
            assertFalse(cartId.isBlank(), "Cart id should be returned");

            var addItemResponse = client.execute(ApiRequestBuilder.request(HttpMethod.POST, "/carts/" + cartId + "/items")
                    .json("{\"productId\":" + productId + ",\"quantity\":1}")
                    .build());
            ApiAssertions.status(addItemResponse, 201);

            var itemsResponse = client.execute(ApiRequestBuilder.request(HttpMethod.GET, "/carts/" + cartId + "/items").build());
            ApiAssertions.status(itemsResponse, 200);
            assertTrue(itemsResponse.text().contains(String.valueOf(productId)),
                    "Cart items should contain the selected product id");
        }
    }
}
