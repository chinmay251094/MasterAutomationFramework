# External Reference Sample Cases

This folder contains runnable reference samples that use public demo systems.

The samples are intentionally separated from normal framework tests:

```text
src/test/java/com/enterprise/automation/samplecases
```

The class names end with `SampleCases`, not `Test`, so Maven Surefire does not execute them during a normal run:

```bash
mvn test
```

Run them only when you explicitly want reference examples.

## UI Samples

Location:

```text
src/test/java/com/enterprise/automation/samplecases/ui/AskOmDchUiSampleCases.java
```

Demo site:

```text
https://askomdch.com
```

Included scenarios:

- Verify the home page displays featured products.
- Open the Blue Shoes product details page.

Run:

```bash
mvn test -Dtest=AskOmDchUiSampleCases -Dbrowser=chromium -Dheadless=false
```

Override the sample UI URL:

```bash
mvn test -Dtest=AskOmDchUiSampleCases -Dsample.ui.baseUrl=https://askomdch.com
```

## API Samples

Location:

```text
src/test/java/com/enterprise/automation/samplecases/api/SimpleGroceryApiSampleCases.java
```

Demo API:

```text
https://simple-grocery-store-api.click
```

Included scenarios:

- Verify API status is `UP`.
- Get one available product, create a cart, add the product to the cart, and verify cart items.

Run:

```bash
mvn test -Dtest=SimpleGroceryApiSampleCases
```

Override the sample API URL:

```bash
mvn test -Dtest=SimpleGroceryApiSampleCases -Dsample.api.baseUrl=https://simple-grocery-store-api.click
```

## Run All External Samples

```bash
mvn test -Dtest=*SampleCases -Dbrowser=chromium -Dheadless=false
```

## How To Omit Them

Do nothing. They are omitted from normal execution because their class names do not match the default Surefire test naming pattern.

If a team renames them to end with `Test`, keep the tag and exclude it:

```bash
mvn test -DexcludeTags=sample-reference
```

## Notes

These tests depend on public external systems, so they should be treated as learning/reference samples, not release gates for an application team.
