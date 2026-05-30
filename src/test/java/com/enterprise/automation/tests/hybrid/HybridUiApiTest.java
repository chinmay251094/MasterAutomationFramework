package com.enterprise.automation.tests.hybrid;

import com.enterprise.automation.annotations.ApiTest;
import com.enterprise.automation.annotations.UiTest;
import com.enterprise.automation.api.ApiClient;
import com.enterprise.automation.api.ApiFactory;
import com.enterprise.automation.api.ApiRequestBuilder;
import com.enterprise.automation.api.HttpMethod;
import com.enterprise.automation.tests.BaseTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@UiTest
@ApiTest
class HybridUiApiTest extends BaseTest {
    @Test
    @Disabled("Hybrid sample placeholder; enable after configuring environment.yaml")
    void apiSetupUiExecutionApiCleanup() {
        ApiClient api = new ApiFactory().create(playwrightManager.playwright(), config.getEnvironment().getApiBaseUrl());
        api.execute(ApiRequestBuilder.request(HttpMethod.POST, "/test-data/cart").json("{\"sku\":\"SKU-1\"}").build());

        page.navigate(config.getEnvironment().getBaseUrl() + "/cart");
        page.getByText("SKU-1").waitFor();

        api.execute(ApiRequestBuilder.request(HttpMethod.DELETE, "/test-data/cart/SKU-1").build());
    }
}
