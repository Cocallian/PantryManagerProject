package com.example.myapplication.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RecipeDao {

    // Adds a recipe and returns its id.
    @Insert
    long insert(Recipe recipe);

    // Adds a list of recipe ingredients.
    @Insert
    void insertIngredientsList(List<RecipeIngredient> ingredients);

    // Adds a list of recipe steps.
    @Insert
    void insertStepsList(List<RecipeStep> steps);

    // Gets all ingredients for a recipe.
    @Query("SELECT * FROM recipe_ingredient WHERE recipeId = :recipeId")
    List<RecipeIngredient> getIngredients(int recipeId);

    // Gets all steps for a recipe.
    @Query("SELECT * FROM recipe_step WHERE recipeId = :recipeId ORDER BY stepNumber")
    List<RecipeStep> getSteps(int recipeId);

    // Gets every recipe by name.
    @Query("SELECT * FROM recipe ORDER BY name")
    List<Recipe> getAll();

    // Gets one recipe by id.
    @Query("SELECT * FROM recipe WHERE id = :id")
    Recipe getById(int id);

    // Finds recipes matching a name.
    @Query("SELECT * FROM recipe WHERE name LIKE :name")
    List<Recipe> getByName(String name);

    // Counts all recipes.
    @Query("SELECT COUNT(*) FROM recipe")
    int count();
}
