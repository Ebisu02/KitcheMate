package com.example.kitchemate;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class FoundRecipesActivity extends AppCompatActivity {

    private ArrayList<String> ingredients;
    private RecipeAdapter recipeAdapter;
    private ListView recipeListView;
    private ArrayList<String> foundRecipeNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.found_recipes_activity);

        Toolbar toolbar = findViewById(R.id.foundRecipesToolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ingredients = getIntent().getStringArrayListExtra("ingredients");

        Pair<ArrayList<Recipe>, ArrayList<String>> p = DBHelper.findRecipesByIngredients(this, ingredients);

        ArrayList<Recipe> foundRecipes = p.first;
        foundRecipeNames = p.second;
        recipeListView = findViewById(R.id.foundRecipesListView);
        recipeAdapter = new RecipeAdapter(this, R.layout.recipe_list_item, foundRecipes);
        recipeListView.setAdapter(recipeAdapter);

        this.recipeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedRecipe = foundRecipeNames.get(position);
                Intent intent = new Intent(FoundRecipesActivity.this, RecipeDetailsActivity.class);
                intent.putExtra("recipeName", selectedRecipe);
                startActivity(intent);

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}