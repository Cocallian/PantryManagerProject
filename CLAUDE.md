# Project context

Android Studio project for the Mobile App Development 700 practical assignment: **Smart Pantry Manager**.
Full brief: `docs/assignment.txt` (text) and `docs/assignment.pdf` (original). Read it before design decisions.

## Key requirements (summary)
- **Java only (not Kotlin).** No Google Maps, mapping SDKs, or GPS/location features.
- Concept: user tracks pantry ingredients; app suggests recipes using **strictly** what they have.
- **Strict-matching rule (most important logic):** a recipe is suggested only if EVERY ingredient is in the pantry in at least the required quantity. 4 of 5 ingredients = excluded. Must be robust to simple messiness (unit differences, singular/plural like "tomato" vs "tomatoes"). Optional bonus: separate "Almost There" list (missing exactly 1 ingredient), clearly separated.
- Screens (min 4 + settings): Pantry List, Add/Edit Ingredient, Suggested Recipes, Recipe Detail, Settings.
- Pantry items: name, quantity, unit, optional expiry date; add/edit/delete.
- 15-20+ recipes seeded in DB on first run (name, ingredients, steps).
- Empty state message when no recipes match.
- RecyclerView/ListView with custom Adapter bound to DB; Intents to navigate/pass data; navigation element (bottom nav/toolbar/drawer); input validation on forms; sensible layouts.
- Database: ONE of SQLite (SQLiteOpenHelper/Room), Firebase, or PostgreSQL. Full CRUD, data must persist across app restarts.
- Marking: functionality 20, database 15, code quality 25 (clean, commented Java), UI/UX 10, GitHub 10, video 10, report 10.

## Deliverables / process
- GitHub public repo, 10+ meaningful incremental commits with descriptive messages, README.md (app name/description, DB choice and why, setup/run instructions).
- 5-7 min narrated video, written report, all in one ZIP (<50MB): `Studentnumber_Surname_MobileAppDev700_Assignment.zip`.
- Code should be commented and explainable by the student (they must defend it).
