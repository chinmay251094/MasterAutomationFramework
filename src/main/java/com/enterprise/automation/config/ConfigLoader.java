package com.enterprise.automation.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public final class ConfigLoader {
    private static final ObjectMapper YAML = new ObjectMapper(new YAMLFactory());
    private static volatile PlatformConfig cached;

    private ConfigLoader() {
    }

    public static PlatformConfig get() {
        if (cached == null) {
            synchronized (ConfigLoader.class) {
                if (cached == null) {
                    cached = load();
                }
            }
        }
        return cached;
    }

    private static PlatformConfig load() {
        JsonNode app = resource("application.yaml");
        JsonNode env = resource("environment.yaml");
        JsonNode execution = resource("execution.yaml");

        String selectedEnv = System.getProperty("env",
                app.at("/platform/defaultEnvironment").asText("qa"));
        EnvironmentName environmentName = EnvironmentName.from(selectedEnv);
        JsonNode envNode = env.at("/environments/" + selectedEnv.toLowerCase());

        ExecutionConfig executionConfig = ExecutionConfig.builder()
                .browser(BrowserName.from(System.getProperty("browser", execution.at("/execution/browser").asText("chromium"))))
                .headless(Boolean.parseBoolean(System.getProperty("headless", execution.at("/execution/headless").asText("true"))))
                .timeoutMs(Integer.parseInt(System.getProperty("timeoutMs", execution.at("/execution/timeoutMs").asText("30000"))))
                .retries(Integer.parseInt(System.getProperty("retries", execution.at("/execution/retries").asText("1"))))
                .viewportWidth(execution.at("/execution/viewport/width").asInt(1440))
                .viewportHeight(execution.at("/execution/viewport/height").asInt(900))
                .screenshotOnFailure(execution.at("/execution/artifacts/screenshotOnFailure").asBoolean(true))
                .videoMode(execution.at("/execution/artifacts/video").asText("retain-on-failure"))
                .traceMode(execution.at("/execution/artifacts/trace").asText("retain-on-failure"))
                .harEnabled(execution.at("/execution/artifacts/har").asBoolean(false))
                .build();

        return PlatformConfig.builder()
                .name(app.at("/platform/name").asText("automation-platform"))
                .version(app.at("/platform/version").asText("0.0.0"))
                .reportsDirectory(app.at("/platform/reportsDirectory").asText("target/reports"))
                .artifactsDirectory(app.at("/platform/artifactsDirectory").asText("target/artifacts"))
                .environment(EnvironmentConfig.builder()
                        .name(environmentName)
                        .baseUrl(envNode.at("/baseUrl").asText("https://example.com"))
                        .apiBaseUrl(envNode.at("/apiBaseUrl").asText("https://api.example.com"))
                        .build())
                .execution(executionConfig)
                .build();
    }

    private static JsonNode resource(String name) {
        try (InputStream stream = ConfigLoader.class.getClassLoader().getResourceAsStream(name)) {
            Objects.requireNonNull(stream, "Missing required config resource: " + name);
            return YAML.readTree(stream);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to load " + name, e);
        }
    }
}
