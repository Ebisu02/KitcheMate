package com.example.kitchemate.api;

import com.example.kitchemate.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("/recipes")
    Call<List<Recipe>> getAllRecipes();

    @GET("/recipes/{name}")
    Call<Recipe> getRecipeByName(@Path("name") String name);

    @GET("/search")
    Call<List<Recipe>> searchRecipes(@Query("q") String query);
}
