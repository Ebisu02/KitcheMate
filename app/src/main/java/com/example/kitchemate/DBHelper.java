package com.example.kitchemate;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Pair;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "recipes.db";
    private static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        return;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        return;
    }

    public static ArrayList<Recipe> getRecipes(Context context, RecipeAdapter recipesListViewAdapter) {
        ArrayList<Recipe> res = new ArrayList<Recipe>();
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor uCursor = db.rawQuery("SELECT Name FROM Recipes", null);

        if (uCursor.moveToFirst()) {
            do {
                String recipeName = uCursor.getString(0);
                res.add(new Recipe(R.drawable.not_found, recipeName));
            } while (uCursor.moveToNext());
        } else {
            Toast.makeText(context, "RecipesNotFound", Toast.LENGTH_SHORT).show();
        }

        uCursor.close();
        recipesListViewAdapter.clear();
        recipesListViewAdapter.addAll(res);
        recipesListViewAdapter.notifyDataSetChanged();
        db.close();

        return res;
    }

    public static void copyDatabase(Context context) {
        try {
            InputStream inputStream = context.getAssets().open("recipes.db");
            String outFileName = context.getDatabasePath("recipes.db").getPath();
            OutputStream outputStream = new FileOutputStream(outFileName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void fetchRecipes(Context context, ArrayList<String> recipeNamesList, RecipeAdapter recipesListViewAdapter) {
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor uCursor = db.rawQuery("SELECT Name FROM Recipes", null);

        if (uCursor.moveToFirst()) {
            do {
                String recipeName = uCursor.getString(0);
                recipeNamesList.add(recipeName);
            } while (uCursor.moveToNext());
        } else {
            Toast.makeText(context, "RecipesNotFound", Toast.LENGTH_SHORT).show();
        }

        uCursor.close();
        recipesListViewAdapter.notifyDataSetChanged();
        db.close();
    }

    public static Pair<ArrayList<Recipe>, ArrayList<String>> findRecipesByIngredients(Context context, ArrayList<String> ingredients) {
        ArrayList<Recipe> matchingRecipes = new ArrayList<>();
        ArrayList<String> matchingRecipeNames = new ArrayList<>();

        // 0 - Name
        // 1 - Ingredients
        ArrayList<Pair<String, String>> recipes = new ArrayList<>();
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor uCursor = db.rawQuery("SELECT Name, Ingredients FROM Recipes", null);
        if (uCursor.moveToFirst()) {
            do {
                Pair pair = new Pair(uCursor.getString(0), uCursor.getString(1));
                recipes.add(pair);
            } while (uCursor.moveToNext());
        } else {
            Toast.makeText(context, "RecipesNotFound", Toast.LENGTH_SHORT).show();
        }
        uCursor.close();
        db.close();

        for (Pair<String, String> recipe : recipes) {
            String[] recipeIngredients = recipe.second.split(", ");
            for (String ingredient : ingredients) {
                for (String recipeIngredient: recipeIngredients) {
                    if (recipeIngredient.startsWith(ingredient)) {
                        matchingRecipes.add(new Recipe(R.drawable.not_found, recipe.first));
                        matchingRecipeNames.add(recipe.first);
                        break;
                    }
                }
            }
        }

        Pair<ArrayList<Recipe>, ArrayList<String>> res = new Pair<>(matchingRecipes, matchingRecipeNames);

        return res;
    }
}
