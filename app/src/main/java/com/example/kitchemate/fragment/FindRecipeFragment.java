package com.example.kitchemate.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.kitchemate.R;
import com.example.kitchemate.activity.FoundRecipesActivity;
import com.example.kitchemate.component.IngredientListComponentFactory;

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
        setRetainInstance(true);
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
        if (!ingredientNamesList.isEmpty()) {
            Intent intent = new Intent(getContext(), FoundRecipesActivity.class);
            intent.putStringArrayListExtra("ingredients", ingredientNamesList);
            startActivity(intent);
        }
    }

    public void addIngredient(View view) {
        EditText editTextIngredient = getView().findViewById(R.id.ingrEditText);
        IngredientListComponentFactory factory = new IngredientListComponentFactory();
        final String ingredientName = editTextIngredient.getText().toString().trim()
                .toLowerCase().replaceAll("[^a-zA-Zа-яА-ЯёЁ]", "");

        if (ingredientName.isEmpty()) {
            return;
        }
        ingredientNamesList.add(ingredientName);
        factory.createNewIngredientComponent(ingredientName, getContext(), ingredientNamesList,
                ingredientListContainer, editTextIngredient, getResources());
    }
}