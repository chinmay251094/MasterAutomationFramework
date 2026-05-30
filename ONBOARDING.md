# Onboarding

## Quick Start

1. Install Java 21 and Maven.
2. Configure `src/main/resources/environment.yaml`.
3. Run `mvn test -Denv=qa -Dbrowser=chromium -Dheadless=true`.
4. Enable sample tests after selectors and API paths match your application.

For detailed sample execution steps, read `docs/HOW_TO_RUN_SAMPLE_TESTS.md`.

For a beginner-friendly guide to adding new UI, API, and hybrid tests, read `docs/NEW_TEST_AUTHOR_GUIDE.md`.

For guidance on configuring UI and API URLs in each team's isolated framework copy, read `docs/MULTI_APPLICATION_URL_CONFIGURATION.md`.

## Setting Up A Team Repository

1. Pull or copy this framework template into a new application repository.
2. Update `src/main/resources/environment.yaml` with only that application's URLs.
3. Add page objects and components for that application.
4. Add test data for that application.
5. Configure application credentials through CI secrets or environment variables.
6. Use platform annotations such as `@Smoke`, `@Regression`, `@UiTest`, and `@ApiTest`.
7. Keep unrelated applications out of this repository.

## Common Commands

```bash
mvn test -Denv=qa
mvn test -Denv=uat -Dbrowser=firefox
mvn test -Denv=stage -Dheadless=true -Dtags=smoke
mvn allure:report
mvn org.owasp:dependency-check-maven:check
```
