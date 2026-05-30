package com.enterprise.automation.config;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PlatformConfig {
    String name;
    String version;
    String reportsDirectory;
    String artifactsDirectory;
    EnvironmentConfig environment;
    ExecutionConfig execution;
}
