# Defect Triage Matrix

How failures from this suite are classified, prioritised and routed. **Severity** is the technical impact; **priority** is how soon it must be fixed. They are set independently.

## 1. Severity definitions

| Severity | Definition | Example in this app |
|---|---|---|
| **S1 – Critical** | Core journey blocked for all users; data loss; security exposure | Login fails for every valid user; account deletion deletes the wrong account |
| **S2 – High** | Core journey broken for a segment, or a key function wrong with no workaround | Cart drops items after login; search returns unrelated products |
| **S3 – Medium** | Function wrong but a workaround exists, or a non-core feature broken | Brand filter empty; wrong error text on invalid login |
| **S4 – Low** | Cosmetic, copy or minor UX issue | Misaligned header; typo in a product description |

## 2. Priority matrix (severity × user reach)

| | **Most users** | **Some users** | **Edge case** |
|---|---|---|---|
| **S1** | P1: hotfix now | P1: hotfix now | P2: next release |
| **S2** | P1: hotfix now | P2: next release | P3: backlog, planned |
| **S3** | P2: next release | P3: backlog, planned | P4: backlog |
| **S4** | P3: backlog, planned | P4: backlog | P4: backlog |

## 3. Failure triage flow for automated runs

| Step | Question | If yes | If no |
|---|---|---|---|
| 1 | Did it pass on retry or a re-run with no code change? | Mark **flaky**, quarantine (`groups = "quarantine"`), raise a test-debt ticket | Go to 2 |
| 2 | Is the environment or a dependency down (API 5xx, site unreachable, ads overlay)? | **Environment issue**: notify owner, re-run later, no product defect | Go to 3 |
| 3 | Did the app change intentionally (new locator, copy change, feature change)? | **Test maintenance**: update page object or test data | Go to 4 |
| 4 | Does the app behave contrary to requirements? | **Product defect**: log with severity and priority from the tables above | Re-investigate; pair with a developer |

## 4. Minimum defect report contents

| Field | Source |
|---|---|
| Title: *[Area] observed behaviour when action* | Tester |
| Steps to reproduce, expected and actual result | Allure steps for the failed test |
| Evidence | Allure screenshot, page URL, API request/response attachments |
| Environment | Browser and version, headless or not, commit SHA, CI run link |
| Severity and priority | Tables above |

## 5. SLA targets

| Priority | Acknowledge | Fix target |
|---|---|---|
| P1 | 1 hour | Same day (hotfix) |
| P2 | 1 business day | Next release |
| P3 | 3 business days | Planned sprint |
| P4 | Next grooming | As capacity allows |
