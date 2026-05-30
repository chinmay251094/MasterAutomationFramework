# Contributing

## Principles

- Keep the platform generic and application-neutral.
- Prefer configuration and extension points over core modifications.
- Use Java 21, Playwright Java, JUnit 5, Maven, Jackson, Lombok, Allure, Extent Reports, and Log4j2 only unless an architecture review approves a new dependency.
- Do not add Selenium, REST Assured, TestNG, or Cucumber.

## Branching

- `main`: releasable platform source.
- `develop`: integration branch.
- `feature/*`: feature work.
- `fix/*`: defect work.
- `release/*`: release preparation.

## Pull Request Checklist

- Tests or documented verification are included.
- No hardcoded secrets.
- Configuration remains environment-neutral.
- Public APIs are backward compatible or documented as breaking.
- `CONTEXT.md` is updated for major changes.
- Reports, artifacts, and logs do not leak credentials.
