package com.example.simplicook.objects;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Locale;

public class Planning {

    private final int id;
    private final int recipeID;

    public Planning(int id, int recipeID) {
        this.id = id;
        this.recipeID = recipeID;
    }

    public int getId() {
        return id;
    }

    public int getRecipeID() {
        return recipeID;
    }
}