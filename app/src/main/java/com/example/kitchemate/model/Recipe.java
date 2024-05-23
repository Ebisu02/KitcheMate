package com.example.kitchemate.model;

import com.google.gson.annotations.SerializedName;

public class Recipe {
    @SerializedName("Id")
    private int id;
    @SerializedName("Name")
    private String name;
    @SerializedName("Ingredients")
    private String ingredients;
    @SerializedName("HowTo")
    private String howTo;
    @SerializedName("ImagePath")
    private String imagePath;

    // Constructors, getters, and setters
    public Recipe(int id, String name, String ingredients, String howTo, String imagePath) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.howTo = howTo;
        this.imagePath = imagePath;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getHowTo() {
        return howTo;
    }

    public String getImagePath() {
        return imagePath;
    }
}