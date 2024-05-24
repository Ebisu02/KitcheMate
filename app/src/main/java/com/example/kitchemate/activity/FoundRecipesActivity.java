package com.example.kitchemate.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.kitchemate.R;
import com.example.kitchemate.adapter.RecipeAdapter;
import com.example.kitchemate.api.ApiClient;
import com.example.kitchemate.api.ApiService;
import com.example.kitchemate.model.Recipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoundRecipesActivity extends AppCompatActivity {

    private ArrayList<String> ingredients;
    private RecipeAdapter recipeAdapter;
    private ListView recipeListView;
    private ArrayList<Recipe> foundRecipes = new ArrayList<Recipe>();
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.found_recipes_activity);
        foundRecipes.clear();
        Toolbar toolbar = findViewById(R.id.foundRecipesToolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ingredients = getIntent().getStringArrayListExtra("ingredients");
        apiService = ApiClient.getClient().create(ApiService.class);

        recipeListView = findViewById(R.id.foundRecipesListView);
        recipeAdapter = new RecipeAdapter(this, R.layout.recipe_list_item, foundRecipes);
        recipeListView.setAdapter(recipeAdapter);
        fetchRecipes(ingredients);

        this.recipeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Recipe selectedRecipe = foundRecipes.get(position);
                Intent intent = new Intent(FoundRecipesActivity.this, RecipeDetailsActivity.class);
                intent.putExtra("recipeName", selectedRecipe.getName());
                intent.putExtra("recipeId", selectedRecipe.getId());
                intent.putExtra("recipeIngr", selectedRecipe.getIngredients());
                intent.putExtra("recipeHowTo", selectedRecipe.getHowTo());
                intent.putExtra("recipeImgP", selectedRecipe.getImagePath());
                startActivity(intent);
            }
        });
    }

    private void fetchRecipes(ArrayList<String> ingredients) {

        for (String ingredient : ingredients) {
            Call<List<Recipe>> call = apiService.searchRecipes(ingredient);
            call.enqueue(new Callback<List<Recipe>>() {
                @Override
                public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        foundRecipes.addAll(response.body());
                        Collections.shuffle(foundRecipes);
                        recipeAdapter.notifyDataSetChanged();
                    } else {
                        Log.e("FAILURE", "Failed to fetch recipes");
                    }
                }

                @Override
                public void onFailure(Call<List<Recipe>> call, Throwable t) {
                    Log.e("FAILURE", "Failed to fetch recipes");
                }
            });
        }
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