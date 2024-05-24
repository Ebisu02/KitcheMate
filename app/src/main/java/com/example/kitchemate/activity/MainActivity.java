package com.example.kitchemate.activity;

import androidx.annotation.NonNull;
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

    private ListView recipesListView;
    private RecipeAdapter recipesListViewAdapter;
    private ArrayList<Recipe> recipesList;
    private ArrayList<Recipe> originalRecipesList;
    private Toolbar mainToolbar;
    private SearchView searchView;
    DrawerLayout drawerLayout;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        searchView = findViewById(R.id.mainActivitySearchView);
        searchView.clearFocus();
        drawerLayout = findViewById(R.id.mainact);
        NavigationView navigationView = findViewById(R.id.navigationView);

        recipesListView = findViewById(R.id.listViewRecipes);
        recipesList = new ArrayList<>();
        originalRecipesList = new ArrayList<>();
        recipesListViewAdapter = new RecipeAdapter(this, R.layout.recipe_list_item, recipesList);
        recipesListView.setAdapter(recipesListViewAdapter);
        apiService = ApiClient.getClient().create(ApiService.class);
        fetchRecipes();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navCatalog:
                        break;
                    case R.id.navFindRecipe:
                        Intent intent = new Intent(MainActivity.this, FindRecipesActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.navAbout:
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterRecipes(newText);
                return true;
            }
        });
        this.recipesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Recipe selectedRecipe = recipesList.get(position);
                Intent intent = new Intent(MainActivity.this, RecipeDetailsActivity.class);
                intent.putExtra("recipeName", selectedRecipe.getName());
                intent.putExtra("recipeId", selectedRecipe.getId());
                intent.putExtra("recipeIngr", selectedRecipe.getIngredients());
                intent.putExtra("recipeHowTo", selectedRecipe.getHowTo());
                intent.putExtra("recipeImgP", selectedRecipe.getImagePath());
                startActivity(intent);
            }
        });
    }

    private void fetchRecipes() {
        Call<List<Recipe>> call = apiService.getAllRecipes();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    recipesList.clear();
                    originalRecipesList.clear();
                    recipesList.addAll(response.body());
                    originalRecipesList.addAll(response.body());
                    recipesListViewAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(MainActivity.this, "Failed to fetch recipes", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void filterRecipes(String query) {
        query = query.toLowerCase(Locale.ROOT);
        ArrayList<Recipe> filteredRecipes = new ArrayList<>();
        for (Recipe recipe : originalRecipesList) {
            if (recipe.getName().toLowerCase(Locale.ROOT).contains(query)) {
                filteredRecipes.add(recipe);
            }
        }
        recipesListViewAdapter.clear();
        recipesListViewAdapter.addAll(filteredRecipes);
        recipesListViewAdapter.notifyDataSetChanged();
    }
}