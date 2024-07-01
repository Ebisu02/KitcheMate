package com.example.kitchemate.component;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kitchemate.R;

import java.util.ArrayList;

public class IngredientListComponentFactory {
    public void createNewIngredientComponent(String ingredientName, Context context, ArrayList<String> ingredientNamesList,
                                      LinearLayout ingredientListContainer, EditText editTextIngredient, Resources resources) {
        LinearLayout ingredientListItem = new LinearLayout(context);
        ingredientListItem.setOrientation(LinearLayout.HORIZONTAL);
        ingredientListItem.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));

        TextView textViewIngredient = new TextView(context);
        textViewIngredient.setPadding(64,2,2,2);
        textViewIngredient.setText(ingredientName);
        textViewIngredient.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1));

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,0,8,0);
        Button deleteButton = new Button(context);
        deleteButton.setText("✖");
        deleteButton.setTextColor(resources.getColor(R.color.gray));
        deleteButton.setBackground(resources.getDrawable(R.drawable.button_primary_selector));
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingredientListContainer.removeView(ingredientListItem);
                ingredientNamesList.remove(ingredientName);
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
