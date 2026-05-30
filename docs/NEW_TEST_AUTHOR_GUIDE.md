# New Test Author Guide

This guide is for someone new to the team who needs to automate a UI webpage scenario or an API test scenario.

Use this when you are thinking:

> I have a manual test case. Where do I add locators, actions, API calls, and assertions?

## 1. First Understand The Test Scenario

Before writing code, write down:

- Which environment will be tested: `qa`, `uat`, `stage`, etc.
- Is it a UI test, API test, or UI + API hybrid test?
- What is the starting page or API endpoint?
- What are the test steps?
- What is the expected result?
- Does the test need login?
- Does the test need test data?

Example manual UI scenario:

```text
Login to the application.
Search for a product.
Verify search results are displayed.
```

Example API scenario:

```text
Send GET /products.
Verify status code is 200.
Verify response body contains products.
```

## 2. Check Or Update The Application URL

Application URLs are configured here:

```text
src/main/resources/environment.yaml
```

Example:

```yaml
environments:
  qa:
    baseUrl: https://qa-myapp.company.com
    apiBaseUrl: https://qa-api-myapp.company.com
```

UI tests use:

```java
config.getEnvironment().getBaseUrl()
```

API tests use:

```java
config.getEnvironment().getApiBaseUrl()
```

Do not hardcode full URLs inside tests.

## 3. How To Add A UI Test

For UI automation, use this pattern:

```text
Page Object = locators + page actions
Test Class  = test steps + assertions
```

Put page objects here:

```text
src/test/java/com/enterprise/automation/tests/pages
```

Put UI tests here:

```text
src/test/java/com/enterprise/automation/tests/ui
```

## 4. Where To Add Locators

Add locators inside the page object class.

Example page object:

```text
src/test/java/com/enterprise/automation/tests/pages/LoginPage.java
```

Example:

```java
public class LoginPage extends BasePage {
    public LoginPage(Page page) {
        super(page);
    }

    public void open(String baseUrl) {
        page.navigate(baseUrl + "/login");
    }

    public void login(String username, String password) {
        locator(LocatorStrategy.CSS, "[name='username']").fill(username);
        locator(LocatorStrategy.CSS, "[name='password']").fill(password);
        locator(LocatorStrategy.CSS, "button[type='submit']").click();
    }
}
```

In simple words:

- Put element selectors in the page object.
- Put user actions in methods like `login`, `search`, `checkout`, `submit`.
- Keep the test class clean and readable.

## 5. How To Choose Locator Strategy

Use `LocatorStrategy` from the framework:

```java
locator(LocatorStrategy.CSS, "[name='username']")
locator(LocatorStrategy.TEXT, "Login")
locator(LocatorStrategy.TEST_ID, "search-input")
```

Recommended priority:

1. Use test IDs when available: `LocatorStrategy.TEST_ID`
2. Use stable CSS selectors when test IDs are not available: `LocatorStrategy.CSS`
3. Use visible text when the text is stable: `LocatorStrategy.TEXT`

Avoid fragile selectors like:

```java
div > div > div:nth-child(3) > button
```

## 6. Where To Add Methods For Locators

Do not expose every locator directly to the test.

Good page object method:

```java
public void search(String keyword) {
    locator(LocatorStrategy.TEST_ID, "search-input").fill(keyword);
    locator(LocatorStrategy.TEST_ID, "search-submit").click();
}
```

Good verification method:

```java
public void verifyResultsVisible() {
    UiAssertions.visible(locator(LocatorStrategy.TEST_ID, "search-results"));
}
```

Then the test reads clearly:

```java
searchPage.search("laptop");
searchPage.verifyResultsVisible();
```

## 7. Example: Add A New UI Page Object

Create a new file:

```text
src/test/java/com/enterprise/automation/tests/pages/SearchPage.java
```

Example:

```java
package com.enterprise.automation.tests.pages;

import com.enterprise.automation.ui.BasePage;
import com.enterprise.automation.ui.LocatorStrategy;
import com.enterprise.automation.ui.UiAssertions;
import com.microsoft.playwright.Page;

public class SearchPage extends BasePage {
    public SearchPage(Page page) {
        super(page);
    }

    public void open(String baseUrl) {
        page.navigate(baseUrl + "/search");
    }

    public void search(String keyword) {
        locator(LocatorStrategy.TEST_ID, "search-input").fill(keyword);
        locator(LocatorStrategy.TEST_ID, "search-submit").click();
    }

    public void verifyResultsVisible() {
        UiAssertions.visible(locator(LocatorStrategy.TEST_ID, "search-results"));
    }
}
```

## 8. Example: Add A New UI Test

Create a test file:

```text
src/test/java/com/enterprise/automation/tests/ui/ProductSearchTest.java
```

Example:

```java
package com.enterprise.automation.tests.ui;

import com.enterprise.automation.annotations.Smoke;
import com.enterprise.automation.annotations.UiTest;
import com.enterprise.automation.tests.BaseTest;
import com.enterprise.automation.tests.pages.SearchPage;
import org.junit.jupiter.api.Test;

@UiTest
class ProductSearchTest extends BaseTest {
    @Test
    @Smoke
    void userCanSearchForProduct() {
        SearchPage searchPage = new SearchPage(page);

        searchPage.open(config.getEnvironment().getBaseUrl());
        searchPage.search("laptop");
        searchPage.verifyResultsVisible();
    }
}
```

Run this test:

```bash
mvn test -Denv=qa -Dbrowser=chromium -Dheadless=false -Dtest=ProductSearchTest
```

## 9. How To Add An API Test

Put API tests here:

```text
src/test/java/com/enterprise/automation/tests/api
```

Use these framework classes:

```java
ApiClient
ApiFactory
ApiRequestBuilder
HttpMethod
ApiAssertions
SchemaValidator
ContractValidator
```

API tests should use relative paths, not full URLs.

Good:

```java
ApiRequestBuilder.request(HttpMethod.GET, "/products")
```

Avoid:

```java
ApiRequestBuilder.request(HttpMethod.GET, "https://qa-api.company.com/products")
```

## 10. Example: Add A GET API Test

Create:

```text
src/test/java/com/enterprise/automation/tests/api/ProductApiTest.java
```

Example:

```java
package com.enterprise.automation.tests.api;

import com.enterprise.automation.annotations.ApiTest;
import com.enterprise.automation.annotations.Smoke;
import com.enterprise.automation.api.ApiAssertions;
import com.enterprise.automation.api.ApiClient;
import com.enterprise.automation.api.ApiFactory;
import com.enterprise.automation.api.ApiRequestBuilder;
import com.enterprise.automation.api.HttpMethod;
import com.enterprise.automation.config.ConfigLoader;
import com.enterprise.automation.ui.PlaywrightManager;
import org.junit.jupiter.api.Test;

@ApiTest
class ProductApiTest {
    @Test
    @Smoke
    void userCanGetProducts() {
        try (PlaywrightManager manager = PlaywrightManager.create()) {
            ApiClient client = new ApiFactory()
                    .create(manager.playwright(), ConfigLoader.get().getEnvironment().getApiBaseUrl());

            var response = client.execute(
                    ApiRequestBuilder.request(HttpMethod.GET, "/products").build()
            );

            ApiAssertions.status(response, 200);
            ApiAssertions.bodyContains(response, "product");
        }
    }
}
```

Run this test:

```bash
mvn test -Denv=qa -Dtest=ProductApiTest
```

## 11. Example: Add A POST API Test

Example:

```java
var response = client.execute(
        ApiRequestBuilder.request(HttpMethod.POST, "/products")
                .json("{\"name\":\"Laptop\",\"price\":1000}")
                .build()
);

ApiAssertions.status(response, 201);
```

For PUT:

```java
ApiRequestBuilder.request(HttpMethod.PUT, "/products/1001")
        .json("{\"name\":\"Laptop Pro\",\"price\":1500}")
        .build();
```

For PATCH:

```java
ApiRequestBuilder.request(HttpMethod.PATCH, "/products/1001")
        .json("{\"price\":1200}")
        .build();
```

For DELETE:

```java
ApiRequestBuilder.request(HttpMethod.DELETE, "/products/1001")
        .build();
```

## 12. How To Add Authentication To API Tests

For bearer token:

```java
var response = client.execute(
        ApiRequestBuilder.request(HttpMethod.GET, "/profile")
                .bearer(System.getProperty("apiToken"))
                .build()
);
```

Run:

```bash
mvn test -Denv=qa -DapiToken=your-token
```

In CI, use secrets. Do not put real tokens in code.

## 13. How To Add A Hybrid UI + API Test

Use a hybrid test when:

- API creates test data, UI verifies it.
- UI performs an action, API verifies backend state.
- API logs in, UI reuses session.
- API cleans up data after UI execution.

Put hybrid tests here:

```text
src/test/java/com/enterprise/automation/tests/hybrid
```

Example flow:

```java
@UiTest
@ApiTest
class CartHybridTest extends BaseTest {
    @Test
    void apiCreatesCartUiVerifiesCart() {
        ApiClient api = new ApiFactory()
                .create(playwrightManager.playwright(), config.getEnvironment().getApiBaseUrl());

        api.execute(ApiRequestBuilder.request(HttpMethod.POST, "/cart")
                .json("{\"sku\":\"SKU-100\"}")
                .build());

        page.navigate(config.getEnvironment().getBaseUrl() + "/cart");
        page.getByText("SKU-100").waitFor();

        api.execute(ApiRequestBuilder.request(HttpMethod.DELETE, "/cart/SKU-100").build());
    }
}
```

## 14. Where To Put Test Data

If the test data is small, keep it inside the test while starting.

For reusable data, create:

```text
src/test/resources/testdata
```

Examples:

```text
src/test/resources/testdata/users.json
src/test/resources/testdata/products.json
src/test/resources/testdata/orders.yaml
```

Keep secrets out of test data files.

## 15. Which Annotation Should I Use

Use these annotations on test classes or methods:

```java
@UiTest
@ApiTest
@Smoke
@Regression
```

Example:

```java
@UiTest
class LoginTest extends BaseTest {
    @Test
    @Smoke
    void userCanLogin() {
        // test steps
    }
}
```

Run smoke tests:

```bash
mvn test -Denv=qa -Dtags=smoke
```

Run UI tests:

```bash
mvn test -Denv=qa -Dtags=ui
```

Run API tests:

```bash
mvn test -Denv=qa -Dtags=api
```

## 16. Basic Folder Map

```text
src/main/resources/environment.yaml
  UI and API URLs

src/test/java/com/enterprise/automation/tests/pages
  Page objects, locators, and UI action methods

src/test/java/com/enterprise/automation/tests/ui
  UI test classes

src/test/java/com/enterprise/automation/tests/api
  API test classes

src/test/java/com/enterprise/automation/tests/hybrid
  UI + API combined test classes

src/test/resources/testdata
  Reusable test data
```

## 17. Before You Commit

Run:

```bash
mvn -B test
```

Check:

- Did the test pass locally?
- Did you avoid hardcoded URLs?
- Did you avoid hardcoded secrets?
- Are locators inside page objects?
- Are test steps readable?
- Did you use tags such as `@Smoke`, `@Regression`, `@UiTest`, or `@ApiTest`?

## 18. Simple Rule To Remember

For UI:

```text
Locator goes in Page Object.
Action method goes in Page Object.
Test steps go in Test Class.
```

For API:

```text
Base API URL goes in environment.yaml.
Endpoint path goes in ApiRequestBuilder.
Assertion goes after the response.
```
