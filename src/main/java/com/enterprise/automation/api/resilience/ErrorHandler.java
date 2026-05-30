package com.enterprise.automation.api.resilience;

import com.enterprise.automation.api.ApiResponseWrapper;

public class ErrorHandler {
    public void throwOnServerError(ApiResponseWrapper response) {
        if (response.status() >= 500) {
            throw new IllegalStateException("Server error " + response.status() + ": " + response.text());
        }
    }
}
