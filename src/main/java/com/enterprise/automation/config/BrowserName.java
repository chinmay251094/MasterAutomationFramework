package com.enterprise.automation.config;

public enum BrowserName {
    CHROMIUM, FIREFOX, WEBKIT;

    public static BrowserName from(String value) {
        if (value == null || value.isBlank()) {
            return CHROMIUM;
        }
        return BrowserName.valueOf(value.trim().toUpperCase());
    }
}
