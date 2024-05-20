package com.example.kitchemate;

public class Recipe {
    private int imageResource;
    private String name;

    public Recipe(int img, String name) {
        this.imageResource = img;
        this.name = name;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getName() {
        return name;
    }

}
