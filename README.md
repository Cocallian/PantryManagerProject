# Smart Pantry Manager

An Android app, written in Java, that helps reduce food waste. The user keeps track of the ingredients they have at home (their pantry) and the app suggests recipes they can cook using **only** what they already have, so no shopping trip is needed.

Built for the Mobile App Development 700 practical assignment.

## Description

### Features

- **Pantry management:** add, edit and delete pantry items (name, quantity, unit and an optional expiry date). The form validates every field before saving.
- **Pantry list:** every ingredient shown in a RecyclerView with a custom adapter, read from the database. Items that expire within 7 days (or have already expired) are shown on a light red card.
- **Strict recipe matching:** a recipe is only suggested when **every** ingredient is in the pantry in at least the required quantity. A recipe with 4 of its 5 ingredients is not suggested. The matching copes with simple differences such as unit conversions (g/kg, ml/l, tsp/tbsp/cup).
- **Recipe tabs:** the recipe screen has three tabs:
  - *Ready to Make* lists recipes the pantry fully covers.
  - *Almost There* lists recipes missing exactly one ingredient, kept separate from the strict suggestions.
  - *All Recipes* lists every recipe and shows what is missing.
- **Empty state:** a clear message is shown when no recipes match the current pantry.
- **Recipe detail:** the full ingredient list and numbered method for a recipe.
- **Seeded data:** 37 recipes and 6 demo users, each with a starter pantry, are added on first run.
- **Settings:** light/dark mode, a switch for the expiry warning highlight, and log out.
- **Login:** each user has their own pantry.

### Screens

1. Login
2. Pantry list (Ingredients tab)
3. Add / Edit ingredient
4. Suggested recipes (Recipes tab)
5. Recipe detail
6. Settings

Navigation is through the bottom tab bar between the main screens. Add/Edit and Recipe Detail open full screen with a back button.

### Demo accounts

| Username | Password |
|----------|----------|
| admin    | admin    |
| alice    | alice123 |
| bob      | bob123   |
| charlie  | charlie123 |
| dana     | dana123  |
| eve      | eve123   |

## Database choice: SQLite via Room

The app stores its data in a local **SQLite** database accessed through the **Room** persistence library.

**Why Room/SQLite:**

- **Local and offline:** a pantry app needs to work at home with no signal. There is no server to build, host or pay for, unlike PostgreSQL, and no account or network dependency, unlike Firebase.
- **Relational data suits the problem:** users own pantry items, and recipes own ingredients and steps. These are one-to-many relationships, and Room models them with foreign keys.
- **Full CRUD and persistence:** pantry items can be created, read, updated and deleted, and the data is still there after the app is closed and reopened.
- **Compile-time safety:** Room checks the SQL in the DAO annotations when the app is built, so mistakes show up as build errors instead of runtime crashes.
- **Simple to run:** the marker only needs to build and run the app, with no extra services.

**Tables:** `users`, `pantry`, `recipe`, `recipe_ingredient`, `recipe_step`.

Database access happens on background threads. Starter data is inserted the first time the app runs.

