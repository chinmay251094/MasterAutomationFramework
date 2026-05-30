package com.enterprise.automation.api.interceptor;

import com.enterprise.automation.api.ApiRequest;

@FunctionalInterface
public interface RequestInterceptor {
    ApiRequest intercept(ApiRequest request);
}
