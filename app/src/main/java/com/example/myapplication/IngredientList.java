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

/**
 * A fragment representing a list of Items.
 */

public class IngredientList extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;   // one ingredient per row

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public IngredientList() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static IngredientList newInstance(int columnCount) {
        IngredientList fragment = new IngredientList();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ingredient_list_list, container, false);

        // "Add" button: ask MainActivity to open the pantry item form
        view.findViewById(R.id.itemButt).setOnClickListener(v ->
                ((MainActivity) requireActivity()).openPantryItemForm());

        // The list now sits inside a layout with headings, so look it up by id
        RecyclerView recyclerView = view.findViewById(R.id.list);
        if (recyclerView != null) {
            Context context = view.getContext();
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            // The adapter starts empty and is filled once the database query finishes
            MyItemRecyclerViewAdapter adapter = new MyItemRecyclerViewAdapter();
            recyclerView.setAdapter(adapter);

            // Id of the logged-in user, saved by LoginScreen (-1 if nobody is logged in)
            int userId = requireContext()
                    .getSharedPreferences(SettingScreen.PREFS_NAME, Context.MODE_PRIVATE)
                    .getInt(SettingScreen.KEY_LOGGED_IN_USER, -1);

            PantryDao dao = AppDatabase.getInstance(context).pantryDao();

            // Database queries must not run on the main thread
            new Thread(() -> {
                List<PantryItem> items = dao.getAllForUser(userId);
                if (isAdded()) {
                    // Hand the result back to the main thread to update the list
                    requireActivity().runOnUiThread(() -> adapter.setItems(items));
                }
            }).start();
        }
        return view;
    }
}