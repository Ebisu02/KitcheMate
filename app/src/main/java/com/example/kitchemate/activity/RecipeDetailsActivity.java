package com.example.kitchemate.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.kitchemate.api.ApiClient;
import com.example.kitchemate.R;
import com.example.kitchemate.model.Recipe;
import com.squareup.picasso.Picasso;

public class RecipeDetailsActivity extends AppCompatActivity {

    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_details_activity);

        ImageView imageViewRecipe = findViewById(R.id.imageRecipe);
        TextView textViewRecipeName = findViewById(R.id.textViewRecipeName);
        TextView textViewIngredientsList = findViewById(R.id.textViewIngredients);
        TextView textViewHowToList = findViewById(R.id.textViewHowTo);
        Toolbar toolbar = findViewById(R.id.toolbarRecipe);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        recipe = new Recipe(
                getIntent().getIntExtra("recipeId", 0),
                getIntent().getStringExtra("recipeName"),
                getIntent().getStringExtra("recipeIngr"),
                getIntent().getStringExtra("recipeHowTo"),
                getIntent().getStringExtra("recipeImgP")
        );

        Picasso.get()
                .load(ApiClient.IMG_PARSE_URL + recipe.getImagePath())
                .placeholder(R.drawable.ic_broken_img) // Изображение-заполнитель
                .error(R.drawable.ic_broken_img) // Изображение при ошибке загрузки
                .fit()
                .centerCrop()
                .into(imageViewRecipe);
        textViewRecipeName.setText(recipe.getName());
        textViewIngredientsList.setText(recipe.getIngredients());
        textViewHowToList.setText(recipe.getHowTo());
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