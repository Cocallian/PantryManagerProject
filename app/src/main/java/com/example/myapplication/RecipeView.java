package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.data.AppDatabase;
import com.example.myapplication.data.PantryItem;
import com.google.android.material.button.MaterialButtonToggleGroup;

import java.util.List;

public class RecipeView extends Fragment {

    private RecyclerView readyList;
    private RecyclerView almostList;
    private RecyclerView allList;
    private TextView emptyMessage;
    private MaterialButtonToggleGroup toggle;

    private final RecipeAdapter readyAdapter = new RecipeAdapter(this::openDetail);
    private final RecipeAdapter almostAdapter = new RecipeAdapter(this::openDetail);
    private final RecipeAdapter allAdapter = new RecipeAdapter(this::openDetail);

    // Empty constructor required by Android.
    public RecipeView() {
    }

    // Builds the recipe screen and its tabs.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_view_list, container, false);

        readyList = view.findViewById(R.id.list_ready);
        almostList = view.findViewById(R.id.list_almost);
        allList = view.findViewById(R.id.list_all);
        emptyMessage = view.findViewById(R.id.empty_message);
        toggle = view.findViewById(R.id.toggle_group);

        setUpList(readyList, readyAdapter);
        setUpList(almostList, almostAdapter);
        setUpList(allList, allAdapter);

        toggle.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked) {
                updateVisibility();
            }
        });

        return view;
    }

    // Reloads recipes when the screen reappears.
    @Override
    public void onResume() {
        super.onResume();
        loadRecipes();
    }

    // Reloads recipes if the screen is showing.
    public void refresh() {
        if (isAdded() && readyList != null) {
            loadRecipes();
        }
    }

    // Opens the detail screen for a recipe.
    private void openDetail(int recipeId) {
        ((MainActivity) requireActivity()).openRecipeDetail(recipeId);
    }

    // Attaches a layout manager and adapter to a list.
    private void setUpList(RecyclerView recyclerView, RecipeAdapter adapter) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(adapter);
    }

    // Matches recipes against the pantry and fills the lists.
    private void loadRecipes() {
        Context context = requireContext();
        int userId = context.getSharedPreferences(SettingScreen.PREFS_NAME, Context.MODE_PRIVATE)
                .getInt(SettingScreen.KEY_LOGGED_IN_USER, -1);
        AppDatabase db = AppDatabase.getInstance(context);

        new Thread(() -> {
            List<PantryItem> pantry = db.pantryDao().getAllForUser(userId);
            List<RecipeMatcher.Result> all = RecipeMatcher.matchAll(pantry, db.recipeDao());
            List<RecipeMatcher.Result> ready = RecipeMatcher.filterByMissing(all, 0);
            List<RecipeMatcher.Result> almost = RecipeMatcher.filterByMissing(all, 1);

            if (isAdded()) {
                requireActivity().runOnUiThread(() -> {
                    readyAdapter.setItems(ready);
                    almostAdapter.setItems(almost);
                    allAdapter.setItems(all);
                    updateVisibility();
                });
            }
        }).start();
    }

    // Shows the selected list or the empty message.
    private void updateVisibility() {
        if (toggle == null) {
            return;
        }
        int checked = toggle.getCheckedButtonId();
        boolean showReady = checked == R.id.button_ready;
        boolean showAlmost = checked == R.id.button_almost;
        RecipeAdapter active = showReady ? readyAdapter : showAlmost ? almostAdapter : allAdapter;
        boolean empty = active.getItemCount() == 0;

        readyList.setVisibility(showReady && !empty ? View.VISIBLE : View.GONE);
        almostList.setVisibility(showAlmost && !empty ? View.VISIBLE : View.GONE);
        allList.setVisibility(!showReady && !showAlmost && !empty ? View.VISIBLE : View.GONE);
        emptyMessage.setVisibility(empty ? View.VISIBLE : View.GONE);
        emptyMessage.setText(showReady ? R.string.recipes_empty_ready
                : showAlmost ? R.string.recipes_empty_almost : R.string.recipes_empty_all);
    }
}
