package com.example.myapplication;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.appbar.MaterialToolbar;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);


            return insets;
        });
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        MaterialToolbar toolbar =findViewById(R.id.toolbar);

        TextView set = findViewById(R.id.settingScreen);
        TextView rec = findViewById(R.id.recipeView);
        TextView ing = findViewById(R.id.ingredientList);

        navController.addOnDestinationChangedListener((controller, destination, args) -> {
            if (destination.getId() == R.id.loginScreen) {
                toolbar.setVisibility(View.GONE);
            } else {
                toolbar.setVisibility(View.VISIBLE);
            }
        });

        set.setOnClickListener(view -> getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment, new SettingScreen()).commit());
        rec.setOnClickListener(view -> getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment, new RecipeView()).commit());
        ing.setOnClickListener(view -> getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment, new IngredientList()).commit());

    }
}
