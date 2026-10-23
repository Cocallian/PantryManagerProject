package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.data.AppDatabase;
import com.example.myapplication.data.PantryDao;
import com.example.myapplication.data.PantryItem;
import com.example.myapplication.databinding.FragmentPantryItemFormBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class PantryItemForm extends Fragment {

    public static final String ARG_ITEM_ID = "item_id";

    private FragmentPantryItemFormBinding binding;
    private PantryDao dao;
    private int itemId = -1;
    private int userId;
    private PantryItem editing;

    // Inflates the form layout.
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentPantryItemFormBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    // Sets up buttons and edit mode.
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Context context = requireContext();
        dao = AppDatabase.getInstance(context).pantryDao();
        userId = context.getSharedPreferences(SettingScreen.PREFS_NAME, Context.MODE_PRIVATE)
                .getInt(SettingScreen.KEY_LOGGED_IN_USER, -1);

        if (getArguments() != null) {
            itemId = getArguments().getInt(ARG_ITEM_ID, -1);
        }

        binding.buttonBack.setOnClickListener(v ->
                requireActivity().getSupportFragmentManager().popBackStack());
        binding.buttonSubmit.setOnClickListener(v -> submit());

        if (itemId != -1) {
            binding.formTitle.setText(R.string.form_title_edit);
            binding.formSubtitle.setText(R.string.form_subtitle_edit);
            binding.buttonSubmit.setText(R.string.form_submit_edit);
            loadItem();
        }
    }

    // Loads the item being edited.
    private void loadItem() {
        new Thread(() -> {
            PantryItem item = dao.getById(itemId);
            if (item != null && isAdded()) {
                requireActivity().runOnUiThread(() -> {
                    if (binding == null) {
                        return;
                    }
                    editing = item;
                    binding.inputName.setText(item.name);
                    binding.inputQuantity.setText(item.quantity == Math.floor(item.quantity)
                            ? String.valueOf((long) item.quantity)
                            : String.valueOf(item.quantity));
                    binding.inputExpiry.setText(item.expiryDate == null ? "" : item.expiryDate);

                    @SuppressWarnings("unchecked")
                    ArrayAdapter<CharSequence> units =
                            (ArrayAdapter<CharSequence>) binding.spinnerUnit.getAdapter();
                    int position = units.getPosition(item.unit);
                    if (position >= 0) {
                        binding.spinnerUnit.setSelection(position);
                    }
                });
            }
        }).start();
    }

    // Checks inputs, then saves the item.
    private void submit() {
        binding.layoutName.setError(null);
        binding.layoutQuantity.setError(null);
        binding.layoutExpiry.setError(null);

        String name = String.valueOf(binding.inputName.getText()).trim();
        String quantityText = String.valueOf(binding.inputQuantity.getText()).trim();
        String expiry = String.valueOf(binding.inputExpiry.getText()).trim();

        boolean valid = true;

        if (name.isEmpty()) {
            binding.layoutName.setError(getString(R.string.form_error_name));
            valid = false;
        }

        double quantity = 0;
        try {
            quantity = Double.parseDouble(quantityText);
        } catch (NumberFormatException e) {
            quantity = 0;
        }
        if (quantity <= 0) {
            binding.layoutQuantity.setError(getString(R.string.form_error_quantity));
            valid = false;
        }

        if (!expiry.isEmpty() && !isValidDate(expiry)) {
            binding.layoutExpiry.setError(getString(R.string.form_error_expiry));
            valid = false;
        }

        if (!valid) {
            return;
        }

        String unit = String.valueOf(binding.spinnerUnit.getSelectedItem());
        PantryItem item = editing != null ? editing : new PantryItem();
        item.userId = userId;
        item.name = name;
        item.quantity = quantity;
        item.unit = unit;
        item.expiryDate = expiry.isEmpty() ? null : expiry;

        boolean isEdit = itemId != -1;
        new Thread(() -> {
            if (isEdit) {
                dao.update(item);
            } else {
                dao.insert(item);
            }
            if (isAdded()) {
                requireActivity().runOnUiThread(() ->
                        requireActivity().getSupportFragmentManager().popBackStack());
            }
        }).start();
    }

    // Checks the date is real yyyy-MM-dd.
    private boolean isValidDate(String text) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        format.setLenient(false);
        try {
            format.parse(text);
            return text.matches("\\d{4}-\\d{2}-\\d{2}");
        } catch (ParseException e) {
            return false;
        }
    }

    // Clears the binding to avoid leaks.
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
