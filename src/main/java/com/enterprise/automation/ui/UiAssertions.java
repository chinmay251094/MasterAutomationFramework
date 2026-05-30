package com.enterprise.automation.ui;

import com.microsoft.playwright.Locator;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public final class UiAssertions {
    private UiAssertions() {
    }

    public static void visible(Locator locator) {
        assertThat(locator).isVisible();
    }

    public static void containsText(Locator locator, String text) {
        assertThat(locator).containsText(text);
    }
}
