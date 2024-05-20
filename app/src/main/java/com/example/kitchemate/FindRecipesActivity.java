package com.example.kitchemate;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Locale;

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

            // Инициализируем контейнер для элемента списка добавленных продуктов
            LinearLayout ingredientListItem = new LinearLayout(FindRecipesActivity.this);
            ingredientListItem.setOrientation(LinearLayout.HORIZONTAL);
            ingredientListItem.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));

            // Создаем новый TextView для ингредиента
            TextView textViewIngredient = new TextView(this);
            textViewIngredient.setPadding(64,2,2,2);
            textViewIngredient.setText(ingredientName);
            textViewIngredient.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1));

            // Добавляем кнопку "крестик" для удаления ингредиента
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

            // Очищаем EditText после добавления ингредиента
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
