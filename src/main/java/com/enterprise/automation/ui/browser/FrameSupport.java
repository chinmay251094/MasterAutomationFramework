package com.enterprise.automation.ui.browser;

import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Page;

public class FrameSupport {
    public FrameLocator frame(Page page, String selector) {
        return page.frameLocator(selector);
    }
}
