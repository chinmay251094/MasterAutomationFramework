# Governance

## Review Gates

- Architecture review for new public APIs, dependencies, execution models, or persistence formats.
- Security review for authentication, secrets, artifact handling, and production validation flows.
- Performance review for parallel execution, browser lifecycle, and reporting storage changes.

## Quality Gates

- `mvn -B test`
- OWASP Dependency Check with build failure at CVSS 8 or higher.
- SonarQube scan with organization-defined coverage, maintainability, and security thresholds.
- Manual review of `CONTEXT.md` updates for major changes.

## Enterprise Standards

- No hardcoded secrets.
- No application-specific logic in platform core.
- Public APIs require migration notes when changed.
- Deprecated APIs require replacement guidance.
