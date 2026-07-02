# SkillForge — Android Developer Take-Home Assignment

A minimal 3-screen Android learning app built with Kotlin and Jetpack Compose. Browse course categories, explore course details, and jump into lessons — all powered by a single REST endpoint.

---

## README · How I Used AI

### Which AI Tools I Used

- **Claude (Anthropic)** — Used for spec generation, architecture planning, and design documentation before writing any code.
- **Codex (OpenAI)** — Used for screen-specific code generation, implementing each composable based on the spec and project template.

---

### 2–3 Actual Prompts I Sent

**Prompt 1 — Generating the full spec doc (sent to Claude):**
> "https://android-assesment.github.io/notes/ — create a detailed markdown file that first has what we are building, then what the theme is, then what colors we are using (primary, secondary, and so on), the typography, then the first home screen details, second screen, and third screen details."

**Prompt 2 — Adding the architecture section (sent to Claude as a follow-up):**
> "Everything in here is good except there is nothing about the architecture. Add that section — do not create a full file, just add the section. Follow MVVM architecture, follow the ViewModel navigation pattern, screens will be stateless, create a UI state for every screen. Koin is already added for dependency injection in the template I created."

**Prompt 3 — Starting the actual code (sent to Codex):**
> "This is the spec.md file I have already created and the template is set up. Use this for context — add the colors to the Material Theme (all colors are given in the file), then build the Home screen following the rules and the architecture already set up in the project."

---

### One Thing AI Got Right — and One Thing It Got Wrong

**✅ Got right:** The layout structure for all three screens was accurate and well-organized from the first generation. The composable hierarchy, padding values, card structures, and the stateless screen pattern (passing lambdas instead of NavController) all matched the spec cleanly and required almost no correction.

**❌ Got wrong:** The `onCategoryClick` function broke after Codex generated the Home screen. It had silently changed the API response model to match what it assumed the click handler needed, which caused a mismatch with the data classes I had already defined in the template. I fixed it by undoing the model change with Ctrl+Z, reverting to my original data class, and then adjusting the `onCategoryClick` usage in the popular courses section to work correctly with the existing model structure.

---

## Tech Stack

- **Language:** Kotlin
- **UI:** Jetpack Compose
- **Architecture:** MVVM + Stateless Screens
- **Dependency Injection:** Koin
- **Networking:** Retrofit + Coroutines
- **Image Loading:** Coil
- **Navigation:** Jetpack Compose Navigation
- **Font:** Plus Jakarta Sans

---

## API

```
https://raw.githubusercontent.com/android-assesment/notes/refs/heads/main/data.json
```
