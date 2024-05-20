package com.example.kitchemate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ListView recipesListView;
    private ArrayList<String> recipeNamesList;
    private RecipeAdapter recipesListViewAdapter;
    private Button navMenuButtonFindRecipes;
    private ArrayList<Recipe> recipesList;
    private Toolbar mainToolbar;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DBHelper.copyDatabase(this);
        setContentView(R.layout.main_activity);

        searchView = findViewById(R.id.mainActivitySearchView);
        searchView.clearFocus();
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

        recipesListView = findViewById(R.id.listViewRecipes);
        recipesList = new ArrayList<Recipe>();
        recipeNamesList = new ArrayList<>();
        recipesListViewAdapter = new RecipeAdapter(this, R.layout.recipe_list_item, recipesList);
        this.recipesListView.setAdapter(recipesListViewAdapter);
        recipesList = DBHelper.getRecipes(this, recipesListViewAdapter);
        DBHelper.fetchRecipes(this, recipeNamesList, recipesListViewAdapter);

        this.recipesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedRecipe = recipeNamesList.get(position);
                Intent intent = new Intent(MainActivity.this, RecipeDetailsActivity.class);
                intent.putExtra("recipeName", selectedRecipe);
                startActivity(intent);
            }
        });

    }

    private void filterRecipes(String query) {
        query = query.toLowerCase(Locale.ROOT);
        ArrayList<Recipe> filteredRecipes = new ArrayList<>();

        for (Recipe recipe : recipesList) {
            if (recipe.getName().toLowerCase(Locale.ROOT).contains(query)) {
                filteredRecipes.add(recipe);
            }
        }

        recipesListViewAdapter.clear();
        recipesListViewAdapter.addAll(filteredRecipes);
        recipesListViewAdapter.notifyDataSetChanged();
    }

    public void navMenuButtonFindRecipesOnClickListener(View view) {
        Intent intent = new Intent(MainActivity.this, FindRecipesActivity.class);
        startActivity(intent);
    }
}