package com.enterprise.automation.accessibility;

import com.microsoft.playwright.Page;

public interface AccessibilityAdapter {
    AccessibilityResult scan(Page page);

    record AccessibilityResult(int violations, String report) {
    }
}
