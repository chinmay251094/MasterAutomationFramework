package com.enterprise.automation.ui;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public abstract class BasePage {
    protected final Page page;

    protected BasePage(Page page) {
        this.page = page;
    }

    protected Locator locator(LocatorStrategy strategy, String selector) {
        return strategy.resolve(page, selector);
    }

    public String title() {
        return page.title();
    }
}
