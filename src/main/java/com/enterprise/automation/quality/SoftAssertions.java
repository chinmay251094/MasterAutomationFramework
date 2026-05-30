package com.enterprise.automation.quality;

import java.util.ArrayList;
import java.util.List;

public class SoftAssertions {
    private final List<AssertionError> errors = new ArrayList<>();

    public void assertTrue(boolean condition, String message) {
        if (!condition) {
            errors.add(new AssertionError(message));
        }
    }

    public void assertAll() {
        if (!errors.isEmpty()) {
            AssertionError aggregate = new AssertionError("Soft assertion failures: " + errors.size());
            errors.forEach(aggregate::addSuppressed);
            throw aggregate;
        }
    }
}
