package com.enterprise.automation.api.resilience;

import com.enterprise.automation.api.ApiResponseWrapper;

import java.time.Duration;

public class RateLimitHandler {
    public boolean isRateLimited(ApiResponseWrapper response) {
        return response.status() == 429;
    }

    public Duration retryAfter(ApiResponseWrapper response, Duration fallback) {
        String retryAfter = response.headers().get("retry-after");
        if (retryAfter == null || retryAfter.isBlank()) {
            return fallback;
        }
        try {
            return Duration.ofSeconds(Long.parseLong(retryAfter));
        } catch (NumberFormatException ignored) {
            return fallback;
        }
    }
}
