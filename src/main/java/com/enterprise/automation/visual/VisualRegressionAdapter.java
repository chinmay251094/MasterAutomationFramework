package com.enterprise.automation.visual;

import com.microsoft.playwright.Page;

import java.nio.file.Path;

public interface VisualRegressionAdapter {
    void compare(Page page, Path baseline, String checkpointName);
}
