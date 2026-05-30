package com.enterprise.automation.config;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ExecutionConfig {
    BrowserName browser;
    boolean headless;
    int timeoutMs;
    int retries;
    int viewportWidth;
    int viewportHeight;
    boolean screenshotOnFailure;
    String videoMode;
    String traceMode;
    boolean harEnabled;
}
