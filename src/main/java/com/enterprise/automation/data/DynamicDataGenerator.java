package com.enterprise.automation.data;

import java.time.Instant;
import java.util.UUID;

public class DynamicDataGenerator {
    public String uniqueEmail(String prefix, String domain) {
        return prefix + "+" + Instant.now().toEpochMilli() + "@" + domain;
    }

    public String uniqueId(String prefix) {
        return prefix + "-" + UUID.randomUUID();
    }
}
