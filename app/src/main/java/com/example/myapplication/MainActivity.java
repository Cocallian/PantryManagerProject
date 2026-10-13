package com.example.myapplication;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.appbar.MaterialToolbar;

public class MainActivity extends AppCompatActivity {

    private static final String TL = "login";
    private static final String TI = "ingredients";
    private static final String TR = "recipes";
    private static final String TS = "settings";

    private static final String STATE_CURRENT = "current_tab";
    private static final String STATE_PREVIOUS = "previous_tab";

    private MaterialToolbar toolbar;

    private String currentTag;
    private String previousTag;

    private OnBackPressedCallback backCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        boolean dark = getSharedPreferences(SettingScreen.PREFS_NAME, MODE_PRIVATE)
                .getBoolean(SettingScreen.KEY_DARK_MODE, false);
        AppCompatDelegate.setDefaultNightMode(dark
                ? AppCompatDelegate.MODE_NIGHT_YES
                : AppCompatDelegate.MODE_NIGHT_NO);

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        toolbar = findViewById(R.id.toolbar);

        backCallback = new OnBackPressedCallback(false) {
            @Override
            public void handleOnBackPressed() {
                String target = previousTag;
                previousTag = null;
                backCallback.setEnabled(false);
                switchTo(target, false);
            }
        };
        getOnBackPressedDispatcher().addCallback(this, backCallback);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container, new LoginScreen(), TL)
                    .commit();
            toolbar.setVisibility(View.GONE);
        } else {
            currentTag = savedInstanceState.getString(STATE_CURRENT);
            previousTag = savedInstanceState.getString(STATE_PREVIOUS);
            backCallback.setEnabled(previousTag != null);

            boolean onLogin = getSupportFragmentManager().findFragmentByTag(TL) != null;
            toolbar.setVisibility(onLogin ? View.GONE : View.VISIBLE);
            updateHighlight();
        }

        findViewById(R.id.ingredientList).setOnClickListener(v -> switchTo(TI, true));
        findViewById(R.id.recipeView).setOnClickListener(v -> switchTo(TR, true));
        findViewById(R.id.settingScreen).setOnClickListener(v -> switchTo(TS, true));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STATE_CURRENT, currentTag);
        outState.putString(STATE_PREVIOUS, previousTag);
    }

    public void onLoginSuccess() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment login = fm.findFragmentByTag(TL);
        if (login != null) {
            fm.beginTransaction().setReorderingAllowed(true).remove(login).commit();
        }
        toolbar.setVisibility(View.VISIBLE);
        switchTo(TI, false);
    }

    private void switchTo(String tag, boolean rememberPrevious) {
        if (tag.equals(currentTag)) {
            return;
        }

        FragmentManager fm = getSupportFragmentManager();
        Fragment target = fm.findFragmentByTag(tag);
        FragmentTransaction tx = fm.beginTransaction().setReorderingAllowed(true);

        switch (tag) {
            case TI:
                hideTab(fm, tx, TR);
                hideTab(fm, tx, TS);
                break;
            case TR:
                hideTab(fm, tx, TI);
                hideTab(fm, tx, TS);
                break;
            case TS:
                hideTab(fm, tx, TI);
                hideTab(fm, tx, TR);
                break;
        }

        if (target == null) {
            tx.add(R.id.fragment_container, createFragment(tag), tag);
        } else {
            tx.show(target);
        }
        tx.commit();

        if (rememberPrevious) {
            previousTag = currentTag;
        }
        currentTag = tag;
        backCallback.setEnabled(previousTag != null);
        updateHighlight();
    }

    private void updateHighlight() {
        findViewById(R.id.ingredientList).setSelected(TI.equals(currentTag));
        findViewById(R.id.recipeView).setSelected(TR.equals(currentTag));
        findViewById(R.id.settingScreen).setSelected(TS.equals(currentTag));
    }

    private void hideTab(FragmentManager fm, FragmentTransaction tx, String tag) {
        Fragment f = fm.findFragmentByTag(tag);
        if (f != null) {
            tx.hide(f);
        }
    }

    private Fragment createFragment(String tag) {
        switch (tag) {
            case TR:
                return new RecipeView();
            case TS:
                return new SettingScreen();
            default:
                return new IngredientList();
        }
    }
}
