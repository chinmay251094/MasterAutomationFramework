# Test Selection And Test Data Guide

This guide explains two common needs:

1. How to run only some tests from a larger suite.
2. How to inject test data from outside the test code.

## Part 1: Running Only Selected Tests

Assume the framework has 20 tests and you want to run only 10.

There are three simple mechanisms:

- Run by test class or method.
- Run by tag.
- Exclude by tag.

## Option 1: Run Specific Test Classes

Use this when you know the exact test class names.

Example:

```bash
mvn test -Dtest=LoginTest,SearchTest,CheckoutTest
```

Run one test method:

```bash
mvn test -Dtest=LoginTest#userCanLogin
```

Run multiple matching classes:

```bash
mvn test -Dtest=*ApiTest
```

This is useful for local debugging.

## Option 2: Run Only Tests With A Tag

Use this when those 10 tests belong to the same category.

Example:

```java
@Test
@Smoke
void userCanLogin() {
    // test steps
}
```

Run only smoke tests:

```bash
mvn test -Dtags=smoke
```

The framework already has these annotations:

```java
@Smoke
@Regression
@UiTest
@ApiTest
```

You can also use JUnit's built-in `@Tag` directly:

```java
import org.junit.jupiter.api.Tag;

@Test
@Tag("release-validation")
void releaseValidationScenario() {
    // test steps
}
```

Run it:

```bash
mvn test -Dtags=release-validation
```

## Option 3: Exclude Tests With A Tag

Use this when you want to run most tests but skip a known group.

Example:

```java
import org.junit.jupiter.api.Tag;

@Test
@Tag("skip-current-run")
void testNotNeededToday() {
    // test steps
}
```

Run everything except that group:

```bash
mvn test -DexcludeTags=skip-current-run
```

Another example:

```bash
mvn test -DexcludeTags=slow
```

This runs all tests except tests tagged `slow`.

## Recommended Way For 20 Tests, Run Only 10

Preferred approach:

1. Add a meaningful tag to the 10 tests you want to run.
2. Execute that tag.

Example:

```java
@Test
@Tag("payment-smoke")
void paymentCanBeCreated() {
    // test steps
}
```

Run:

```bash
mvn test -Dtags=payment-smoke
```

Alternative approach:

1. Add a tag to the 10 tests you want to skip.
2. Exclude that tag.

Example:

```java
@Test
@Tag("not-for-release")
void experimentalScenario() {
    // test steps
}
```

Run:

```bash
mvn test -DexcludeTags=not-for-release
```

## Do Not Use @Disabled For Normal Test Selection

`@Disabled` is useful for placeholder tests or tests that are not ready.

Avoid using it for daily selection like "run these 10 today."

Good use:

```java
@Disabled("Enable after real application URL and selectors are configured")
```

Not recommended:

```java
@Disabled("Skipping for today's run")
```

For daily selection, use tags.

## Part 2: Injecting Test Data From External Sources

Do not hardcode large test data inside tests.

Supported simple mechanisms in this framework:

- System properties
- Environment variables
- JSON files
- YAML files
- CSV files
- JUnit parameterized tests

## Option 1: Inject Data Using System Properties

Use this for simple runtime values.

Run:

```bash
mvn test -Denv=qa -Dusername=test-user -Dpassword=test-password
```

Use in test:

```java
String username = System.getProperty("username");
String password = System.getProperty("password");
```

For secrets, pass them through CI/CD secrets. Do not commit real passwords.

## Option 2: Inject Data Using Environment Variables

Use this for secrets or CI/CD-controlled values.

Example:

```java
String token = System.getenv("API_TOKEN");
```

Run in CI with `API_TOKEN` configured as a secret.

## Option 3: Read JSON Test Data

Create:

```text
src/test/resources/testdata/login-user.json
```

Example JSON:

```json
{
  "username": "standard_user",
  "password": "secret"
}
```

Create a Java record in your test package:

```java
record LoginUser(String username, String password) {
}
```

Read it:

```java
TestDataFactory dataFactory = new TestDataFactory();

LoginUser user = dataFactory.json(
        Path.of("src/test/resources/testdata/login-user.json"),
        LoginUser.class
);
```

Use it:

```java
loginPage.login(user.username(), user.password());
```

## Option 4: Read YAML Test Data

Create:

```text
src/test/resources/testdata/order.yaml
```

Example YAML:

```yaml
orderId: ORD-1001
sku: SKU-123
quantity: 2
```

Java record:

```java
record OrderData(String orderId, String sku, int quantity) {
}
```

Read it:

```java
OrderData order = new TestDataFactory().yaml(
        Path.of("src/test/resources/testdata/order.yaml"),
        OrderData.class
);
```

## Option 5: Use CSV For Data-Driven Tests

Create:

```text
src/test/resources/testdata/search-keywords.csv
```

Example:

```csv
keyword
laptop
phone
tablet
```

Use JUnit parameterized test:

```java
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

class SearchDataDrivenTest extends BaseTest {
    @ParameterizedTest
    @CsvFileSource(resources = "/testdata/search-keywords.csv", numLinesToSkip = 1)
    void userCanSearchForKeyword(String keyword) {
        SearchPage searchPage = new SearchPage(page);
        searchPage.open(config.getEnvironment().getBaseUrl());
        searchPage.search(keyword);
        searchPage.verifyResultsVisible();
    }
}
```

This test runs once per CSV row.

## Option 6: API Test Data From JSON

Create:

```text
src/test/resources/testdata/create-product.json
```

Example:

```json
{
  "name": "Laptop",
  "price": 1000
}
```

Read request body:

```java
String body = Files.readString(Path.of("src/test/resources/testdata/create-product.json"));

var response = client.execute(
        ApiRequestBuilder.request(HttpMethod.POST, "/products")
                .json(body)
                .build()
);
```

Assert:

```java
ApiAssertions.status(response, 201);
```

## Recommended Data Strategy

Use this simple rule:

- Small runtime value: use `-Dproperty=value`.
- Secret: use environment variable or CI secret.
- One object: use JSON or YAML.
- Many rows: use CSV with `@ParameterizedTest`.
- API request body: store JSON under `src/test/resources/testdata`.

## Folder Recommendation

```text
src/test/resources/testdata
|-- users
|   |-- login-user.json
|-- api
|   |-- create-product.json
|-- search
|   |-- search-keywords.csv
```

## Common Commands

Run selected tag:

```bash
mvn test -Dtags=smoke
```

Exclude selected tag:

```bash
mvn test -DexcludeTags=slow
```

Run one class:

```bash
mvn test -Dtest=LoginTest
```

Run one method:

```bash
mvn test -Dtest=LoginTest#userCanLogin
```

Pass runtime data:

```bash
mvn test -Denv=qa -Dusername=test-user
```

Use secret from environment:

```bash
mvn test -Denv=qa
```

And read:

```java
System.getenv("API_TOKEN")
```
