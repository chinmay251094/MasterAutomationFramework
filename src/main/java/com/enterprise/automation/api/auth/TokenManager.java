package com.enterprise.automation.api.auth;

import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class TokenManager {
    private final AtomicReference<Token> token = new AtomicReference<>();

    public Optional<String> currentToken() {
        Token current = token.get();
        if (current == null || current.expiresAt().isBefore(Instant.now())) {
            return Optional.empty();
        }
        return Optional.of(current.value());
    }

    public void store(String value, Instant expiresAt) {
        token.set(new Token(value, expiresAt));
    }

    private record Token(String value, Instant expiresAt) {
    }
}
