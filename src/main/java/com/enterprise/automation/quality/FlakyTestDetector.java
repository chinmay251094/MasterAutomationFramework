package com.enterprise.automation.quality;

public class FlakyTestDetector {
    public boolean isFlaky(int attempts, int failures, int passes) {
        return attempts > 1 && failures > 0 && passes > 0;
    }
}
