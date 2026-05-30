package com.enterprise.automation.contract;

import com.enterprise.automation.api.ApiRequest;
import com.enterprise.automation.api.ApiResponseWrapper;

public interface OpenApiContractAdapter {
    void validate(ApiRequest request, ApiResponseWrapper response);
}
