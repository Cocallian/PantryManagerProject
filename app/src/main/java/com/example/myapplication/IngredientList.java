package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.data.AppDatabase;
import com.example.myapplication.data.PantryDao;
import com.example.myapplication.data.PantryItem;

import java.util.List;

public class IngredientList extends Fragment implements MyItemRecyclerViewAdapter.OnItemActionListener {

    private MyItemRecyclerViewAdapter adapter;
    private PantryDao dao;
    private int userId;

    // Empty constructor required by Android.
    public IngredientList() {
    }

    // Builds the list screen and loads items.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ingredient_list_list, container, false);

        view.findViewById(R.id.itemButt).setOnClickListener(v ->
                ((MainActivity) requireActivity()).openPantryItemForm());

        RecyclerView recyclerView = view.findViewById(R.id.list);
        if (recyclerView != null) {
            Context context = view.getContext();
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            adapter = new MyItemRecyclerViewAdapter(this);
            recyclerView.setAdapter(adapter);

            userId = context
                    .getSharedPreferences(SettingScreen.PREFS_NAME, Context.MODE_PRIVATE)
                    .getInt(SettingScreen.KEY_LOGGED_IN_USER, -1);

            dao = AppDatabase.getInstance(context).pantryDao();
            loadItems();
        }
        return view;
    }

    // Loads the user's items into the list.
    private void loadItems() {
        boolean highlight = requireContext()
                .getSharedPreferences(SettingScreen.PREFS_NAME, Context.MODE_PRIVATE)
                .getBoolean(SettingScreen.KEY_EXPIRY_HIGHLIGHT, true);
        new Thread(() -> {
            List<PantryItem> items = dao.getAllForUser(userId);
            if (isAdded()) {
                requireActivity().runOnUiThread(() -> {
                    adapter.setHighlightExpiring(highlight);
                    adapter.setItems(items);
                });
            }
        }).start();
    }

    // Reloads items when the screen reappears.
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && dao != null) {
            loadItems();
        }
    }

    // Opens the form to edit an item.
    @Override
    public void onEdit(PantryItem item) {
        ((MainActivity) requireActivity()).openPantryItemForm(item.id);
    }

    // Deletes an item and refreshes the list.
    @Override
    public void onDelete(PantryItem item) {
        new Thread(() -> {
            dao.delete(item);
            loadItems();
        }).start();
    }
}
