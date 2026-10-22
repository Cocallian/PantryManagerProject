package com.example.myapplication.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "recipe_step")
public class RecipeStep {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public int recipeId;
    public int stepNumber;
    public String text;

    // Turns text into numbered recipe steps.
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
