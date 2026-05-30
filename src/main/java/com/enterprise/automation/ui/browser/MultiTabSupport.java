package com.enterprise.automation.ui.browser;

import com.microsoft.playwright.Page;

public class MultiTabSupport {
    public Page waitForPopup(Page page, Runnable action) {
        return page.waitForPopup(action);
    }
}
