package com.enterprise.automation.core.retry;

import java.time.Duration;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class RetryPolicy {
    private final int attempts;
    private final Duration delay;

    public RetryPolicy(int attempts, Duration delay) {
        this.attempts = Math.max(1, attempts);
        this.delay = delay;
    }

    public <T> T execute(Supplier<T> action, Predicate<Throwable> retryWhen) {
        Throwable last = null;
        for (int current = 1; current <= attempts; current++) {
            try {
                return action.get();
            } catch (Throwable error) {
                last = error;
                if (current == attempts || !retryWhen.test(error)) {
                    break;
                }
                sleep();
            }
        }
        throw new IllegalStateException("Action failed after " + attempts + " attempts", last);
    }

    private void sleep() {
        try {
            Thread.sleep(delay.toMillis());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Retry interrupted", e);
        }
    }
}
