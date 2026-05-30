package com.enterprise.automation.api.validation;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SchemaValidator {
    private final ObjectMapper mapper = new ObjectMapper();

    public void assertValidJson(String json) {
        try {
            mapper.readTree(json);
        } catch (Exception e) {
            throw new AssertionError("Response is not valid JSON", e);
        }
    }
}
