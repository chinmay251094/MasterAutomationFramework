package com.enterprise.automation.ui.session;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Browser;

import java.nio.file.Path;

public class SessionManager {
    public void saveStorageState(BrowserContext context, Path path) {
        context.storageState(new BrowserContext.StorageStateOptions().setPath(path));
    }

    public Browser.NewContextOptions loadStorageState(Path path) {
        return new Browser.NewContextOptions().setStorageStatePath(path);
    }
}
