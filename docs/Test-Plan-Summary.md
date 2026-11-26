# Test Plan Summary

| Item | Detail |
|---|---|
| **System under test** | [Automation Exercise](https://automationexercise.com): public e-commerce demo with UI and REST API |
| **Objective** | Automated regression of core shopping journeys, with API-driven test data and contract checks on the public API |
| **Approach** | Hybrid: API for setup, teardown and service-level checks; UI only for user-facing behaviour |
| **Owner** | Avinash Kumar Verma |

## 1. Scope

**In scope**

| Area | Layer | Coverage |
|---|---|---|
| Authentication | UI | Valid login (API-seeded user), invalid credentials, logout/re-login |
| Product search and cart | UI | Search relevance, add to cart, cart contents |
| Account lifecycle | API | Create, verify login, delete, duplicate-email rejection |
| Product catalogue | API | List shape, search relevance, method not allowed (405), missing parameter (400), brands |

**Out of scope:** payment processing (demo site, no real gateway), performance/load, accessibility audit, mobile browsers, visual regression.

## 2. Test strategy

| Level | Tooling | Rationale |
|---|---|---|
| API | REST Assured + TestNG | Fast, stable and cheap to run; covers business rules without a browser |
| UI | Selenium 4 + Page Objects | Only journeys a user actually sees; each test seeds its own data via API |
| Reporting | Allure (steps, request/response, screenshots on failure) | One report that a developer can triage from |
| Execution | TestNG `parallel="methods"`, `ThreadLocal<WebDriver>`, Docker, GitHub Actions | Parallel, isolated, reproducible on any machine |

**Test data:** each UI test creates a unique user via `POST /api/createAccount` and deletes it in `@AfterMethod`, even if the test fails. No shared accounts, so tests can run in any order and in parallel.

## 3. Entry and exit criteria

| | Criteria |
|---|---|
| **Entry** | Build compiles; target site and API reachable; config reviewed |
| **Exit** | 100% of smoke tests pass; ≥ 95% of regression tests pass; no open Critical/High defects without an agreed workaround; flaky tests quarantined with a ticket |

## 4. Environments and configuration

| Setting | Default | Override |
|---|---|---|
| Browser | Chrome | `-Dbrowser=firefox` |
| Headless | false locally, true in CI and Docker | `-Dheadless=true` |
| Suite | `testng.xml` (full) | `-Dsuite=testng-smoke.xml` |
| Base URL | `https://automationexercise.com` | `-Dbase.url=...` |

## 5. Risks and mitigations

| Risk | Impact | Mitigation |
|---|---|---|
| Third-party ads and interstitials on the demo site intercept clicks | Flaky UI failures | Navigate by URL; JS-click fallback when a click is intercepted |
| Public demo data changes (products renamed or removed) | False failures | Assertions check relevance (e.g. contains "top"), not exact catalogue positions |
| Demo API always returns HTTP 200 with status in body | Real errors look like passes | `ApiResult` asserts on the body's `responseCode` |
| Shared public environment, rate limits | Intermittent timeouts | Explicit waits only, unique data per test, limited thread count (3) |

## 6. Deliverables

- Allure report (CI artifact on every run) and Surefire XML reports
- Defect reports triaged using the [Defect Triage Matrix](Defect-Triage-Matrix.md)
