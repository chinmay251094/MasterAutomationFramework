# Troubleshooting

| Issue | Resolution |
| --- | --- |
| Maven cannot resolve dependencies | Confirm network access and internal proxy/repository settings. |
| Playwright browsers are missing | Install browsers in the runner or Docker image before executing tests. |
| Tests hit the wrong URL | Check `environment.yaml` and the `-Denv` value. |
| Browser runs visibly in CI | Set `-Dheadless=true`. |
| Missing screenshots | Ensure `target/artifacts` is writable. |
| Tags do not filter tests | Use configured JUnit 5 tags and Surefire groups. |
| Secrets appear in logs | Move credentials to environment variables or CI secret stores and mask pipeline output. |
