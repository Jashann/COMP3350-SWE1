package com.example.simplicook.persistence;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.simplicook.objects.Recipe;

public interface RecipePersistence {

    void insertRecipe(Recipe currentRecipe);

    void deleteRecipe(int id);

    void editRecipe(Recipe currentRecipe);

    Recipe getRecipe(int id);

    ArrayList<Recipe> getRecipes();

    int getSize();

    void clearDB(String clear);
}
