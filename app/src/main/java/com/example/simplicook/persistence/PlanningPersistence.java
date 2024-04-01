package com.example.simplicook.persistence;

import com.example.simplicook.objects.Planning;
import com.example.simplicook.objects.Recipe;

import java.util.ArrayList;

public interface PlanningPersistence {
    ArrayList<Recipe> getRecipes();
    void removeRecipe(int recipeID);
    void addRecipe(int recipeID);
}
