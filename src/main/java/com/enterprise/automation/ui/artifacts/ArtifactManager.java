package com.enterprise.automation.ui.artifacts;

import com.microsoft.playwright.Page;

import java.nio.file.Files;
import java.nio.file.Path;

public class ArtifactManager {
    private final Path artifactRoot;

    public ArtifactManager(Path artifactRoot) {
        this.artifactRoot = artifactRoot;
    }

    public Path screenshot(Page page, String name) {
        try {
            Files.createDirectories(artifactRoot.resolve("screenshots"));
            Path target = artifactRoot.resolve("screenshots").resolve(name + ".png");
            page.screenshot(new Page.ScreenshotOptions().setPath(target).setFullPage(true));
            return target;
        } catch (Exception e) {
            throw new IllegalStateException("Unable to capture screenshot", e);
        }
    }
}
