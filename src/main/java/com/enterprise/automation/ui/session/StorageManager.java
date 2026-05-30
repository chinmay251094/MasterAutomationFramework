package com.enterprise.automation.ui.session;

import com.microsoft.playwright.Page;

public class StorageManager {
    public String localStorageValue(Page page, String key) {
        return (String) page.evaluate("key => window.localStorage.getItem(key)", key);
    }

    public void setLocalStorageValue(Page page, String key, String value) {
        page.evaluate("entry => window.localStorage.setItem(entry.key, entry.value)",
                new StorageEntry(key, value));
    }

    public void clearLocalStorage(Page page) {
        page.evaluate("() => window.localStorage.clear()");
    }

    private record StorageEntry(String key, String value) {
    }
}
