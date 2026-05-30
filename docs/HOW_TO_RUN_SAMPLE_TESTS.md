# How To Run Sample Tests

This guide explains how to execute the sample UI, API, and hybrid tests in this framework.

## 1. Prerequisites

Install:

- Java 21
- Maven 3.9+
- Git, if you are using source control

Confirm setup:

```bash
java -version
mvn -version
```

Expected Java major version: `21`.

## 2. Understand The Current Sample State

The sample tests are intentionally disabled because this repository does not know your real application URLs, selectors, credentials, or API paths yet.

Current sample tests:

- UI login: `src/test/java/com/enterprise/automation/tests/ui/LoginTest.java`
- UI search: `src/test/java/com/enterprise/automation/tests/ui/SearchTest.java`
- UI checkout: `src/test/java/com/enterprise/automation/tests/ui/CheckoutTest.java`
- API CRUD/schema/contract: `src/test/java/com/enterprise/automation/tests/api/SampleApiTest.java`
- Hybrid UI + API: `src/test/java/com/enterprise/automation/tests/hybrid/HybridUiApiTest.java`

When you run the framework as-is, Maven should pass and report skipped sample tests.

```bash
mvn -B test
```

This validates that the framework compiles, JUnit discovers the tests, and the project is healthy.

## 3. Install Playwright Browsers

Before running UI tests, install browser binaries:

```bash
mvn exec:java -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="install"
```

For Linux CI runners, use:

```bash
mvn exec:java -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="install --with-deps"
```

## 4. Configure Environment URLs

Edit:

```text
src/main/resources/environment.yaml
```

Example:

```yaml
environments:
  qa:
    baseUrl: https://your-qa-app.company.com
    apiBaseUrl: https://your-qa-api.company.com
```

Then run with:

```bash
mvn test -Denv=qa
```

Supported environments:

- `dev`
- `qa`
- `sit`
- `uat`
- `perf`
- `stage`
- `prod`

## 5. Enable A UI Sample Test

Open a sample test, for example:

```text
src/test/java/com/enterprise/automation/tests/ui/LoginTest.java
```

Remove or comment this annotation:

```java
@Disabled("Sample application placeholder; enable after configuring environment.yaml")
```

Then update selectors and credentials in:

```text
src/test/java/com/enterprise/automation/tests/pages/LoginPage.java
```

Current placeholder selectors:

```java
[name='username']
[name='password']
button[type='submit']
```

Change them to match your application.

Run the login sample:

```bash
mvn test -Denv=qa -Dbrowser=chromium -Dheadless=false -Dtest=LoginTest
```

Use `-Dheadless=false` locally so you can watch the browser.

## 6. Enable API Sample Test

Open:

```text
src/test/java/com/enterprise/automation/tests/api/SampleApiTest.java
```

Remove or comment this annotation:

```java
@Disabled("Sample API placeholders; enable after configuring environment.yaml")
```

Update:

- `/products`
- `/products/1`
- Request bodies
- Expected status codes
- Authentication token handling

Run only the API sample:

```bash
mvn test -Denv=qa -Dtest=SampleApiTest
```

## 7. Enable Hybrid Sample Test

Open:

```text
src/test/java/com/enterprise/automation/tests/hybrid/HybridUiApiTest.java
```

Remove or comment the `@Disabled` annotation.

Update:

- API setup endpoint
- UI URL path
- UI selectors/text
- API cleanup endpoint

Run:

```bash
mvn test -Denv=qa -Dbrowser=chromium -Dheadless=false -Dtest=HybridUiApiTest
```

## 8. Run By Browser

```bash
mvn test -Denv=qa -Dbrowser=chromium
mvn test -Denv=qa -Dbrowser=firefox
mvn test -Denv=qa -Dbrowser=webkit
```

## 9. Run By Tag

The framework provides JUnit 5 tag annotations:

- `@Smoke`
- `@Regression`
- `@UiTest`
- `@ApiTest`

Examples:

```bash
mvn test -Denv=qa -Dtags=smoke
mvn test -Denv=qa -Dtags=regression
mvn test -Denv=qa -Dtags=ui
mvn test -Denv=qa -Dtags=api
```

## 10. Reports And Artifacts

After execution, check:

```text
target/surefire-reports
target/allure-results
target/artifacts
target/logs
```

Generate Allure report:

```bash
mvn allure:report
```

Open the generated report from:

```text
target/site/allure-maven-plugin/index.html
```

## 11. Common Execution Commands

Run framework health check:

```bash
mvn -B test
```

Run one UI test visibly:

```bash
mvn test -Denv=qa -Dbrowser=chromium -Dheadless=false -Dtest=LoginTest
```

Run one API test:

```bash
mvn test -Denv=qa -Dtest=SampleApiTest
```

Run smoke tests:

```bash
mvn test -Denv=qa -Dtags=smoke
```

Run in CI mode:

```bash
mvn -B test -Denv=qa -Dbrowser=chromium -Dheadless=true
```

## 12. Troubleshooting

| Problem | Likely Cause | Fix |
| --- | --- | --- |
| Tests are skipped | `@Disabled` is still present | Remove `@Disabled` after configuring URLs/selectors |
| Browser does not launch | Playwright browsers are not installed | Run the Playwright install command |
| UI test cannot find element | Placeholder selector does not match your app | Update page object selectors |
| API test returns 404 | Placeholder endpoint does not exist | Update API paths |
| API test returns 401/403 | Missing or invalid auth | Wire token/header handling |
| Running by tag executes nothing | No enabled test has that tag | Enable tagged tests or add the tag |
