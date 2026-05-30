package com.enterprise.automation.integrations;

public interface NotificationPublisher {
    void publish(String channel, String subject, String message);
}
