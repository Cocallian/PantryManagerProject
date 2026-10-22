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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingScreen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingScreen extends Fragment {

    // SharedPreferences file and key used to remember the chosen theme.
    // MainActivity reads these on launch to re-apply the theme.
    public static final String PREFS_NAME = "pantry_settings";
    public static final String KEY_DARK_MODE = "dark_mode";

    public static final String KEY_LOGGED_IN_USER = "logged_in_user";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingScreen() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingScreen.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingScreen newInstance(String param1, String param2) {
        SettingScreen fragment = new SettingScreen();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting_screen, container, false);
    }

    // Views only exist once onCreateView has returned, so they are looked up here.
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MaterialSwitch mode = view.findViewById(R.id.switch_dark_mode);
        MaterialSwitch units = view.findViewById(R.id.switch_units);
        MaterialSwitch alerts = view.findViewById(R.id.switch_expiry_alerts);

        SharedPreferences prefs = requireContext()
                .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        // Show the saved choice before attaching the listener, so setting the
        // initial state does not trigger a theme change.
        mode.setChecked(prefs.getBoolean(KEY_DARK_MODE, false));

        mode.setOnCheckedChangeListener((button, isChecked) -> {
            prefs.edit().putBoolean(KEY_DARK_MODE, isChecked).apply();
            // Switches the whole app's theme; the activity is recreated automatically.
            AppCompatDelegate.setDefaultNightMode(isChecked
                    ? AppCompatDelegate.MODE_NIGHT_YES
                    : AppCompatDelegate.MODE_NIGHT_NO);
        });

        // Clear the saved user, then ask MainActivity to go back to the login screen
        view.findViewById(R.id.button_logout).setOnClickListener(v -> {
            logOut();
            ((MainActivity) requireActivity()).onLogout();
        });
    }

    public void logOut()
    {
        requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit()
                .remove(KEY_LOGGED_IN_USER)
                .apply();

    }
}