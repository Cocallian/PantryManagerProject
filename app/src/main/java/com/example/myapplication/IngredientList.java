package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;

    private MyItemRecyclerViewAdapter adapter;
    private PantryDao dao;
    private int userId;

    // Empty constructor required by Android.
    public IngredientList() {
    }

    // Creates the screen with a column count.
    @SuppressWarnings("unused")
    public static IngredientList newInstance(int columnCount) {
        IngredientList fragment = new IngredientList();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    // Reads the column count setting.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
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
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

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
        new Thread(() -> {
            List<PantryItem> items = dao.getAllForUser(userId);
            if (isAdded()) {
                requireActivity().runOnUiThread(() -> adapter.setItems(items));
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
