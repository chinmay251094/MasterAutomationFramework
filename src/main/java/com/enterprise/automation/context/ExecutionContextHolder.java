package com.enterprise.automation.context;

import com.enterprise.automation.config.ConfigLoader;

public final class ExecutionContextHolder {
    private static final ThreadLocal<ExecutionContext> CONTEXT =
            ThreadLocal.withInitial(() -> ExecutionContext.start(ConfigLoader.get()));

    private ExecutionContextHolder() {
    }

    public static ExecutionContext current() {
        return CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
    }
}
