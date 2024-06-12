package com.example.kitchemate.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.kitchemate.R;
import com.example.kitchemate.fragment.AboutFragment;
import com.example.kitchemate.fragment.CatalogFragment;
import com.example.kitchemate.fragment.FindRecipeFragment;
import com.google.android.material.navigation.NavigationView;

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

        catalogFragment = new CatalogFragment(this);
        findRecipeFragment = new FindRecipeFragment();
        aboutFragment = new AboutFragment();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, catalogFragment).commit();
            navigationView.setCheckedItem(R.id.navCatalog);
        }

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