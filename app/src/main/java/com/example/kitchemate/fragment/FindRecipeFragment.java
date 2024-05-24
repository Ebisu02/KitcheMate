package com.example.kitchemate.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kitchemate.R;
import com.example.kitchemate.activity.FoundRecipesActivity;

import java.util.ArrayList;

public class FindRecipeFragment extends Fragment {

    private ArrayList<String> ingredientNamesList = new ArrayList<>();
    private LinearLayout ingredientListContainer;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Toolbar toolbar = getView().findViewById(R.id.toolbarRecipe1);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setTitle(R.string.findrecipe_menu_title);
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        ingredientListContainer = getView().findViewById(R.id.ingredientsListView);
        Button buttonAdd = getView().findViewById(R.id.addIngrButton);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addIngredient(view);
            }
        });
        Button buttonFindRecipe = getView().findViewById(R.id.findRecipeButton);
        buttonFindRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findByIndredients(view);
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_find_recipe, container, false);
    }

    public void findByIndredients(View view) {
        Intent intent = new Intent(getContext(), FoundRecipesActivity.class);
        intent.putStringArrayListExtra("ingredients", ingredientNamesList);
        startActivity(intent);
    }

    public void addIngredient(View view) {
        EditText editTextIngredient = getView().findViewById(R.id.ingrEditText);
        String ingredientName = editTextIngredient.getText().toString().trim();

        if (!ingredientName.isEmpty()) {

            ingredientName = ingredientName.toLowerCase();
            ingredientName.replaceAll("[^a-zA-Zа-яА-ЯёЁ]", "");
            ingredientNamesList.add(ingredientName);

            LinearLayout ingredientListItem = new LinearLayout(getContext());
            ingredientListItem.setOrientation(LinearLayout.HORIZONTAL);
            ingredientListItem.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));

            TextView textViewIngredient = new TextView(getContext());
            textViewIngredient.setPadding(64,2,2,2);
            textViewIngredient.setText(ingredientName);
            textViewIngredient.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1));

            Button deleteButton = new Button(getContext());
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
}