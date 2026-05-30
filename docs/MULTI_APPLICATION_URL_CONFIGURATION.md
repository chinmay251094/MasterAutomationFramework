# Application Team URL Configuration

This framework is intended to be used as a template. The central automation team pushes this framework to Git, and each application team creates its own isolated copy for its application.

The goal is simple:

- One application team owns one framework copy.
- One framework copy targets one application.
- UI and API URLs are updated through configuration only.
- Tests, page objects, data, and CI settings stay isolated per application team.
- No single framework repository should contain tests for many unrelated applications.

## Recommended Operating Model

Central framework repository:

```text
enterprise-automation-framework-template
```

Application team repositories:

```text
payments-automation
orders-automation
customer-profile-automation
claims-automation
```

Each team takes a fresh pull or copy of the template and updates only its own project.

```text
payments-automation
|-- src/main/resources/environment.yaml
|-- src/test/java
|-- src/test/resources
|-- pom.xml
|-- Jenkinsfile
|-- Dockerfile

orders-automation
|-- src/main/resources/environment.yaml
|-- src/test/java
|-- src/test/resources
|-- pom.xml
|-- Jenkinsfile
|-- Dockerfile
```

The repositories are independent. Payments tests do not live beside Orders tests.

## Why This Model

This avoids clutter and ownership conflicts. If three applications are automated in the same framework repository, the project quickly becomes noisy:

- Different URLs
- Different credentials
- Different test data
- Different page objects
- Different release cycles
- Different pipeline needs
- Different owners changing shared files

Keeping one framework copy per application keeps the project understandable for each team.

## What Each Team Should Update

After taking a pull/copy of the framework template, each application team should update:

1. `src/main/resources/environment.yaml`
2. Application-specific page objects under `src/test/java`
3. Application-specific API tests under `src/test/java`
4. Test data under `src/test/resources`, if needed
5. CI/CD variables and secrets
6. Sample tests, or replace samples with real tests

Teams should not modify framework internals unless they are intentionally enhancing their own copy.

## UI And API URL File

Update:

```text
src/main/resources/environment.yaml
```

Example for a Payments application:

```yaml
environments:
  dev:
    baseUrl: https://dev-payments.company.com
    apiBaseUrl: https://dev-api-payments.company.com
  qa:
    baseUrl: https://qa-payments.company.com
    apiBaseUrl: https://qa-api-payments.company.com
  sit:
    baseUrl: https://sit-payments.company.com
    apiBaseUrl: https://sit-api-payments.company.com
  uat:
    baseUrl: https://uat-payments.company.com
    apiBaseUrl: https://uat-api-payments.company.com
  perf:
    baseUrl: https://perf-payments.company.com
    apiBaseUrl: https://perf-api-payments.company.com
  stage:
    baseUrl: https://stage-payments.company.com
    apiBaseUrl: https://stage-api-payments.company.com
  prod:
    baseUrl: https://payments.company.com
    apiBaseUrl: https://api-payments.company.com
```

For an Orders application, the Orders team has a separate repository and its own file:

```yaml
environments:
  qa:
    baseUrl: https://qa-orders.company.com
    apiBaseUrl: https://qa-api-orders.company.com
  uat:
    baseUrl: https://uat-orders.company.com
    apiBaseUrl: https://uat-api-orders.company.com
  prod:
    baseUrl: https://orders.company.com
    apiBaseUrl: https://api-orders.company.com
```

Do not put Payments and Orders into the same `environment.yaml`.

## How To Run Against An Environment

Run QA:

```bash
mvn test -Denv=qa
```

Run UAT:

```bash
mvn test -Denv=uat
```

Run production validation:

```bash
mvn test -Denv=prod -Dtags=production-validation
```

The same command works for every team because each team repository has its own URLs.

## UI URL Usage

Tests and page objects should use the configured UI URL:

```java
LoginPage loginPage = new LoginPage(page);
loginPage.open(config.getEnvironment().getBaseUrl());
```

Page object:

```java
public void open(String baseUrl) {
    page.navigate(baseUrl + "/login");
}
```

Good:

```java
page.navigate(config.getEnvironment().getBaseUrl() + "/cart");
```

Avoid:

```java
page.navigate("https://qa-payments.company.com/cart");
```

## API URL Usage

API tests should create clients with the configured API URL:

```java
ApiClient client = new ApiFactory()
        .create(manager.playwright(), ConfigLoader.get().getEnvironment().getApiBaseUrl());
```

Requests should use relative paths:

```java
client.execute(ApiRequestBuilder.request(HttpMethod.GET, "/products").build());
```

Good:

```java
ApiRequestBuilder.request(HttpMethod.GET, "/orders/123")
```

Avoid:

```java
ApiRequestBuilder.request(HttpMethod.GET, "https://qa-api-payments.company.com/orders/123")
```

## CI/CD Setup Per Team

Each team should configure its own pipeline for its isolated repository.

Example QA pipeline command:

```bash
mvn -B test -Denv=qa -Dbrowser=chromium -Dheadless=true
```

Example smoke command:

```bash
mvn -B test -Denv=qa -Dtags=smoke
```

Example release validation:

```bash
mvn -B test -Denv=stage -Dtags=release-validation
```

## Secrets

URLs can live in `environment.yaml`. Secrets must not.

Do not store:

- Passwords
- Tokens
- API keys
- Client secrets
- Private certificates

Use:

- GitHub Secrets
- Jenkins Credentials
- Environment variables
- Enterprise secret managers

Example:

```bash
mvn test -Denv=qa -DapiToken=$API_TOKEN
```

## What Not To Do

Do not create this kind of structure in one framework repository:

```text
src/test/java/com/company/payments
src/test/java/com/company/orders
src/test/java/com/company/customerprofile
src/test/java/com/company/claims
```

Do not add app-specific switches like:

```bash
mvn test -Dapp=payments -Denv=qa
mvn test -Dapp=orders -Denv=qa
```

That model is intentionally avoided because it turns the framework into a shared, cluttered test warehouse.

## New Team Setup Checklist

1. Pull or copy the framework template into a new application repository.
2. Rename the repository for the application, for example `payments-automation`.
3. Update `src/main/resources/environment.yaml` with only that application's URLs.
4. Update or replace sample page objects.
5. Update or replace sample UI/API/hybrid tests.
6. Configure GitHub/Jenkins secrets for credentials.
7. Run `mvn -B test` to verify the framework.
8. Enable one sample or smoke test.
9. Run `mvn test -Denv=qa -Dbrowser=chromium -Dheadless=false`.
10. Add the team's real regression suite incrementally.

## Summary

Use this framework as a clean template, not as one central repository for every application.

Each team gets an isolated copy, updates its own URLs, owns its own tests, and runs the same Maven commands without changing core framework code.
