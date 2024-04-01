package com.example.simplicook.objects;

import androidx.annotation.NonNull;

public class Ingredient {
    //Ingredient have name and unit , for example, salt , x1 teaspoon
    public String name;
    public String unit;

    //constructor
    public Ingredient() {
        this.name = "";
        this.unit = "";
    }

    public Ingredient(String name, String unit) {
        this.name = name;
        this.unit = unit;
    }


    @NonNull
    public String toString() {
        return String.format("(name=%s, unit=%s)", name, unit);
    }
}
