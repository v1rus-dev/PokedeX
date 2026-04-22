# Project Overview

Pokedex is a multi-module Compose Multiplatform application built with Kotlin, targeting Android and iOS (iosArm64, iosSimulatorArm64).

## Expert Profile

You are an expert in Kotlin Multiplatform, Compose Multiplatform, Clean Architecture, and MVI.
Use feature-first organization and preserve module boundaries.

## Documentation Requirements

- Keep code explicit so comments are rarely needed.
- Add comments only for non-obvious decisions or tricky constraints.
- Do not add tests; tests are not required in this project.

### Updating this document

AI agents should update this file whenever they learn reusable project-wide rules:
architecture patterns, common UI conventions, shared infrastructure constraints.
Do not add feature-specific one-off details.

## Architecture

This is a multi-module Compose Multiplatform project with Gradle Kotlin DSL.

Main areas:
- `androidApp/` - Android application entry point, hosts the Compose root.
- `iosApp/` - iOS application on swift
- `composeApp/` - shared Compose UI entry point, root navigation host, startup orchestration, hosts the Compose root for iOS via UIViewController.
- `core/` - shared infrastructure modules:
    - `common/` - shared utilities, base classes, extensions.
    - `config/` - BuildConfig.
    - `database/` - local persistence.
    - `design/` - design system, theme, shared UI components, mvi realization.
    - `network/api/` - common network utilities.
    - `network/impl/` - network implementation (Ktor).
    - `resources/` - shared multiplatform resources (strings, images, fonts).
- `features/` - user-facing feature modules; each feature is split into:
    - `<feature>/api/` - public contracts: repository/use-case interfaces and navigation keys (`NavKey`).
    - `<feature>/ui/` - reusable feature-scoped UI adapters: shared UI models, mappers, resource-backed specs, formatters for presentation modules.
    - `<feature>/impl/` - implementation split into `data/`, `domain/`, `presentation/`.
- `build-logic-convention/` - convention plugins that apply base configuration to modules.

Use standard KMP source sets:
- `commonMain/kotlin`
- `androidMain/kotlin`
- `iosMain/kotlin`
- `commonMain/resources`

`iOSMain` enabled only on Mac system, this is disabled for other via ConventionPlugins.

Gradle command for updating resources generated class `Res` after updating string, drawable and e.c use: 
- `./gradlew :core:resources:build` - update `Res` class.

### Navigation architecture

Navigation is built on **Navigation3** and is type-safe and feature-owned.

- Each destination key is a `NavKey` (`data object` / `data class`), defined in the feature's `api` module.
- Each feature registers its own destinations via koin and koin-navigation3 library with using navigation<NavKey>{} / back-stack host in `composeApp`.
- Cross-feature navigation uses only `NavKey` contracts from the target feature's `api` module; never import internal `presentation` classes of another feature.
- Do not add new global navigation layers in ad-hoc root packages; place logic inside the owning feature or `composeApp`.

Destination flow pattern:
- `*Destination` composable receives navigation back-stack controller and route params decoded from `NavKey`.
- `*Destination` creates `ViewModel` via the DI integration.
- `*Destination` collects `uiState` from `ViewModel`.
- `*Destination` observes one-shot `EventUi` through `CollectEventsUiEffect`.
- `*Destination` handles navigation/side effects from events.
- `*Screen` composable is called with pure `state` + `onAction` and does not own navigation orchestration.

### Feature ownership

- Each feature owns its own `presentation`, `domain`, and `data` layers inside its `impl` module.
- Public contracts (interfaces, `NavKey`s) live in the feature's `api` module.
- When multiple presentation modules need the same feature-specific UI adaptation, place shared UI models/mappers/resource mapping in a sibling `<feature>/ui` module instead of `api`.
- Keep `<feature>/ui` reusable and presentation-focused: it may depend on `core:design`, but it must not contain screen-level `StateUi`/`ActionUi`/`EventUi`, navigation orchestration, or feature DI modules.
- Cross-feature navigation uses typed `NavKey` contracts, never internal implementation classes.
- Do not add new global business layers in ad-hoc root packages; place logic inside the owning feature or a dedicated `core` module.
- Cross-feature data synchronization should use contracts from `features/sync-data/api`; each feature-specific sync use case decides whether to skip based on local data state, while orchestration calls a single sync entry point and may pass `force = true` for manual full refresh.

## Dependency Injection

- Use Koin like DI solution.
- Prefer constructor injection.
- Keep DI bindings close to their feature (`features/<feature>/impl/(data/domain/presentation)/di/`).
- If multiple implementations must be resolved via `getAll<Interface>()`, do not register several unqualified definitions with the same interface as the primary type. Register each implementation with its own primary type and expose shared contracts through `bind<Interface>()`.

## ViewModel & MVI

ViewModels implement `MviViewModel<State : StateUi, Action : ActionUi, Event : EventUi>`.

- `StateUi` — immutable UI state exposed as a `StateFlow`.
- `ActionUi` — user intent sent from the UI layer via `onAction(action)`.
- `EventUi` — one-shot side-effect emitted from the ViewModel and observed via `CollectEventsUiEffect`.
- Keep `init` blocks minimal: do not place business or orchestration logic directly in `init` when it can be moved to a dedicated function.
- In `onAction`, if one `when` branch needs more than one call/operation, extract it into a dedicated private function and call only that function from `onAction`.

## Code Style

- Language: Kotlin, JVM/Java target 21 for Android; native for iOS.
- Indentation: 4 spaces, no tabs.
- Max line length: 140 characters (Detekt `MaxLineLength`).
- Packages: lowercase dot notation.
- `PascalCase` for classes/composables.
- `camelCase` for functions/properties.
- `UPPER_SNAKE_CASE` for constants.
- Before copying similar code, first evaluate whether it should be extracted into reusable shared code (within the owning feature or an appropriate `core` module).

Compose rules:
- Keep composables stable and predictable.
- Keep parameter ordering consistent.
- Add `modifier: Modifier = Modifier` where applicable.
- For all UI composables except `*Destination`, create `@Preview` with naming pattern `WidgetNamePreview`.

## UI Code Style & Principles

- Prefer small composable units; split large screens into meaningful UI blocks and files.
- Event handlers used by composables should be explicit and readable (named lambdas/functions where it improves clarity).
- Prefer Material 3 components and ripple-enabled interactions by default.
- Use application theme `PokedexTheme` for colors, typography, spacings.
- Access app typography through `PokedexTheme.typography` only; do not read `MaterialTheme.typography` directly in feature or shared UI code.
- Use `Inter` as the default UI font family with only `Regular`, `Medium`, `SemiBold`, and `Bold` font resources kept in the project.
- Reserve the Pokemon display font for the largest branded titles such as top app bar headings; use `Inter` for the rest of the UI text.
- Use plain text click handlers without heavy button chrome only for text-like actions.
- UI layer sends `ActionUi` to `ViewModel`; one-shot behaviors come back as `EventUi`.

[//]: # (- Use centralized controllers from `core:design` for cross-cutting UI &#40;`LocalDialogController`, `LocalBottomSheetController`, snackbar controller&#41; instead of ad-hoc global UI plumbing.)

### Compose UI blocks in `components` subpackage

- If a screen contains a logical UI block reused in multiple places or a block that makes the main screen hard to read, extract it into `composable/` and `composable/components` or if more screen in feature `screenExample/composable` and `screenExample/composable/component`.
- Place reusable block composables under `features/<feature>/impl/src/commonMain/kotlin/.../components/` near the owning feature screen.
- Name files and composables by UI role (`OutfitHeaderBlock`, `WardrobeEmptyState`, `GeneratedLooksGrid`) instead of generic names (`Item`, `Content`, `Block`).
- Keep `components` composables presentational: they accept data and callbacks, do not own navigation, do not create `ViewModel`, and do not access repositories/use cases directly.
- `components` composables should expose explicit parameters (`state`, `onAction`, `modifier`) and avoid passing whole screen state when only a subset is needed.
- Keep screen-level orchestration in `*Screen`/`*Destination`; `components` should only render UI and emit callbacks.
- If a block has private helper composables used only inside one component file, keep them `private` in that file.
- Keep not shared composables function in module with `internal` function modifier

## Build Commands

Use Gradle wrapper from repository root:

- `./gradlew :androidApp:assembleDebug` - build debug Android APK.
- `./gradlew :composeApp:iosDeployDebug` - build iOS debug (or use Xcode via `iosApp`).
- `./gradlew clean` - remove build outputs.

## Commit & Pull Request Guidelines

- Prefer short imperative commit messages; include ticket ID when available.
- Keep commits focused; avoid mixing refactor and feature behavior changes.
- PRs should include user-visible and technical summary.
- PRs should include linked issue/ticket.
- PRs should include screenshots/video for UI changes.
