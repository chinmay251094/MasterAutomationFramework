# Release Process

## Versioning

Use semantic versioning:

- MAJOR: breaking public API or behavior changes.
- MINOR: backward-compatible features.
- PATCH: backward-compatible fixes.

## Flow

1. Freeze changes on a `release/*` branch.
2. Run unit, smoke, regression, dependency-check, and SonarQube gates.
3. Update release notes and migration notes.
4. Tag the release as `vMAJOR.MINOR.PATCH`.
5. Publish Maven artifacts and CI reports.
6. Announce deprecations and migration guidance.

## Deprecation

Deprecated APIs must include a replacement path and remain available for at least one minor release unless a security issue requires faster removal.
