# Skillforge App — Full Design & Screen Specification

---

## What We Are Building

**Skillforge** is a minimal 3-screen Android learning app built in **Kotlin + Jetpack Compose**.

It lets users browse course categories, tap into a course to see its lessons and instructor, then open a lesson to watch a video. There is a single REST endpoint that returns all the data — categories, courses, instructors, and lessons — nested in one JSON response. The app parses and renders that data across the three screens.

**Navigation flow:**

```
Home Screen  →  Course Detail Screen  →  Lesson Player Screen
```

**Tech stack:**
- Language: Kotlin
- UI: Jetpack Compose
- Networking: Retrofit + Coroutines
- Image loading: Coil or Glide
- Font: Plus Jakarta Sans (Google Fonts)

**API endpoint:**
```
https://raw.githubusercontent.com/android-assesment/notes/refs/heads/main/data.json
```

**Data shape:**
```
categories → courses → lessons
```

---

## Architecture

The app follows **MVVM (Model-View-ViewModel)** with a clean unidirectional data flow. All screens are **stateless composables** — they only receive state and emit events upward, never holding or mutating state themselves.

---

### Layer Overview

```
UI Layer (Composables)
       ↕  UiState / Events
ViewModel Layer
       ↕  suspend functions / Flow
Repository Layer
       ↕  Retrofit calls
Remote Data Source (API)
```

---

### Dependency Injection — Koin

Koin is already added to the project template. Wire up modules like this:

```
appModule
  ├── single { ApiService }          ← Retrofit instance
  ├── single { CourseRepository }    ← takes ApiService
  ├── viewModel { HomeViewModel }    ← takes CourseRepository
  ├── viewModel { DetailViewModel }  ← takes CourseRepository
  └── viewModel { PlayerViewModel }  ← takes CourseRepository
```

Inject ViewModels in composables using `koinViewModel()`, never `viewModel()` from the Compose ViewModel library directly.

---

### Navigation

Use **Jetpack Compose Navigation** (`NavHost` + `NavController`).

```
NavHost
  ├── home              → HomeScreen
  ├── detail/{courseId} → CourseDetailScreen
  └── player/{lessonId} → LessonPlayerScreen
```

- `NavController` lives at the app root (in `MainActivity` or a `AppNavGraph` composable), not inside any screen
- Screens receive a **lambda** for navigation (`onCourseClick: (courseId: String) -> Unit`), never the `NavController` directly
- Route arguments are passed as strings in the nav route; the ViewModel fetches the full data using that ID

---

### UI State

Each screen has its own sealed `UiState` defined alongside its ViewModel. Pattern is the same across all three screens:

```kotlin
sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(val data: HomeData) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}
```

The ViewModel exposes state as `StateFlow<ScreenUiState>`, collected in the composable via `collectAsStateWithLifecycle()`.

- **HomeUiState** holds: category list + popular courses list
- **DetailUiState** holds: selected course (with instructor + lessons)
- **PlayerUiState** holds: current lesson + full lesson list for the course + active tab index

---

### Screen Contract (Stateless Pattern)

Every screen composable follows this signature pattern — no ViewModel reference inside the composable itself:

we will use function overloading so we make a function as same as a screen in which we call only the viewmodel 
that function is used in navigation then inside this function we collect the ui state and all the values and funtion 
needed by the screen and passed it on to the actual screen

```kotlin
@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onCourseClick: (courseId: String) -> Unit,
    onCategoryClick: (categoryId: String) -> Unit,
    onRetry: () -> Unit
)
```

The screen's **route-level composable** (the one registered in `NavHost`) is the only place that calls `koinViewModel()` and passes state + lambdas down:
we will have a serializabe object extending the **AppRoute** given then we that oject to navigate to different scren

```kotlin
// In NavHost
composable<HomeScreenRoute> {
  val viewModel: HomeScreenViewModel = appViewModel(,
    navController = appNavController
  )
  HomeScreen(viewModel = viewModel)
}
```

---

## Theme

The app uses a **Light theme** only — no dark mode required.

The overall mood is clean, modern, and educational. Think cream/off-white backgrounds with teal as the primary action/accent color. The design is airy, well-spaced, and uses card-based layouts with rounded corners. No harsh blacks — text uses dark charcoal tones instead.

---

## Color Palette

| Role | Name | Hex |
|---|---|---|
| **Primary** | Teal | `#2DD4BF` |
| **Primary Variant** | Deep Teal | `#0F9B8E` |
| **Secondary** | Emerald Green | `#34D399` |
| **Accent / Highlight** | Amber (Design category) | `#FBBF24` |
| **Background** | Cream / Off-White | `#FAFAF9` |
| **Surface** | White | `#FFFFFF` |
| **On Primary** | White | `#FFFFFF` |
| **On Background** | Charcoal | `#1C1C1E` |
| **On Surface Secondary** | Medium Grey | `#6B7280` |
| **Divider / Border** | Light Grey | `#E5E7EB` |
| **FREE badge background** | Light Teal | `#CCFBF1` |
| **FREE badge text** | Teal | `#0F766E` |
| **PRICE badge background** | Light Amber | `#FEF3C7` |
| **PRICE badge text** | Amber Dark | `#92400E` |
| **Level chip — Beginner** | Light Green | `#D1FAE5` / text `#065F46` |
| **Level chip — Intermediate** | Light Blue | `#DBEAFE` / text `#1E40AF` |

**Category icon colors (from API):**
- Android Development: `#2DD4BF`
- Backend & APIs: `#34D399`
- Product & UI Design: `#FBBF24`

---

## Typography

**Font Family:** `Plus Jakarta Sans` (import from Google Fonts)

| Style | Weight | Size | Usage |
|---|---|---|---|
| Display / Hero | ExtraBold (800) | 28sp | "Find your next skill" headline |
| Title Large | Bold (700) | 22sp | Course title on Detail screen |
| Title Medium | SemiBold (600) | 18sp | Section headers ("Categories", "Popular courses") |
| Title Small | SemiBold (600) | 15sp | Course card title |
| Body Large | Regular (400) | 16sp | Course description paragraph |
| Body Medium | Regular (400) | 14sp | Instructor bio, lesson content text |
| Label Large | Medium (500) | 13sp | Rating, duration, lesson duration chips |
| Label Small | Medium (500) | 11sp | Level badge (BEGINNER / INTERMEDIATE) |
| Caption | Regular (400) | 12sp | "See all", timestamps, sub-labels |

---

## Screen 1 — Home

### Purpose
The entry screen. Shows a greeting, a search bar, a horizontal scrollable row of categories, and a vertical list of popular courses.

---

### Layout Structure (top to bottom)

#### 1. Top Greeting Section
- Background: Teal (`#2DD4BF`) — fills a rounded-bottom card or a simple colored header block
- Text line 1: `"Welcome back"` — Caption style, white, slightly transparent
- Text line 2: `"Find your next skill"` — Display/Hero style, white, bold
- Padding: `24dp` horizontal, `32dp` top, `24dp` bottom

#### 2. Search Bar
- Full-width, sits just below the hero header (can overlap slightly with a white card)
- Placeholder text: `"Search courses, topics…"`
- Background: White (`#FFFFFF`)
- Border radius: `12dp`
- Leading icon: search icon (teal tint)
- Elevation: subtle shadow
- Margin: `16dp` horizontal

#### 3. Categories Section
- Section header row:
  - Left: `"Categories"` — Title Medium, charcoal
  - Right: `"See all"` — Caption, teal, tappable
- Below: Horizontal `LazyRow` of category chips/cards

**Each Category Card:**
- Width: ~`140dp`, Height: ~`80dp`
- Background: White with teal-tinted left border or colored icon area
- Icon/color dot: uses the category's `iconColor` from API
- Category name: `"Android Development"` — Body Medium, bold, charcoal
- Course count: `"2 courses"` — Caption, grey
- Corner radius: `12dp`
- Spacing between cards: `12dp`

**Data (3 categories):**
1. Android Development — 2 courses — icon color `#2DD4BF`
2. Backend & APIs — 2 courses — icon color `#34D399`
3. Product & UI Design — 2 courses — icon color `#FBBF24`

#### 4. Popular Courses Section
- Section header row:
  - Left: `"Popular courses"` — Title Medium, charcoal
  - Right: `"See all"` — Caption, teal, tappable
- Below: Vertical `LazyColumn` list of course cards (no scroll conflict with screen scroll — use a `Column` inside a scrollable parent or `nestedScroll`)

**Each Course Card:**
- Full width, horizontal padding `16dp`
- Background: White, corner radius `14dp`, subtle shadow
- Layout inside card:
  - **Thumbnail image** — Full width, height ~`160dp`, corner radius `14dp` top, loaded via Coil/Glide from `thumbnailUrl`
  - **Level badge** — overlaid top-left on image: `"BEGINNER"` or `"INTERMEDIATE"` — Label Small, colored chip
  - **Course title** — Title Small, charcoal, `12dp` top padding inside card body
  - **Instructor name** — Caption, grey, below title
  - **Bottom row** — Rating (star icon + number) | Duration (clock icon + hours) — Label Large, grey
- Spacing between cards: `16dp`

**Courses shown (from API popular list — first course of each category):**
1. Kotlin Fundamentals — Aarav Sharma — ⭐ 4.7 — 6.5h — BEGINNER
2. Jetpack Compose Essentials — Meera Nair — ⭐ 4.8 — 9h — INTERMEDIATE
3. Node.js from Scratch — Sara Khan — ⭐ 4.5 — 7.5h — BEGINNER

#### 5. Loading & Error States
- **Loading:** Centered `CircularProgressIndicator` in teal
- **Error:** Centered text message e.g. `"Something went wrong. Please try again."` + retry button

---

## Screen 2 — Course Detail

### Purpose
Shows the full info for a selected course: hero image, course metadata, instructor card, lesson list, and an enroll CTA at the bottom.

---

### Layout Structure (top to bottom)

#### 1. Top App Bar
- Back arrow (left) — navigates back to Home
- Transparent or white background
- No title in the app bar itself (title is in the content)

#### 2. Hero Image
- Full-width image, height ~`220dp`
- Loaded from course's `thumbnailUrl` via Coil/Glide
- Tags overlaid bottom-left (e.g., pill chips for `"Kotlin"`, `"Basics"`, `"JVM"`)

#### 3. Course Info Block
- Course tags as small pills (from `tags` array)
- Course title: `"Kotlin Fundamentals"` — Title Large, charcoal, bold
- Course subtitle: `"Everything you need to start writing Kotlin"` — Body Medium, grey
- Metadata row (icons + text, horizontal):
  - ⭐ `4.7` (rating)
  - 👥 `18,420` (students enrolled)
  - 🕐 `6.5h` (duration)
  - 📶 `Beginner` (level)
- All metadata uses Label Large, grey, spaced with `·` or icon separators

#### 4. Instructor Card
- White card, corner radius `12dp`, padding `16dp`
- Layout: avatar (circle, 48dp) | Name + Title | `Follow` button (outlined, teal)
- Avatar loaded from `avatarUrl`
- Name: `"Aarav Sharma"` — Title Small, charcoal
- Title: `"Senior Android Engineer"` — Caption, grey
- Below the row: instructor bio text — Body Medium, grey, 2-line collapsed or full

#### 5. Course Description
- Heading: `"About this course"` (optional) or directly the description text
- Body Large, charcoal, full paragraph
- Example: `"Start from zero and learn Kotlin's syntax, null safety, collections, and functions…"`

#### 6. Course Content Section
- Header: `"Course content"` — Title Medium, charcoal
- Sub-label: `"3 lessons · 41 min"` — Caption, grey (sum of all lesson durations)
- List of lesson rows (not cards — simple rows with divider)

**Each Lesson Row:**
- Lesson number or play icon (left)
- Lesson title — Body Medium, charcoal
- Duration — Caption, grey — right-aligned
- FREE / PRICE badge — right side:
  - FREE: small teal chip
  - Locked: small amber/orange chip labeled `"PRICE"` or a lock icon

**Lessons for Kotlin Fundamentals:**
| # | Title | Duration | Access |
|---|---|---|---|
| 1 | Welcome & Setup | 8 min | FREE |
| 2 | Variables & Null Safety | 15 min | FREE |
| 3 | Functions & Lambdas | 18 min | PRICE (locked) |

#### 7. Bottom CTA Bar
- Fixed to bottom of screen
- Left: `"Free"` — Title Small, charcoal (price label)
- Right: `"Enroll now"` button — filled teal, white text, corner radius `12dp`, full or half width

#### 8. Loading & Error States
- Same pattern as Home screen — centered spinner or error message

---

## Screen 3 — Lesson Player

### Purpose
Full video player experience for a single lesson. Has a player area at the top and a lesson list + tab bar below.

---

### Layout Structure (top to bottom)

#### 1. Video Player Header
- Black/dark background area, aspect ratio ~`16:9`
- Center: Play/pause icon overlay
- Bottom bar inside player:
  - Current timestamp: `"02:14"` — white
  - Progress scrubber bar — teal fill
  - Total duration: `"06:00"` — white
- Top-left: back arrow (white, on dark background)

#### 2. Lesson Identity Block
- Below the player, white background
- Label: `"LESSON 1 · KOTLIN FUNDAMENTALS"` — Label Small, teal, uppercase, letter-spaced
- Lesson title: `"Welcome & Setup"` — Title Medium, charcoal, bold
- Description: `"Set up Android Studio and run your first Kotlin file."` — Body Medium, grey

#### 3. Tab Bar
- 3 tabs: **Lessons** | **Notes** | **Resources**
- Active tab: teal underline indicator, teal text
- Inactive tab: grey text
- Default active tab: **Lessons**

#### 4. Lessons Tab Content
- Vertical list of all lessons in the course
- Each row:
  - Lesson title — Body Medium, charcoal
  - Duration — Caption, grey
  - Status badge:
    - Currently playing: `"Now playing · 8 min"` — teal text, highlighted row background (light teal tint `#F0FDFA`)
    - Free unlocked: `"FREE"` badge
    - Locked: lock icon or `"PRICE"` badge

**Lessons list (Kotlin Fundamentals):**
| Title | Duration | Status |
|---|---|---|
| Welcome & Setup | 8 min | Now playing |
| Variables & Null Safety | 15 min | FREE |
| Functions & Lambdas | 18 min | Locked |

#### 5. Notes Tab Content (basic)
- Simple `TextField` or static placeholder: `"Your notes for this lesson…"`
- Not wired to any API — local state only

#### 6. Resources Tab Content (basic)
- Static or empty state: `"No resources for this lesson."`

#### 7. Loading & Error States
- Player area shows a dark placeholder with a spinner if video URL is loading
- Error state shows `"Unable to load lesson"` with a retry option

---

## API Data Summary

**Base URL:**
```
https://raw.githubusercontent.com/android-assesment/notes/refs/heads/main/data.json
```

**Full structure:**
```
categories[]
  ├── id, name, description, iconColor, courseCount
  └── courses[]
        ├── id, title, subtitle, thumbnailUrl
        ├── level, durationHours, rating, studentsEnrolled
        ├── language, lastUpdated, tags[]
        ├── instructor { id, name, title, avatarUrl, bio }
        ├── description
        └── lessons[]
              ├── id, title, durationMinutes, isFree
              ├── videoUrl
              └── content
```

---

## States to Handle (All Screens)

| State | UI |
|---|---|
| Loading | `CircularProgressIndicator` centered, teal color |
| Success | Render the screen content |
| Error | Short error message + "Retry" text button |

---

## Checklist

- [ ] Fetch JSON from API on app launch
- [ ] Home: category row + popular courses list
- [ ] Course Detail: hero, metadata, instructor card, lessons list, enroll CTA
- [ ] Lesson Player: player header, lesson identity, tab bar, lesson list
- [ ] Coil/Glide for all image URLs
- [ ] Loading + error states on all screens
- [ ] At least one unit test
- [ ] Plus Jakarta Sans font applied globally
- [ ] README with AI usage notes
- [ ] Debug APK exported
- [ ] Pushed to public GitHub repo