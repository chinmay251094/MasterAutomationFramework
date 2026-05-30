package com.enterprise.automation.tests.pages;

import com.enterprise.automation.ui.BasePage;
import com.enterprise.automation.ui.LocatorStrategy;
import com.microsoft.playwright.Page;

public class LoginPage extends BasePage {
    public LoginPage(Page page) {
        super(page);
    }

    public void open(String baseUrl) {
        page.navigate(baseUrl + "/login");
    }

    public void login(String username, String password) {
        locator(LocatorStrategy.CSS, "[name='username']").fill(username);
        locator(LocatorStrategy.CSS, "[name='password']").fill(password);
        locator(LocatorStrategy.CSS, "button[type='submit']").click();
    }
}
