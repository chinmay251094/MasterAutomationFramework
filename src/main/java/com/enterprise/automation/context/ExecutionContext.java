package com.enterprise.automation.context;

import com.enterprise.automation.config.PlatformConfig;
import lombok.Builder;
import lombok.Value;

import java.time.Instant;
import java.util.UUID;

@Value
@Builder
public class ExecutionContext {
    String executionId;
    String correlationId;
    Instant startedAt;
    PlatformConfig config;

    public static ExecutionContext start(PlatformConfig config) {
        String id = UUID.randomUUID().toString();
        return ExecutionContext.builder()
                .executionId(id)
                .correlationId(id)
                .startedAt(Instant.now())
                .config(config)
                .build();
    }
}
