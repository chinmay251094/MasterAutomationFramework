package com.enterprise.automation.ui.session;

import com.microsoft.playwright.BrowserContext;

import java.nio.file.Path;

public class AuthenticationManager {
    private final SessionManager sessions = new SessionManager();

    public void persistAuthenticatedSession(BrowserContext context, Path path) {
        sessions.saveStorageState(context, path);
    }
}
