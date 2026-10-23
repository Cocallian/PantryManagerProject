package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.data.AppDatabase;
import com.example.myapplication.data.Recipe;
import com.example.myapplication.data.RecipeDao;
import com.example.myapplication.data.RecipeIngredient;
import com.example.myapplication.data.RecipeStep;
import com.example.myapplication.databinding.FragmentRecipeDetailBinding;

import java.util.List;

public class RecipeDetail extends Fragment {

    public static final String ARG_RECIPE_ID = "recipe_id";

    private FragmentRecipeDetailBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentRecipeDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonBack.setOnClickListener(v ->
                requireActivity().getSupportFragmentManager().popBackStack());

        int recipeId = getArguments() != null ? getArguments().getInt(ARG_RECIPE_ID, -1) : -1;
        RecipeDao dao = AppDatabase.getInstance(requireContext()).recipeDao();

        new Thread(() -> {
            Recipe recipe = dao.getById(recipeId);
            List<RecipeIngredient> ingredients = dao.getIngredients(recipeId);
            List<RecipeStep> steps = dao.getSteps(recipeId);

            StringBuilder ingredientText = new StringBuilder();
            for (RecipeIngredient ingredient : ingredients) {
                String quantity = ingredient.quantity == Math.floor(ingredient.quantity)
                        ? String.valueOf((long) ingredient.quantity)
                        : String.valueOf(ingredient.quantity);
                if (ingredientText.length() > 0) {
                    ingredientText.append("\n");
                }
                ingredientText.append("• ").append(quantity).append(" ")
                        .append(ingredient.unit).append(" ").append(ingredient.name);
            }

            StringBuilder stepText = new StringBuilder();
            for (RecipeStep step : steps) {
                if (stepText.length() > 0) {
                    stepText.append("\n\n");
                }
                stepText.append(step.stepNumber).append(". ").append(step.text);
            }

            if (recipe != null && isAdded()) {
                requireActivity().runOnUiThread(() -> {
                    if (binding == null) {
                        return;
                    }
                    binding.detailTitle.setText(recipe.name);
                    binding.detailIngredients.setText(ingredientText);
                    binding.detailSteps.setText(stepText);
                });
            }
        }).start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
