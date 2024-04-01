package com.example.simplicook.business;

import com.example.simplicook.objects.Recipe;

import java.util.ArrayList;

public interface ISortRecipe
{
    public void sortByRate(ArrayList<Recipe> recipesList);
    public void sortByDifficulty(ArrayList<Recipe> recipesList);
    public void sortByTime(ArrayList<Recipe> recipesList);

    public void sortByFavourites(ArrayList<Recipe> recipesList);
    public void sortManager(String option,ArrayList<Recipe> recipesList);

}
