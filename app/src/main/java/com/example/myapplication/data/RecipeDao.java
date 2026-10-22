package com.example.myapplication.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/** Database operations for recipes, their ingredients and their steps. */
@Dao
public interface RecipeDao {

    /** Inserts a recipe and returns its new id, so ingredients/steps can be linked to it. */
    @Insert
    long insert(Recipe recipe);

    @Insert
    void insertIngredientsList(List<RecipeIngredient> ingredients);

    @Insert
    void insertStepsList(List<RecipeStep> steps);

    @Query("SELECT * FROM recipe_ingredient WHERE recipeId = :recipeId")
    List<RecipeIngredient> getIngredients(int recipeId);

    @Query("SELECT * FROM recipe_step WHERE recipeId = :recipeId ORDER BY stepNumber")
    List<RecipeStep> getSteps(int recipeId);

    @Query("SELECT * FROM recipe ORDER BY name")
    List<Recipe> getAll();

    @Query("SELECT * FROM recipe WHERE id = :id")
    Recipe getById(int id);

    @Query("SELECT * FROM recipe WHERE name LIKE :name")
    List<Recipe> getByName(String name);

    @Query("SELECT COUNT(*) FROM recipe")
    int count();
}
