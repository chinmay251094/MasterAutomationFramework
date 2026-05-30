package com.enterprise.automation.observability;

import java.util.Map;

public interface TelemetryPublisher {
    void event(String name, Map<String, String> attributes);
}
