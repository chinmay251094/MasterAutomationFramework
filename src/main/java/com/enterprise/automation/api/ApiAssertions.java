package com.enterprise.automation.api;

import org.junit.jupiter.api.Assertions;

public final class ApiAssertions {
    private ApiAssertions() {
    }

    public static void status(ApiResponseWrapper response, int expected) {
        Assertions.assertEquals(expected, response.status(), "Unexpected API response status");
    }

    public static void bodyContains(ApiResponseWrapper response, String expectedText) {
        Assertions.assertTrue(response.text().contains(expectedText),
                "Expected response body to contain: " + expectedText);
    }
}
