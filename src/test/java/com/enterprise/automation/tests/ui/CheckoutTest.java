package com.enterprise.automation.tests.ui;

import com.enterprise.automation.annotations.Regression;
import com.enterprise.automation.annotations.UiTest;
import com.enterprise.automation.tests.BaseTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@UiTest
class CheckoutTest extends BaseTest {
    @Test
    @Regression
    @Disabled("Sample application placeholder; enable after configuring environment.yaml")
    void userCanCheckout() {
        page.navigate(config.getEnvironment().getBaseUrl() + "/cart");
        page.getByTestId("checkout").click();
        page.getByTestId("place-order").click();
        page.getByText("Order confirmed").waitFor();
    }
}
