package com.enterprise.automation.api;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.Playwright;

public class ApiFactory {
    public ApiClient create(Playwright playwright, String baseUrl) {
        APIRequest.NewContextOptions options = new APIRequest.NewContextOptions().setBaseURL(baseUrl);
        APIRequestContext context = playwright.request().newContext(options);
        return new ApiClient(context);
    }
}
