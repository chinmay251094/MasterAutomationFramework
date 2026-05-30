package com.enterprise.automation.api.auth;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

public class ApiAuthenticationManager {
    public Map<String, String> bearer(String token) {
        return new HeaderManager().bearer(token).build();
    }

    public Map<String, String> basic(String username, String password) {
        String encoded = Base64.getEncoder()
                .encodeToString((username + ":" + password).getBytes(StandardCharsets.UTF_8));
        return new HeaderManager().basic(encoded).build();
    }

    public Map<String, String> apiKey(String headerName, String value) {
        return new HeaderManager().apiKey(headerName, value).build();
    }
}
