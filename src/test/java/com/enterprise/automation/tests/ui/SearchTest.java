package com.enterprise.automation.tests.ui;

import com.enterprise.automation.annotations.Regression;
import com.enterprise.automation.annotations.UiTest;
import com.enterprise.automation.tests.BaseTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@UiTest
class SearchTest extends BaseTest {
    @Test
    @Regression
    @Disabled("Sample application placeholder; enable after configuring environment.yaml")
    void userCanSearch() {
        page.navigate(config.getEnvironment().getBaseUrl());
        page.getByTestId("search-input").fill("laptop");
        page.getByTestId("search-submit").click();
        page.getByTestId("search-results").waitFor();
    }
}
