package com.example.kitchemate.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kitchemate.R;
import com.example.kitchemate.fragment.AboutFragment;
import com.example.kitchemate.fragment.CatalogFragment;
import com.example.kitchemate.fragment.FindRecipeFragment;
import com.example.kitchemate.model.Recipe;
import com.example.kitchemate.adapter.RecipeAdapter;
import com.example.kitchemate.api.ApiClient;
import com.example.kitchemate.api.ApiService;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private CatalogFragment catalogFragment;
    private FindRecipeFragment findRecipeFragment;
    private AboutFragment aboutFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        drawerLayout = findViewById(R.id.mainact);
        navigationView = findViewById(R.id.navigationView);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CatalogFragment()).commit();
            navigationView.setCheckedItem(R.id.navCatalog);
        }

        catalogFragment = new CatalogFragment();
        findRecipeFragment = new FindRecipeFragment();
        aboutFragment = new AboutFragment();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navCatalog:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, catalogFragment).commit();
                        navigationView.setCheckedItem(R.id.navCatalog);
                        break;
                    case R.id.navFindRecipe:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, findRecipeFragment).commit();
                        navigationView.setCheckedItem(R.id.navFindRecipe);
                        break;
                    case R.id.navAbout:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, aboutFragment).commit();
                        navigationView.setCheckedItem(R.id.navAbout);
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}