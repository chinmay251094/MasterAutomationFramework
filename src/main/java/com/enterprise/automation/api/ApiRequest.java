package com.enterprise.automation.api;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.Map;

@Value
@Builder
public class ApiRequest {
    HttpMethod method;
    String path;
    String body;
    @Singular Map<String, String> headers;
    @Singular Map<String, Object> queryParams;
}
