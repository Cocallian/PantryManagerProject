package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Context;

import com.example.myapplication.data.AppDatabase;
import com.example.myapplication.data.Users;
import com.example.myapplication.data.UsersDao;

public class LoginScreen extends Fragment {

    // Empty constructor required by Android.
    public LoginScreen() {
    }

    // Inflates the login layout.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login_screen, container, false);
    }

    // Connects the login button.
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        EditText un = view.findViewById(R.id.UnameBox);
        EditText ps = view.findViewById(R.id.PBox);
        Button log = view.findViewById(R.id.logButt);

        log.setOnClickListener(v -> validate(un.getText().toString(), ps.getText().toString()));

    }

    // Remembers which user logged in.
    private void saveLoggedInUser(int id)
    {
        requireContext().getSharedPreferences(SettingScreen.PREFS_NAME, Context.MODE_PRIVATE)
                .edit()
                .putInt(SettingScreen.KEY_LOGGED_IN_USER, id)
                .apply();
    }
    // Checks login details against the database.
    public void validate(String user, String pass)
    {

        if(user.isEmpty() || pass.isEmpty())
        {
            Toast.makeText(getContext(), "Please enter a username and password", Toast.LENGTH_SHORT).show();
            return;
        }
        UsersDao dao = AppDatabase.getInstance(requireContext()).usersDao();

        new Thread(() -> {
            Users cur = dao.findUser(user, pass);

            if (!isAdded()) {
                return;
            }
            requireActivity().runOnUiThread(() -> {
                if (cur == null) {
                    Toast.makeText(getContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
                } else {
                    saveLoggedInUser(cur.id);
                    ((MainActivity) requireActivity()).onLoginSuccess();
                }
            });
        }).start();

    }
}