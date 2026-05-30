package com.enterprise.automation.ui;

import com.enterprise.automation.config.ExecutionConfig;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;

import java.nio.file.Path;

public class ContextFactory {
    public BrowserContext create(Browser browser, ExecutionConfig config, Path artifactsDirectory) {
        Browser.NewContextOptions options = new Browser.NewContextOptions()
                .setViewportSize(config.getViewportWidth(), config.getViewportHeight())
                .setRecordVideoDir(artifactsDirectory.resolve("videos"));
        return browser.newContext(options);
    }
}
