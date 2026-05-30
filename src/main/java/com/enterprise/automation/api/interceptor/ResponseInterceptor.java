package com.enterprise.automation.api.interceptor;

import com.enterprise.automation.api.ApiResponseWrapper;

@FunctionalInterface
public interface ResponseInterceptor {
    ApiResponseWrapper intercept(ApiResponseWrapper response);
}
