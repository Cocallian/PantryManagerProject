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
    private TextView emptyMessage;
    private MaterialButtonToggleGroup toggle;

    private final RecipeAdapter readyAdapter = new RecipeAdapter();
    private final RecipeAdapter almostAdapter = new RecipeAdapter();

    public RecipeView() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_view_list, container, false);

        readyList = view.findViewById(R.id.list_ready);
        almostList = view.findViewById(R.id.list_almost);
        emptyMessage = view.findViewById(R.id.empty_message);
        toggle = view.findViewById(R.id.toggle_group);

        setUpList(readyList, readyAdapter);
        setUpList(almostList, almostAdapter);

        toggle.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked) {
                updateVisibility();
            }
        });

        loadRecipes();
        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && readyList != null) {
            loadRecipes();
        }
    }

    private void setUpList(RecyclerView recyclerView, RecipeAdapter adapter) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(adapter);
    }

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
                    updateVisibility();
                });
            }
        }).start();
    }

    private void updateVisibility() {
        if (toggle == null) {
            return;
        }
        boolean showReady = toggle.getCheckedButtonId() == R.id.button_ready;
        RecipeAdapter active = showReady ? readyAdapter : almostAdapter;
        boolean empty = active.getItemCount() == 0;

        readyList.setVisibility(showReady && !empty ? View.VISIBLE : View.GONE);
        almostList.setVisibility(!showReady && !empty ? View.VISIBLE : View.GONE);
        emptyMessage.setVisibility(empty ? View.VISIBLE : View.GONE);
        emptyMessage.setText(showReady ? R.string.recipes_empty_ready : R.string.recipes_empty_almost);
    }
}
