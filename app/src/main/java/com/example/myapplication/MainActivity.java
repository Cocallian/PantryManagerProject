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

import com.example.myapplication.data.AppDatabase;
import com.example.myapplication.data.PantryItem;
import com.example.myapplication.data.Recipe;
import com.example.myapplication.data.Users;
import com.google.android.material.appbar.MaterialToolbar;


public class MainActivity extends AppCompatActivity {

    private static final String TL = "login";
    private static final String TI = "ingredients";
    private static final String TR = "recipes";
    private static final String TS = "settings";
    private static final String TF = "pantry_form";
    private static final String TD = "recipe_detail";

    private static final String STATE_CURRENT = "current_tab";
    private static final String STATE_PREVIOUS = "previous_tab";

    private MaterialToolbar toolbar;

    private String currentTag;
    private String previousTag;

    private OnBackPressedCallback backCallback;

    // Starts the app, tabs and database seeding.
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
            // Returns to the previous tab when back is pressed.
            @Override
            public void handleOnBackPressed() {
                String target = previousTag;
                previousTag = null;
                backCallback.setEnabled(false);
                switchTo(target, false);
            }
        };
        getOnBackPressedDispatcher().addCallback(this, backCallback);

        getSupportFragmentManager().addOnBackStackChangedListener(() -> {
            boolean formOpen = getSupportFragmentManager().getBackStackEntryCount() > 0;
            backCallback.setEnabled(previousTag != null && !formOpen);

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

        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance(getApplicationContext());
            Users.seedIfEmpty(db.usersDao());
            PantryItem.seedIfEmpty(db.pantryDao(), db.usersDao());
            Recipe.seedIfEmpty(db.recipeDao());
        }).start();
    }

    // Saves the current tab for rotation.
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STATE_CURRENT, currentTag);
        outState.putString(STATE_PREVIOUS, previousTag);
    }

    // Shows the main tabs after login.
    public void onLoginSuccess() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment login = fm.findFragmentByTag(TL);
        if (login != null) {
            fm.beginTransaction().setReorderingAllowed(true).remove(login).commit();
        }
        toolbar.setVisibility(View.VISIBLE);
        switchTo(TI, false);
    }

    // Clears the tabs and shows login.
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

        currentTag = null;
        previousTag = null;
        backCallback.setEnabled(false);
        toolbar.setVisibility(View.GONE);
        updateHighlight();
    }

    // Opens the form to add an item.
    public void openPantryItemForm() {
        openPantryItemForm(-1);
    }

    // Opens the form, editing if id given.
    public void openPantryItemForm(int itemId) {
        PantryItemForm form = new PantryItemForm();
        Bundle args = new Bundle();
        args.putInt(PantryItemForm.ARG_ITEM_ID, itemId);
        form.setArguments(args);
        openFullScreen(form, TF);
    }

    // Opens the full recipe screen.
    public void openRecipeDetail(int recipeId) {
        RecipeDetail detail = new RecipeDetail();
        Bundle args = new Bundle();
        args.putInt(RecipeDetail.ARG_RECIPE_ID, recipeId);
        detail.setArguments(args);
        openFullScreen(detail, TD);
    }

    // Shows a fragment full screen over the current one.
    private void openFullScreen(Fragment fragment, String tag) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment current = fm.findFragmentByTag(currentTag);

        FragmentTransaction tx = fm.beginTransaction().setReorderingAllowed(true);
        if (current != null) {
            tx.hide(current);
        }
        tx.add(R.id.fragment_container, fragment, tag);
        tx.addToBackStack(tag);
        tx.commit();
        backCallback.setEnabled(false);
        toolbar.setVisibility(View.GONE);
    }

    // Shows the chosen tab.
    private void switchTo(String tag, boolean rememberPrevious) {
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

        if (target instanceof RecipeView) {
            ((RecipeView) target).refresh();
        }

        if (rememberPrevious) {
            previousTag = currentTag;
        }
        currentTag = tag;
        backCallback.setEnabled(previousTag != null);
        updateHighlight();
    }

    // Highlights the active tab button.
    private void updateHighlight() {
        findViewById(R.id.ingredientList).setSelected(TI.equals(currentTag));
        findViewById(R.id.recipeView).setSelected(TR.equals(currentTag));
        findViewById(R.id.settingScreen).setSelected(TS.equals(currentTag));
    }

    // Hides a tab if it exists.
    private void hideTab(FragmentManager fm, FragmentTransaction tx, String tag) {
        Fragment f = fm.findFragmentByTag(tag);
        if (f != null) {
            tx.hide(f);
        }
    }

    // Creates the screen for a tab.
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
