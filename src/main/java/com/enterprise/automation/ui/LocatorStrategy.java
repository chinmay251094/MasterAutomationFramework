package com.enterprise.automation.ui;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public enum LocatorStrategy {
    CSS {
        @Override
        public Locator resolve(Page page, String selector) {
            return page.locator(selector);
        }
    },
    TEXT {
        @Override
        public Locator resolve(Page page, String selector) {
            return page.getByText(selector);
        }
    },
    TEST_ID {
        @Override
        public Locator resolve(Page page, String selector) {
            return page.getByTestId(selector);
        }
    };

    public abstract Locator resolve(Page page, String selector);
}
