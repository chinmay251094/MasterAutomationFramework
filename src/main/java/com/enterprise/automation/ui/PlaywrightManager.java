package com.enterprise.automation.ui;

import com.microsoft.playwright.Playwright;

public final class PlaywrightManager implements AutoCloseable {
    private final Playwright playwright;

    private PlaywrightManager() {
        this.playwright = Playwright.create();
    }

    public static PlaywrightManager create() {
        return new PlaywrightManager();
    }

    public Playwright playwright() {
        return playwright;
    }

    @Override
    public void close() {
        playwright.close();
    }
}
