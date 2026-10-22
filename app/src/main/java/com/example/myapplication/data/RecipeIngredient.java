package com.example.myapplication.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

/** One ingredient line of a recipe, e.g. "200 g flour". Linked to Recipe by recipeId. */
@Entity(tableName = "recipe_ingredient")
public class RecipeIngredient {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public int recipeId;
    public String name;
    public double quantity;
    public String unit;

    // ---------------------------------------------------------------------------------
    // Seed helper
    // ---------------------------------------------------------------------------------

    /**
     * Seed helper: builds the ingredient rows for one recipe from short text specs.
     * Each spec has the form "name:quantity:unit", e.g. "flour:200:g" or "egg:2:pcs".
     *
     * @param recipeId id returned when the parent recipe was inserted
     * @param specs    one spec string per ingredient
     */
    public static List<RecipeIngredient> seedFor(int recipeId, String... specs) {
        List<RecipeIngredient> list = new ArrayList<>();
        for (String spec : specs) {
            String[] parts = spec.split(":");
            RecipeIngredient ingredient = new RecipeIngredient();
            ingredient.recipeId = recipeId;
            ingredient.name = parts[0];
            ingredient.quantity = Double.parseDouble(parts[1]);
            ingredient.unit = parts[2];
            list.add(ingredient);
        }
        return list;
    }
}
