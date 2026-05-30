package com.enterprise.automation.logging;

import com.enterprise.automation.context.ExecutionContextHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

public final class FrameworkLogger {
    private FrameworkLogger() {
    }

    public static Logger getLogger(Class<?> type) {
        var context = ExecutionContextHolder.current();
        ThreadContext.put("executionId", context.getExecutionId());
        ThreadContext.put("correlationId", context.getCorrelationId());
        ThreadContext.put("threadId", String.valueOf(Thread.currentThread().threadId()));
        return LogManager.getLogger(type);
    }
}
