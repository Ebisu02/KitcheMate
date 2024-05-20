package com.example.kitchemate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;

public class RecipeAdapter extends ArrayAdapter<Recipe> {
    private Context context;
    private int resource;
    private ArrayList<Recipe> recipes;

    public RecipeAdapter(Context context, int resource, ArrayList<Recipe> recipes) {
        super(context, resource, recipes);
        this.context = context;
        this.resource = resource;
        this.recipes = recipes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
        }
        Recipe recipe = recipes.get(position);

        ImageView imageView = convertView.findViewById(R.id.imageRecipe);
        TextView textView = convertView.findViewById(R.id.textRecipeName);

        imageView.setImageResource(recipe.getImageResource());
        textView.setText(recipe.getName());

        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public void clear() {
        recipes.clear();
        notifyDataSetChanged();
    }

    @Override
    public void addAll(Collection<? extends Recipe> collection) {
        recipes.addAll(collection);
        notifyDataSetChanged();
    }
}
