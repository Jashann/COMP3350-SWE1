package com.example.simplicook.persistence.utils;

import com.example.simplicook.objects.Recipe;

import java.util.ArrayList;
import java.util.HashMap;

public class RecipeUtils {

    public static ArrayList<Recipe> getRecipesFromHashSet(HashMap<Integer, Recipe> recipes) {
        ArrayList<Recipe> recipeArrayList = new ArrayList<>();

        for (Integer key : recipes.keySet())
            recipeArrayList.add(recipes.get(key));

        return recipeArrayList;
    }
}
