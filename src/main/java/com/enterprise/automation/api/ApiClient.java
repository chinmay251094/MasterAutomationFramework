package com.enterprise.automation.api;

import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;

public class ApiClient {
    private final APIRequestContext requestContext;

    public ApiClient(APIRequestContext requestContext) {
        this.requestContext = requestContext;
    }

    public ApiResponseWrapper execute(ApiRequest request) {
        RequestOptions options = RequestOptions.create();
        request.getHeaders().forEach(options::setHeader);
        request.getQueryParams().forEach((key, value) -> options.setQueryParam(key, String.valueOf(value)));
        if (request.getBody() != null) {
            options.setData(request.getBody());
        }
        APIResponse response = switch (request.getMethod()) {
            case GET -> requestContext.get(request.getPath(), options);
            case POST -> requestContext.post(request.getPath(), options);
            case PUT -> requestContext.put(request.getPath(), options);
            case PATCH -> requestContext.patch(request.getPath(), options);
            case DELETE -> requestContext.delete(request.getPath(), options);
        };
        return new ApiResponseWrapper(response);
    }
}
