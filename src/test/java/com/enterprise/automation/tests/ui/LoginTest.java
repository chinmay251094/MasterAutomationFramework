package com.enterprise.automation.tests.ui;

import com.enterprise.automation.annotations.Smoke;
import com.enterprise.automation.annotations.UiTest;
import com.enterprise.automation.tests.BaseTest;
import com.enterprise.automation.tests.pages.LoginPage;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@UiTest
class LoginTest extends BaseTest {
    @Test
    @Smoke
    @Disabled("Sample application placeholder; enable after configuring environment.yaml")
    void userCanLogin() {
        LoginPage loginPage = new LoginPage(page);
        loginPage.open(config.getEnvironment().getBaseUrl());
        loginPage.login("standard_user", "secret");
    }
}
