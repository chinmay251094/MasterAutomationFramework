package com.enterprise.automation.api;

import com.microsoft.playwright.APIResponse;

import java.util.Map;

public class ApiResponseWrapper {
    private final APIResponse response;

    public ApiResponseWrapper(APIResponse response) {
        this.response = response;
    }

    public int status() {
        return response.status();
    }

    public String text() {
        return response.text();
    }

    public byte[] body() {
        return response.body();
    }

    public Map<String, String> headers() {
        return response.headers();
    }

    public APIResponse raw() {
        return response;
    }
}
