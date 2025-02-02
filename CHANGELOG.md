# Changelog

## 0.6.0 (BREAKING)
- **Breaking: Automatically attach `onBackPressedDispatcher` in `setContentNavigable()`.** Now, the only code required to attach Magellan to your `MainActivity` is `setContentNavigable()`.
- Make compose previews work without using interactive mode for `Journey`s and nested `Step`s
- Bump target and compile SDK level from 34 to 35
- Update dependencies, including to Kotlin `2.1.0` and Compose BOM to `2024.12.01`

## 0.5.2
- Update dependencies, including to Kotlin `1.9.23`, Compose BOM to `2024.03.00`, Compose Compiler to `1.5.11`.

## 0.5.1
- Fix transitions showing when they shouldn't when `Journey`s are first shown.

## 0.5.0
- BREAKING: Add `Started` lifecycle state
  - `Started` replaces `Shown` and represents "being drawn to the screen", but `Shown` now represents "on top of the backstack". `show()` and `hide()` are now effectively `navigatedTo()` and `navigatedFrom()` respectively.

| State               | On backstack | Top of backstack | View created + <br/>Activity available + <br/>Compose active | In focus |
|---------------------|--------------|------------------|--------------------------------------------------------------|----------|
| Destroyed           | ✘            | ✘                | ✘                                                            | ✘        |
| Created             | ✔ (roughly)  | ✘                | ✘                                                            | ✘        |
| **Shown** (changed) | ✔            | ✔                | ✘                                                            | ✘        |
| **Started** (new)   | ✔            | ✔                | ✔                                                            | ✘        |
| Resumed             | ✔            | ✔                | ✔                                                            | ✔        |

- Call pause() when starting to transition away from a screen

## 0.4.1
- Expose `ComposeNavigator.currentNavigable` and `currentNavigableFlow`

## 0.4.0
- Update Kotlin to `1.9.22` and compose compiler to `1.5.8`
- Update Compose BOM to `2024.01.00` (Compose version `1.6.0`)
- Update other internal dependencies

## 0.3.1
- Fix Jitpack build

## 0.3.0
- Update Kotlin to `1.9.21` and the compose compiler to `1.5.7`
- Update compose BOM to `2023.10.01`
- Update and/or remove other internal dependencies

## 0.2.0
- Update compose to BOM version `2022.11.00`
- Update Kotlin to `1.7.10` and the compose compiler to `1.3.1`
- Update lots of other internal dependencies

## 0.1.2
- Removed `Context` from lifecycle events

## 0.1.1
- Fixed artifact name for `magellanx-test`

## 0.1.0
- Copied code in from Stickers app
