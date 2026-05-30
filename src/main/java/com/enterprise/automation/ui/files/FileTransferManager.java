package com.enterprise.automation.ui.files;

import com.microsoft.playwright.Download;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.nio.file.Path;

public class FileTransferManager {
    public void upload(Locator input, Path file) {
        input.setInputFiles(file);
    }

    public Download download(Page page, Runnable action) {
        return page.waitForDownload(action);
    }
}
