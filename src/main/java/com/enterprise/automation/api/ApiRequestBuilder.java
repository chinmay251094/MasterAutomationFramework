package com.enterprise.automation.api;

import java.util.Map;

public class ApiRequestBuilder {
    private final ApiRequest.ApiRequestBuilder delegate = ApiRequest.builder();

    public static ApiRequestBuilder request(HttpMethod method, String path) {
        return new ApiRequestBuilder().method(method).path(path);
    }

    public ApiRequestBuilder method(HttpMethod method) {
        delegate.method(method);
        return this;
    }

    public ApiRequestBuilder path(String path) {
        delegate.path(path);
        return this;
    }

    public ApiRequestBuilder bearer(String token) {
        delegate.header("Authorization", "Bearer " + token);
        return this;
    }

    public ApiRequestBuilder header(String key, String value) {
        delegate.header(key, value);
        return this;
    }

    public ApiRequestBuilder query(String key, Object value) {
        delegate.queryParam(key, value);
        return this;
    }

    public ApiRequestBuilder headers(Map<String, String> headers) {
        headers.forEach(delegate::header);
        return this;
    }

    public ApiRequestBuilder json(String json) {
        delegate.body(json);
        delegate.header("Content-Type", "application/json");
        return this;
    }

    public ApiRequest build() {
        return delegate.build();
    }
}
