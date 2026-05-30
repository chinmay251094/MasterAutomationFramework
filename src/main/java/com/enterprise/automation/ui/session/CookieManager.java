package com.enterprise.automation.ui.session;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.options.Cookie;

import java.util.List;

public class CookieManager {
    public List<Cookie> cookies(BrowserContext context) {
        return context.cookies();
    }

    public void add(BrowserContext context, List<Cookie> cookies) {
        context.addCookies(cookies);
    }

    public void clear(BrowserContext context) {
        context.clearCookies();
    }
}
