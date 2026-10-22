package com.example.myapplication.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(
        entities = {PantryItem.class, Recipe.class, RecipeIngredient.class, RecipeStep.class, Users.class},
        version = 3,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    // Gives access to pantry table queries.
    public abstract PantryDao pantryDao();
    // Gives access to recipe table queries.
    public abstract RecipeDao recipeDao();
    // Gives access to user table queries.
    public abstract UsersDao usersDao();

    private static volatile AppDatabase instance;
    // Returns the one shared database.
    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "pantry_db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return instance;
    }
}
