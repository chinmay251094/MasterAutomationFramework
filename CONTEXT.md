# Project Context

## Vision

Build a Java 21 enterprise automation framework template using Playwright Java, JUnit 5, Maven, Jackson, Lombok, Allure, Extent Reports, Log4j2, Docker, GitHub Actions, Jenkins, SonarQube, and OWASP Dependency Check. The central template is pushed to Git, and each application team takes its own isolated copy. Each team updates URLs, tests, data, and CI/CD settings for one application only.

## Current Status

Phase 1 design documentation and a Phase 2 foundation have been scaffolded. The repository now contains Maven configuration, layered YAML configuration, core execution context, UI factories, API client primitives, reporting helpers, logging setup, sample tests, CI/CD assets, Docker assets, and governance docs. The documented operating model is one isolated framework copy per application team.

## Completed Modules

- Maven template artifact setup.
- Layered configuration files.
- Execution context and structured logging helper.
- Playwright manager, browser factory, context factory, page factory, base page, wait engine, locator strategy, UI assertions, screenshot artifact manager, session manager.
- Playwright API client, request builder, response wrapper, auth header helper, token manager, schema validator, contract validator.
- Test data factory for JSON/YAML/CSV and dynamic data generator.
- Allure and Extent report helpers.
- JUnit 5 tag annotations.
- Sample UI, API, and hybrid tests.
- Enterprise extension ports for network monitoring, cookies, storage, authentication/session sharing, iframe/shadow DOM/multi-tab support, file transfer, API interceptors/loggers/auth/resilience, soft assertions, flaky detection, visual, accessibility, contract, observability, AI, and notifications.
- GitHub Actions, Jenkinsfile, Dockerfile, Docker Compose, SonarQube properties, and dependency-check configuration through Maven.
- Sample execution runbook at `docs/HOW_TO_RUN_SAMPLE_TESTS.md`.
- Beginner test authoring guide at `docs/NEW_TEST_AUTHOR_GUIDE.md`.
- Application team UI/API URL configuration guide at `docs/MULTI_APPLICATION_URL_CONFIGURATION.md`.

## Pending Modules

- Full visual regression implementation behind `VisualRegressionAdapter`.
- Accessibility implementation for Axe/WCAG validation behind `AccessibilityAdapter`.
- OpenTelemetry exporter behind `TelemetryPublisher`.
- Database and Excel data loaders.
- Advanced flaky-test analytics.
- Slack, Teams, and email notification implementations.
- Maven multi-module split if organizational scale requires it.
- AI self-healing, AI failure analysis, AI root-cause analysis, and AI test generation adapters.

## Architecture Decisions

- The platform starts as a Maven-based template so each application team can pull an isolated copy.
- Playwright is the only UI and API execution engine.
- JUnit 5 provides tagging, lifecycle, and parallelization.
- Jackson handles JSON, YAML, and CSV.
- Source code remains environment-neutral; each team controls runtime behavior through its own YAML and JVM system properties.
- Sample tests are disabled until a real target application is configured.
- One repository should target one application; do not combine many application suites into one framework copy.

## Folder Structure

```text
automation-platform
|-- src/main/java/com/enterprise/automation
|   |-- annotations
|   |-- api
|   |-- config
|   |-- context
|   |-- core
|   |-- data
|   |-- logging
|   |-- reporting
|   `-- ui
|-- src/main/resources
|-- src/test/java/com/enterprise/automation/tests
|-- docs
|-- .github/workflows
|-- docker-compose.yml
|-- Dockerfile
|-- Jenkinsfile
|-- sonar-project.properties
|-- CONTRIBUTING.md
|-- RELEASE_PROCESS.md
|-- TROUBLESHOOTING.md
|-- ONBOARDING.md
`-- CONTEXT.md
```

## Implementation Roadmap

1. Stabilize compilation and dependency versions.
2. Add richer UI artifact retention for trace, video, HAR, network logs, downloads, and failures.
3. Expand API interceptors, request/response logging, OAuth2 flows, rate-limit handling, multipart, form data, XML, and error strategies.
4. Add reporting dashboard rollups and historical trend storage.
5. Harden CI/CD with notifications, publishing, and branch-specific quality gates.
6. Add production validation and rollback/canary validation examples.
7. Add extension adapters for visual, accessibility, contract, observability, and AI features.

## Decision Log

- 2026-05-30: Initialized platform as a Java 21 Maven framework template in an empty workspace.
- 2026-05-30: Used disabled sample tests to avoid false failures before application configuration exists.
- 2026-05-30: Chose extension points for Excel, database, Axe, OpenTelemetry, and AI features to respect the requested technology boundary.
- 2026-05-30: Added stable ports for named enterprise capabilities so later phases can implement them without breaking consumer tests.
- 2026-05-30: Added step-by-step sample test execution guide.
- 2026-05-30: Updated URL strategy to one isolated framework copy per application team, avoiding central multi-application clutter.
- 2026-05-30: Added simple step-by-step guide for new team members adding UI, API, and hybrid tests.

## Known Risks

- External dependencies require Maven network access on first build.
- Sample selectors and endpoints are placeholders.
- Some enterprise integrations require organization-specific credentials, URLs, and notification channels.

## Known Limitations

- Current implementation is a foundation, not the final full production hardening pass.
- Accessibility, visual, OpenTelemetry, and AI modules are designed as extension points but not fully implemented.
- Contract validation currently checks allowed statuses and JSON validity; full OpenAPI validation requires an adapter.

## Technical Debt

- Keep the template lean; avoid multi-application routing unless the operating model changes.
- Add integration tests around artifact retention.
- Add real quality metric aggregation.

## Change Log

- Added platform source, documentation, governance files, and pipeline assets.

## Next Steps

- Run Maven validation after dependencies are available.
- Configure one application's URLs and secrets in each team repository.
- Enable sample tests one by one after aligning selectors and API paths.

## Future Enhancements

- AI-assisted selector healing.
- Failure clustering and root-cause recommendations.
- Synthetic test data generation with policy guardrails.
- Grafana/ELK dashboards.
- Consumer-driven contract adapters.

## Migration Notes

Application teams should pull or copy the template into their own repository and keep only their application-specific tests, page objects, data, and configuration there. Shared improvements should be proposed back to the central template deliberately.

## Context Handoff Instructions

Read this file first, then `docs/FRAMEWORK_DESIGN.md`, then inspect `pom.xml` and the package tree. Preserve the one-application-per-framework-copy operating model and update this document after each major implementation step.

## Update Rules

Update `Current Status`, `Completed Modules`, `Pending Modules`, `Decision Log`, `Known Risks`, and `Next Steps` after each significant architecture or implementation change.
