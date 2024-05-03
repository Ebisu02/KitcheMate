package com.example.kitchemate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<String> recipeNames;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        copyDatabase(this);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listViewRecipes);
        recipeNames = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, recipeNames);


        listView.setAdapter(adapter);

        fetchRecipes();
    }

    private void fetchRecipes() {
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor uCursor = db.rawQuery("SELECT Name FROM Recipes", null);

        if (uCursor.moveToFirst()) {
            do {
                String recipeName = uCursor.getString(0);
                recipeNames.add(recipeName);
            } while (uCursor.moveToNext());
        } else {
            Toast.makeText(this, "RecipesNotFound", Toast.LENGTH_SHORT).show();
        }

        uCursor.close();
        adapter.notifyDataSetChanged();
        db.close();
    }

    private void copyDatabase(Context context) {
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
}