package com.enterprise.automation.ui;

import com.enterprise.automation.config.BrowserName;
import com.enterprise.automation.config.ExecutionConfig;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;

public class BrowserFactory {
    public Browser create(Playwright playwright, ExecutionConfig config) {
        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions()
                .setHeadless(config.isHeadless())
                .setTimeout(config.getTimeoutMs());
        BrowserName browser = config.getBrowser();
        return switch (browser) {
            case FIREFOX -> playwright.firefox().launch(options);
            case WEBKIT -> playwright.webkit().launch(options);
            case CHROMIUM -> playwright.chromium().launch(options);
        };
    }
}
