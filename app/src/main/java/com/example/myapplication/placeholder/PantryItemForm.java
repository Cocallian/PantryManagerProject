package com.example.myapplication.placeholder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.databinding.FragmentPantryItemFormBinding;

/** Full-screen form for adding a new pantry item. */
public class PantryItemForm extends Fragment {

    private FragmentPantryItemFormBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentPantryItemFormBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Back: close this form (MainActivity put it on the back stack) and return to the list
        binding.buttonBack.setOnClickListener(v ->
                requireActivity().getSupportFragmentManager().popBackStack());

        // TODO: binding.buttonSubmit - validate the fields and save the PantryItem
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
