package com.enterprise.automation.ui;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;

public class PageFactory {
    public Page create(BrowserContext context) {
        Page page = context.newPage();
        page.setDefaultTimeout(30000);
        return page;
    }
}
