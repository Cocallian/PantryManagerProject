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

        add(dao, "Chicken Rice Bowl",
                new String[]{"chicken:200:g", "rice:200:g", "soy sauce:15:ml", "onion:1:pcs"},
                new String[]{"Cook the rice.", "Fry the diced chicken and sliced onion until the chicken is cooked through.",
                        "Add the soy sauce, then serve over the rice."});

        add(dao, "Mushroom Omelette",
                new String[]{"egg:3:pcs", "mushroom:100:g", "butter:10:g", "salt:1:g"},
                new String[]{"Fry the sliced mushrooms in half the butter until golden.",
                        "Beat the eggs with the salt and cook them in the rest of the butter.",
                        "Add the mushrooms, fold and serve."});

        add(dao, "Spinach Omelette",
                new String[]{"egg:2:pcs", "spinach:50:g", "cheese:30:g", "butter:10:g"},
                new String[]{"Wilt the spinach in the butter.", "Pour in the beaten eggs and cook until nearly set.",
                        "Scatter over the cheese, fold and serve."});

        add(dao, "Banana Pancakes",
                new String[]{"banana:2:pcs", "egg:1:pcs", "flour:100:g", "milk:100:ml"},
                new String[]{"Mash the bananas and whisk in the egg, flour and milk.",
                        "Cook ladles of batter in a hot pan until golden on both sides."});

        add(dao, "Creamy Mushroom Pasta",
                new String[]{"pasta:200:g", "mushroom:150:g", "cream:100:ml", "garlic:2:cloves", "parmesan:30:g"},
                new String[]{"Boil the pasta and drain.", "Fry the mushrooms and garlic until golden.",
                        "Add the cream and parmesan, simmer briefly and toss through the pasta."});

        add(dao, "Overnight Oats",
                new String[]{"oats:80:g", "milk:150:ml", "honey:15:g", "banana:1:pcs"},
                new String[]{"Stir the oats, milk and honey together in a jar.", "Chill overnight.",
                        "Top with sliced banana before eating."});

        add(dao, "Tuna Pasta",
                new String[]{"pasta:200:g", "tuna:160:g", "olive oil:15:ml", "garlic:2:cloves", "lemon:1:pcs"},
                new String[]{"Boil the pasta and drain.", "Warm the garlic in the oil, then add the flaked tuna.",
                        "Toss with the pasta and a squeeze of lemon."});

        add(dao, "Cucumber Yogurt Salad",
                new String[]{"cucumber:1:pcs", "yogurt:150:g", "garlic:1:cloves", "olive oil:10:ml", "salt:1:g"},
                new String[]{"Dice the cucumber.", "Mix the yogurt, crushed garlic, oil and salt.",
                        "Fold in the cucumber and chill before serving."});

        add(dao, "Bean and Carrot Soup",
                new String[]{"beans:300:g", "onion:1:pcs", "carrot:2:pcs", "garlic:2:cloves", "water:500:ml", "salt:3:g"},
                new String[]{"Fry the chopped onion, carrot and garlic until soft.",
                        "Add the beans, water and salt and simmer for 25 minutes.", "Mash lightly and serve."});

        add(dao, "Shakshuka",
                new String[]{"egg:4:pcs", "onion:1:pcs", "tomato:4:pcs", "cumin:3:g", "salt:2:g"},
                new String[]{"Fry the chopped onion until soft and stir in the cumin.",
                        "Add the chopped tomatoes and salt and simmer for 10 minutes.",
                        "Make wells, crack in the eggs and cover until just set."});

        add(dao, "Peanut Butter Banana Toast",
                new String[]{"bread:2:slices", "peanut butter:30:g", "banana:1:pcs"},
                new String[]{"Toast the bread.", "Spread with peanut butter.", "Top with sliced banana."});

        add(dao, "Beef Tacos",
                new String[]{"beef mince:300:g", "taco shells:6:pcs", "avocado:1:pcs", "salsa:100:g", "cheese:50:g"},
                new String[]{"Brown the beef mince in a pan.", "Warm the taco shells.",
                        "Fill with beef, sliced avocado, salsa and grated cheese."});

        add(dao, "Salmon Sushi Rolls",
                new String[]{"sushi rice:200:g", "nori:4:sheets", "salmon:150:g", "rice vinegar:30:ml", "cucumber:1:pcs"},
                new String[]{"Cook the sushi rice and season with the rice vinegar.",
                        "Spread rice over each nori sheet and add strips of salmon and cucumber.",
                        "Roll tightly and slice."});

        add(dao, "Chicken Curry",
                new String[]{"chicken:400:g", "curry paste:40:g", "coconut milk:400:ml", "onion:1:pcs"},
                new String[]{"Fry the sliced onion and curry paste for 2 minutes.", "Add the diced chicken and brown it.",
                        "Pour in the coconut milk and simmer for 20 minutes."});

        add(dao, "Guacamole",
                new String[]{"avocado:2:pcs", "lime:1:pcs", "coriander:10:g", "salt:1:g"},
                new String[]{"Mash the avocados.", "Stir in the lime juice, chopped coriander and salt."});

        add(dao, "Red Lentil Dahl",
                new String[]{"red lentils:200:g", "coconut milk:200:ml", "onion:1:pcs", "garlic:2:cloves", "turmeric:3:g"},
                new String[]{"Fry the onion and garlic with the turmeric.",
                        "Add the lentils, coconut milk and 300ml water.", "Simmer for 20 minutes until thick."});

        add(dao, "Beef Stir Fry",
                new String[]{"beef strips:250:g", "broccoli:150:g", "soy sauce:15:ml", "garlic:2:cloves", "ginger:10:g"},
                new String[]{"Stir-fry the beef in a hot pan until browned.",
                        "Add the broccoli, garlic and grated ginger and cook for 4 minutes.", "Finish with the soy sauce."});
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
