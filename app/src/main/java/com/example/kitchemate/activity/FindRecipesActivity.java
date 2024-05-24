package com.example.kitchemate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.kitchemate.R;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class FindRecipesActivity extends AppCompatActivity {

    private ArrayList<String> ingredientNamesList = new ArrayList<>();
    private LinearLayout ingredientListContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_recipe_activity);

        Toolbar toolbar = findViewById(R.id.toolbarRecipe1);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ingredientListContainer = findViewById(R.id.ingredientsListView);
        DrawerLayout drawerLayout = findViewById(R.id.findRecipeDrawerLayout);
        NavigationView navigationView = findViewById(R.id.findRecipeNavigationView);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navCatalog:

                        break;
                    case R.id.navFindRecipe:
                        break;
                    case R.id.navAbout:
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    public void findByIndredients(View view) {
        Intent intent = new Intent(this, FoundRecipesActivity.class);
        intent.putStringArrayListExtra("ingredients", ingredientNamesList);
        startActivity(intent);
    }

    public void addIngredient(View view) {
        EditText editTextIngredient = findViewById(R.id.ingrEditText);
        String ingredientName = editTextIngredient.getText().toString().trim();

        if (!ingredientName.isEmpty()) {

            ingredientName = ingredientName.toLowerCase();
            ingredientName.replaceAll("[^a-zA-Zа-яА-ЯёЁ]", "");
            ingredientNamesList.add(ingredientName);

            LinearLayout ingredientListItem = new LinearLayout(FindRecipesActivity.this);
            ingredientListItem.setOrientation(LinearLayout.HORIZONTAL);
            ingredientListItem.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));

            TextView textViewIngredient = new TextView(this);
            textViewIngredient.setPadding(64,2,2,2);
            textViewIngredient.setText(ingredientName);
            textViewIngredient.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1));

            Button deleteButton = new Button(this);
            deleteButton.setText("✖");
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ingredientListContainer.removeView(ingredientListItem);
                    int pos = ingredientListContainer.indexOfChild(ingredientListItem);
                    if (pos != -1 && pos < ingredientNamesList.size()) {
                        ingredientNamesList.remove(pos);
                    }
                }
            });

            // Заполняем элемент списка ингридиентов
            // Формат: Текст -> "Название х" <- Кнопка
            ingredientListItem.addView(textViewIngredient);
            ingredientListItem.addView(deleteButton);
            ingredientListContainer.addView(ingredientListItem);

            editTextIngredient.setText("");
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
