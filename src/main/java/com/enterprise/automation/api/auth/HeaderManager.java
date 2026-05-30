package com.enterprise.automation.api.auth;

import java.util.LinkedHashMap;
import java.util.Map;

public class HeaderManager {
    private final Map<String, String> headers = new LinkedHashMap<>();

    public HeaderManager bearer(String token) {
        headers.put("Authorization", "Bearer " + token);
        return this;
    }

    public HeaderManager basic(String encodedCredential) {
        headers.put("Authorization", "Basic " + encodedCredential);
        return this;
    }

    public HeaderManager apiKey(String headerName, String value) {
        headers.put(headerName, value);
        return this;
    }

    public Map<String, String> build() {
        return Map.copyOf(headers);
    }
}
