package com.enterprise.automation.api.validation;

import com.enterprise.automation.api.ApiResponseWrapper;

import java.util.Set;

public class ContractValidator {
    public void assertAllowedStatus(ApiResponseWrapper response, Set<Integer> allowedStatuses) {
        if (!allowedStatuses.contains(response.status())) {
            throw new AssertionError("Status " + response.status() + " is not allowed by contract " + allowedStatuses);
        }
    }
}
