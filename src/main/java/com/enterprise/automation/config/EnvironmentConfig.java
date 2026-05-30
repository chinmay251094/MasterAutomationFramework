package com.enterprise.automation.config;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class EnvironmentConfig {
    EnvironmentName name;
    String baseUrl;
    String apiBaseUrl;
}
