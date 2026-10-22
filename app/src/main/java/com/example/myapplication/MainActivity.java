package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
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

import com.example.myapplication.data.AppDatabase;
import com.example.myapplication.data.PantryItem;
import com.example.myapplication.data.Recipe;
import com.example.myapplication.data.RecipeDao;
import com.example.myapplication.data.Users;
import com.example.myapplication.placeholder.PantryItemForm;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TL = "login";
    private static final String TI = "ingredients";
    private static final String TR = "recipes";
    private static final String TS = "settings";
    private static final String TF = "pantry_form";

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

        // While a form is open the fragment back stack handles Back; our tab-history
        // callback only takes over again once the form has been closed.
        getSupportFragmentManager().addOnBackStackChangedListener(() -> {
            boolean formOpen = getSupportFragmentManager().getBackStackEntryCount() > 0;
            backCallback.setEnabled(previousTag != null && !formOpen);

            // The tab bar is hidden while a form is open and returns when it closes
            // (currentTag is null on the login screen, where the bar must stay hidden)
            toolbar.setVisibility(formOpen || currentTag == null ? View.GONE : View.VISIBLE);
        });

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
            boolean formOpen = getSupportFragmentManager().getBackStackEntryCount() > 0;
            toolbar.setVisibility(onLogin || formOpen ? View.GONE : View.VISIBLE);
            updateHighlight();
        }

        findViewById(R.id.ingredientList).setOnClickListener(v -> switchTo(TI, true));
        findViewById(R.id.recipeView).setOnClickListener(v -> switchTo(TR, true));
        findViewById(R.id.settingScreen).setOnClickListener(v -> switchTo(TS, true));

        // Seed starter users and recipes on first run (background thread; no-op afterwards)
        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance(getApplicationContext());
            RecipeDao dao = db.recipeDao();
            try {
                Users.seedIfEmpty(db.usersDao());
                PantryItem.seedIfEmpty(db.pantryDao(), db.usersDao());
                Recipe.seedIfEmpty(dao);
            } catch (Exception e) {
                Log.e("DB_TEST", "Seeding failed", e);
            }
            // TEMPORARY: log the seeded data to verify it, remove once confirmed
            List<Recipe> recipes = dao.getAll();
            Log.d("DB_TEST", "Recipes in database: " + recipes.size());
            for (Recipe r : recipes) {
                Log.d("DB_TEST", r.id + " " + r.name + " | ingredients: "
                        + dao.getIngredients(r.id).size()
                        + ", steps: " + dao.getSteps(r.id).size());
            }
        }).start();
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

    /** Returns to the login screen: removes all tabs so the next user starts fresh. */
    public void onLogout() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction tx = fm.beginTransaction().setReorderingAllowed(true);


        Fragment f1 = fm.findFragmentByTag(TI);
        Fragment f2 = fm.findFragmentByTag(TR);
        Fragment f3 = fm.findFragmentByTag(TS);

        if(f1 != null)tx.remove(f1);
        if (f2!= null)tx.remove(f2);
        if(f3 != null)tx.remove(f3);
        tx.add(R.id.fragment_container, new LoginScreen(), TL);
        tx.commit();

        // Reset navigation state, otherwise switchTo() would ignore the first tab next login
        currentTag = null;
        previousTag = null;
        backCallback.setEnabled(false);
        toolbar.setVisibility(View.GONE);
        updateHighlight();
    }

    /** Opens the pantry item form on top of the current tab; Back returns to the tab. */
    public void openPantryItemForm() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment current = fm.findFragmentByTag(currentTag);

        FragmentTransaction tx = fm.beginTransaction().setReorderingAllowed(true);
        if (current != null) {
            tx.hide(current);
        }
        tx.add(R.id.fragment_container, new PantryItemForm(), TF);
        tx.addToBackStack(TF);
        tx.commit();
        backCallback.setEnabled(false);
        toolbar.setVisibility(View.GONE);   // hide the tab bar while the form is open
    }

    private void switchTo(String tag, boolean rememberPrevious) {
        // Tapping a tab while a form is open closes the form first
        FragmentManager backStackFm = getSupportFragmentManager();
        if (backStackFm.getBackStackEntryCount() > 0) {
            backStackFm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

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
