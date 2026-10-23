package com.example.myapplication;

import com.example.myapplication.data.PantryItem;
import com.example.myapplication.data.Recipe;
import com.example.myapplication.data.RecipeDao;
import com.example.myapplication.data.RecipeIngredient;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RecipeMatcher {

    public static class Result {
        public final Recipe recipe;
        public final List<String> missing;

        // Pairs a recipe with the ingredients the pantry is missing.
        public Result(Recipe recipe, List<String> missing) {
            this.recipe = recipe;
            this.missing = missing;
        }
    }

    // Checks every recipe against the pantry and records what is missing.
    public static List<Result> matchAll(List<PantryItem> pantry, RecipeDao dao) {
        List<Result> results = new ArrayList<>();
        for (Recipe recipe : dao.getAll()) {
            List<String> missing = new ArrayList<>();
            for (RecipeIngredient ingredient : dao.getIngredients(recipe.id)) {
                if (!hasIngredient(pantry, ingredient)) {
                    missing.add(ingredient.name);
                }
            }
            results.add(new Result(recipe, missing));
        }
        return results;
    }

    // Keeps only recipes missing exactly this many ingredients.
    public static List<Result> filterByMissing(List<Result> all, int missingCount) {
        List<Result> out = new ArrayList<>();
        for (Result result : all) {
            if (result.missing.size() == missingCount) {
                out.add(result);
            }
        }
        return out;
    }

    // Checks the pantry holds at least the required amount of an ingredient.
    private static boolean hasIngredient(List<PantryItem> pantry, RecipeIngredient ingredient) {
        String search = ingredient.name.trim().toLowerCase(Locale.ROOT);
        String family = unitFamily(ingredient.unit);
        double needed = ingredient.quantity * unitFactor(ingredient.unit);
        double have = 0;

        for (PantryItem item : pantry) {
            String pantryName = item.name.trim().toLowerCase(Locale.ROOT);
            if (pantryName.contains(search) && unitFamily(item.unit).equals(family)) {
                have += item.quantity * unitFactor(item.unit);
            }
        }
        return have >= needed - 0.0001;
    }

    // Groups units that can be converted, such as g and kg.
    private static String unitFamily(String unit) {
        switch (unit.trim().toLowerCase(Locale.ROOT)) {
            case "g":
            case "kg":
                return "mass";
            case "ml":
            case "l":
            case "tsp":
            case "tbsp":
            case "cup":
                return "volume";
            default:
                return unit.trim().toLowerCase(Locale.ROOT);
        }
    }

    // Gives the multiplier that converts a unit to its base unit.
    private static double unitFactor(String unit) {
        switch (unit.trim().toLowerCase(Locale.ROOT)) {
            case "kg":
            case "l":
                return 1000;
            case "tsp":
                return 5;
            case "tbsp":
                return 15;
            case "cup":
                return 240;
            default:
                return 1;
        }
    }
}
