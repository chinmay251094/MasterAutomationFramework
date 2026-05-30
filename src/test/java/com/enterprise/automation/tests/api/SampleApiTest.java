package com.enterprise.automation.tests.api;

import com.enterprise.automation.annotations.ApiTest;
import com.enterprise.automation.api.ApiAssertions;
import com.enterprise.automation.api.ApiClient;
import com.enterprise.automation.api.ApiFactory;
import com.enterprise.automation.api.ApiRequestBuilder;
import com.enterprise.automation.api.HttpMethod;
import com.enterprise.automation.api.validation.ContractValidator;
import com.enterprise.automation.api.validation.SchemaValidator;
import com.enterprise.automation.config.ConfigLoader;
import com.enterprise.automation.ui.PlaywrightManager;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Set;

@ApiTest
@Disabled("Sample API placeholders; enable after configuring environment.yaml")
class SampleApiTest {
    @Test
    void supportsCrudAuthenticationSchemaAndContractChecks() {
        try (PlaywrightManager manager = PlaywrightManager.create()) {
            ApiClient client = new ApiFactory().create(manager.playwright(), ConfigLoader.get().getEnvironment().getApiBaseUrl());

            var get = client.execute(ApiRequestBuilder.request(HttpMethod.GET, "/products").bearer("token").build());
            ApiAssertions.status(get, 200);
            new SchemaValidator().assertValidJson(get.text());
            new ContractValidator().assertAllowedStatus(get, Set.of(200));

            client.execute(ApiRequestBuilder.request(HttpMethod.POST, "/products").json("{\"name\":\"book\"}").build());
            client.execute(ApiRequestBuilder.request(HttpMethod.PUT, "/products/1").json("{\"name\":\"notebook\"}").build());
            client.execute(ApiRequestBuilder.request(HttpMethod.PATCH, "/products/1").json("{\"price\":10}").build());
            client.execute(ApiRequestBuilder.request(HttpMethod.DELETE, "/products/1").build());
        }
    }
}
