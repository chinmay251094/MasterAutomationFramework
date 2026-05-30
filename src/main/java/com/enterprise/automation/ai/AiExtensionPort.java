package com.enterprise.automation.ai;

public interface AiExtensionPort {
    String analyzeFailure(String testName, String failureMessage, String artifactDirectory);
}
