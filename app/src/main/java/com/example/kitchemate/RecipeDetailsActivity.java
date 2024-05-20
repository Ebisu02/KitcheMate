package com.example.kitchemate;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class RecipeDetailsActivity extends AppCompatActivity {

    private String recipe_name = "";
    private String recipe_ingr = "";
    private String recipe_howto = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_details_activity);

        // Получаем данные о рецепте из интента
        recipe_name = getIntent().getStringExtra("recipeName");

        // Находим представления в макете активити
        ImageView imageViewRecipe = findViewById(R.id.imageRecipe);
        TextView textViewRecipeName = findViewById(R.id.textViewRecipeName);
        TextView textViewIngredientsList = findViewById(R.id.textViewIngredients);
        TextView textViewHowToList = findViewById(R.id.textViewHowTo);

        fetchRecipe();
        imageViewRecipe.setImageResource(R.drawable.ic_launcher_foreground);
        textViewRecipeName.setText(recipe_name);
        textViewIngredientsList.setText(recipe_ingr);
        textViewHowToList.setText(recipe_howto);

        Toolbar toolbar = findViewById(R.id.toolbarRecipe);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void fetchRecipe() {
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String rawQ = "SELECT Name, Ingredients, HowTo FROM Recipes WHERE Name = \"" + recipe_name + "\"";
        Cursor uCursor = db.rawQuery(rawQ, null);

        if (uCursor.moveToNext()) {
            recipe_ingr = uCursor.getString(1).replace(", ", "\n- ");
            recipe_howto = uCursor.getString(2);
        }
        recipe_ingr = "- " + recipe_ingr;
        uCursor.close();
        db.close();
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