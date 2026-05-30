package com.enterprise.automation.tests;

import com.enterprise.automation.config.ConfigLoader;
import com.enterprise.automation.config.PlatformConfig;
import com.enterprise.automation.ui.BrowserFactory;
import com.enterprise.automation.ui.ContextFactory;
import com.enterprise.automation.ui.PageFactory;
import com.enterprise.automation.ui.PlaywrightManager;
import com.enterprise.automation.ui.artifacts.ArtifactManager;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;

import java.nio.file.Path;

public abstract class BaseTest {
    protected PlatformConfig config;
    protected PlaywrightManager playwrightManager;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;
    protected ArtifactManager artifacts;

    @BeforeEach
    void setUp() {
        config = ConfigLoader.get();
        Path artifactRoot = Path.of(config.getArtifactsDirectory());
        artifacts = new ArtifactManager(artifactRoot);
        playwrightManager = PlaywrightManager.create();
        browser = new BrowserFactory().create(playwrightManager.playwright(), config.getExecution());
        context = new ContextFactory().create(browser, config.getExecution(), artifactRoot);
        page = new PageFactory().create(context);
    }

    @AfterEach
    void tearDown(TestInfo testInfo) {
        if (page != null && config.getExecution().isScreenshotOnFailure()) {
            artifacts.screenshot(page, testInfo.getDisplayName().replaceAll("[^a-zA-Z0-9.-]", "_"));
        }
        if (context != null) {
            context.close();
        }
        if (browser != null) {
            browser.close();
        }
        if (playwrightManager != null) {
            playwrightManager.close();
        }
    }
}
