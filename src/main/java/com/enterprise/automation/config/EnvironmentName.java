package com.enterprise.automation.config;

public enum EnvironmentName {
    DEV, QA, SIT, UAT, PERF, STAGE, PROD;

    public static EnvironmentName from(String value) {
        if (value == null || value.isBlank()) {
            return QA;
        }
        return EnvironmentName.valueOf(value.trim().toUpperCase());
    }
}
