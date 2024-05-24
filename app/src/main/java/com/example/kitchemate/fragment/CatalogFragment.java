package com.example.kitchemate.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kitchemate.R;
import com.example.kitchemate.activity.MainActivity;
import com.example.kitchemate.activity.RecipeDetailsActivity;
import com.example.kitchemate.adapter.RecipeAdapter;
import com.example.kitchemate.api.ApiClient;
import com.example.kitchemate.api.ApiService;
import com.example.kitchemate.model.Recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CatalogFragment extends Fragment {
    private SearchView searchView;
    private ListView recipesListView;
    private RecipeAdapter recipesListViewAdapter;
    private ArrayList<Recipe> recipesList;
    private ArrayList<Recipe> originalRecipesList;
    private ApiService apiService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        searchView = getView().findViewById(R.id.mainActivitySearchView);
        searchView.clearFocus();

        recipesListView = getView().findViewById(R.id.listViewRecipes);
        recipesList = new ArrayList<>();
        originalRecipesList = new ArrayList<>();
        recipesListViewAdapter = new RecipeAdapter(getContext(), R.layout.recipe_list_item, recipesList);
        recipesListView.setAdapter(recipesListViewAdapter);
        apiService = ApiClient.getClient().create(ApiService.class);
        fetchRecipes();

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

        recipesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Recipe selectedRecipe = recipesList.get(position);
                Intent intent = new Intent(getContext(), RecipeDetailsActivity.class);
                intent.putExtra("recipeName", selectedRecipe.getName());
                intent.putExtra("recipeId", selectedRecipe.getId());
                intent.putExtra("recipeIngr", selectedRecipe.getIngredients());
                intent.putExtra("recipeHowTo", selectedRecipe.getHowTo());
                intent.putExtra("recipeImgP", selectedRecipe.getImagePath());
                startActivity(intent);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_catalog, container, false);
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
                    Log.e("FAILURE", "Failed to fetch recipes");
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.e("FAILURE", "Failed to fetch recipes");
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