package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.materialswitch.MaterialSwitch;

public class SettingScreen extends Fragment {

    public static final String PREFS_NAME = "pantry_settings";
    public static final String KEY_DARK_MODE = "dark_mode";
    public static final String KEY_EXPIRY_HIGHLIGHT = "expiry_highlight";

    public static final String KEY_LOGGED_IN_USER = "logged_in_user";

    // Empty constructor required by Android.
    public SettingScreen() {
    }

    // Inflates the settings layout.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting_screen, container, false);
    }

    // Sets up switches and logout button.
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MaterialSwitch mode = view.findViewById(R.id.switch_dark_mode);

        SharedPreferences prefs = requireContext()
                .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        mode.setChecked(prefs.getBoolean(KEY_DARK_MODE, false));

        mode.setOnCheckedChangeListener((button, isChecked) -> {
            prefs.edit().putBoolean(KEY_DARK_MODE, isChecked).apply();
            AppCompatDelegate.setDefaultNightMode(isChecked
                    ? AppCompatDelegate.MODE_NIGHT_YES
                    : AppCompatDelegate.MODE_NIGHT_NO);
        });

        MaterialSwitch highlight = view.findViewById(R.id.switch_expiry_highlight);
        highlight.setChecked(prefs.getBoolean(KEY_EXPIRY_HIGHLIGHT, true));
        highlight.setOnCheckedChangeListener((button, isChecked) ->
                prefs.edit().putBoolean(KEY_EXPIRY_HIGHLIGHT, isChecked).apply());

        view.findViewById(R.id.button_logout).setOnClickListener(v -> {
            logOut();
            ((MainActivity) requireActivity()).onLogout();
        });
    }

    // Forgets the logged in user.
    public void logOut()
    {
        requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit()
                .remove(KEY_LOGGED_IN_USER)
                .apply();

    }
}