package com.enterprise.automation.ui.browser;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ShadowDomSupport {
    public Locator pierce(Page page, String selector) {
        return page.locator(selector);
    }
}
