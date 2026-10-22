package com.example.myapplication.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

/** One cooking step of a recipe. stepNumber gives the order. Linked to Recipe by recipeId. */
@Entity(tableName = "recipe_step")
public class RecipeStep {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public int recipeId;
    public int stepNumber;
    public String text;

    // ---------------------------------------------------------------------------------
    // Seed helper
    // ---------------------------------------------------------------------------------

    /**
     * Seed helper: builds the numbered step rows for one recipe.
     *
     * @param recipeId id returned when the parent recipe was inserted
     * @param texts    the steps, in order
     */
    public static List<RecipeStep> seedFor(int recipeId, String... texts) {
        List<RecipeStep> list = new ArrayList<>();
        for (int i = 0; i < texts.length; i++) {
            RecipeStep step = new RecipeStep();
            step.recipeId = recipeId;
            step.stepNumber = i + 1;
            step.text = texts[i];
            list.add(step);
        }
        return list;
    }
}
