package com.enterprise.automation.reporting;

import io.qameta.allure.Allure;

import java.io.ByteArrayInputStream;

public class AllureReporter {
    public void attachText(String name, String content) {
        Allure.addAttachment(name, "text/plain", content);
    }

    public void attachPng(String name, byte[] content) {
        Allure.addAttachment(name, "image/png", new ByteArrayInputStream(content), ".png");
    }
}
