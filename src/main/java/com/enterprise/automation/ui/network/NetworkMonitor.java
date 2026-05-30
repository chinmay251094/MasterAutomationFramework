package com.enterprise.automation.ui.network;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Request;
import com.microsoft.playwright.Response;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class NetworkMonitor {
    private final List<String> requests = new CopyOnWriteArrayList<>();
    private final List<String> responses = new CopyOnWriteArrayList<>();

    public void attach(Page page) {
        page.onRequest(this::recordRequest);
        page.onResponse(this::recordResponse);
    }

    public List<String> requests() {
        return List.copyOf(requests);
    }

    public List<String> responses() {
        return List.copyOf(responses);
    }

    private void recordRequest(Request request) {
        requests.add(request.method() + " " + request.url());
    }

    private void recordResponse(Response response) {
        responses.add(response.status() + " " + response.url());
    }
}
