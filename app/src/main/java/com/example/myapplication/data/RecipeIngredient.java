package com.example.myapplication.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "recipe_ingredient")
public class RecipeIngredient {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public int recipeId;
    public String name;
    public double quantity;
    public String unit;

    // Turns text specs into ingredient objects.
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
