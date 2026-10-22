package com.example.myapplication.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "recipe")
public class Recipe {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;

    // Adds the starter recipes once.
    public static void seedIfEmpty(RecipeDao dao) {
        if (dao.count() > 0) {
            return;
        }

        add(dao, "Scrambled Eggs",
                new String[]{"egg:3:pcs", "butter:10:g", "salt:1:g"},
                new String[]{"Beat the eggs with the salt.", "Melt the butter in a pan.",
                        "Pour in the eggs and stir gently until just set."});

        add(dao, "Plain Omelette",
                new String[]{"egg:2:pcs", "butter:10:g", "salt:1:g"},
                new String[]{"Beat the eggs with the salt.", "Melt the butter in a pan.",
                        "Pour in the eggs, cook until set, then fold in half."});

        add(dao, "Cheese Omelette",
                new String[]{"egg:2:pcs", "cheese:50:g", "butter:10:g"},
                new String[]{"Beat the eggs.", "Melt the butter in a pan and add the eggs.",
                        "Sprinkle on the cheese, then fold and serve once melted."});

        add(dao, "Tomato Pasta",
                new String[]{"pasta:200:g", "tomato:4:pcs", "olive oil:15:ml", "garlic:2:cloves", "salt:2:g"},
                new String[]{"Boil the pasta in salted water until tender.",
                        "Fry the garlic in the oil, add chopped tomatoes and simmer.",
                        "Drain the pasta and toss through the sauce."});

        add(dao, "Garlic Butter Pasta",
                new String[]{"pasta:200:g", "butter:30:g", "garlic:3:cloves", "salt:2:g"},
                new String[]{"Boil the pasta until tender and drain.",
                        "Melt the butter and gently fry the garlic.",
                        "Toss the pasta in the garlic butter."});

        add(dao, "Cheese Toastie",
                new String[]{"bread:2:slices", "cheese:40:g", "butter:10:g"},
                new String[]{"Butter one side of each slice of bread.",
                        "Put the cheese between the unbuttered sides.",
                        "Fry on both sides until golden and the cheese melts."});

        add(dao, "French Toast",
                new String[]{"bread:4:slices", "egg:2:pcs", "milk:100:ml", "butter:15:g"},
                new String[]{"Whisk the eggs and milk together.", "Soak each slice of bread in the mix.",
                        "Fry in butter until golden on both sides."});

        add(dao, "Pancakes",
                new String[]{"flour:150:g", "egg:1:pcs", "milk:250:ml", "butter:10:g", "sugar:20:g"},
                new String[]{"Whisk the flour, sugar, egg and milk into a smooth batter.",
                        "Melt a little butter in a hot pan.",
                        "Pour in a ladle of batter and cook until golden on both sides."});

        add(dao, "Fried Rice",
                new String[]{"rice:200:g", "egg:2:pcs", "onion:1:pcs", "soy sauce:15:ml", "olive oil:15:ml"},
                new String[]{"Cook the rice and let it cool.", "Fry the chopped onion in the oil.",
                        "Add the rice and soy sauce, push aside and scramble in the eggs, then mix."});

        add(dao, "Tomato Soup",
                new String[]{"tomato:6:pcs", "onion:1:pcs", "garlic:2:cloves", "water:500:ml", "salt:3:g"},
                new String[]{"Fry the chopped onion and garlic until soft.",
                        "Add the chopped tomatoes, water and salt and simmer for 20 minutes.",
                        "Blend until smooth."});

        add(dao, "Mashed Potatoes",
                new String[]{"potato:500:g", "butter:40:g", "milk:60:ml", "salt:2:g"},
                new String[]{"Peel and boil the potatoes until soft.", "Drain and mash.",
                        "Stir in the butter, milk and salt."});

        add(dao, "Roast Potatoes",
                new String[]{"potato:600:g", "olive oil:30:ml", "salt:3:g"},
                new String[]{"Heat the oven to 200C.", "Chop the potatoes and toss with the oil and salt.",
                        "Roast for 40 minutes, turning once."});

        add(dao, "Egg Fried Rice",
                new String[]{"rice:200:g", "egg:2:pcs", "soy sauce:15:ml"},
                new String[]{"Cook the rice and let it cool.", "Scramble the eggs in a hot pan.",
                        "Add the rice and soy sauce and stir-fry for 3 minutes."});

        add(dao, "Garlic Bread",
                new String[]{"bread:4:slices", "butter:40:g", "garlic:2:cloves"},
                new String[]{"Mash the crushed garlic into the soft butter.", "Spread it on the bread.",
                        "Bake at 200C for 8 minutes until crisp."});

        add(dao, "Cheesy Mashed Potato",
                new String[]{"potato:500:g", "cheese:60:g", "butter:30:g", "milk:50:ml"},
                new String[]{"Boil and drain the potatoes.", "Mash with the butter and milk.",
                        "Stir through the grated cheese until melted."});

        add(dao, "Tomato Omelette",
                new String[]{"egg:3:pcs", "tomato:1:pcs", "butter:10:g", "salt:1:g"},
                new String[]{"Beat the eggs with the salt and dice the tomato.",
                        "Melt the butter in a pan and add the eggs.",
                        "Scatter the tomato on top, then fold and serve."});

        add(dao, "Buttered Rice",
                new String[]{"rice:200:g", "butter:20:g", "salt:2:g"},
                new String[]{"Cook the rice in salted water.", "Drain and stir through the butter."});

        add(dao, "Onion Soup",
                new String[]{"onion:4:pcs", "butter:30:g", "water:700:ml", "salt:3:g", "bread:2:slices"},
                new String[]{"Slowly fry the sliced onions in the butter until deep golden.",
                        "Add the water and salt and simmer for 20 minutes.",
                        "Serve with toasted bread."});

        add(dao, "Milk Rice Pudding",
                new String[]{"rice:100:g", "milk:500:ml", "sugar:40:g"},
                new String[]{"Put the rice, milk and sugar in a pan.",
                        "Simmer gently, stirring often, for 30 minutes until thick and creamy."});

        add(dao, "Cheese and Tomato Pasta",
                new String[]{"pasta:200:g", "tomato:3:pcs", "cheese:60:g", "olive oil:15:ml"},
                new String[]{"Boil the pasta and drain.", "Fry the chopped tomatoes in the oil for 5 minutes.",
                        "Toss with the pasta and top with the grated cheese."});
    }

    // Saves a recipe with its ingredients and steps.
    private static void add(RecipeDao dao, String name, String[] ingredientSpecs, String[] steps) {
        Recipe recipe = new Recipe();
        recipe.name = name;
        int recipeId = (int) dao.insert(recipe);

        dao.insertIngredientsList(RecipeIngredient.seedFor(recipeId, ingredientSpecs));
        dao.insertStepsList(RecipeStep.seedFor(recipeId, steps));
    }

}
