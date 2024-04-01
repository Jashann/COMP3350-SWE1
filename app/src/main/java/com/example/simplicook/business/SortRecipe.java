package com.example.simplicook.business;


import com.example.simplicook.objects.Recipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortRecipe implements ISortRecipe
{
    @Override
    public void sortByRate(ArrayList<Recipe> recipesList) {
        Collections.sort(recipesList, new Comparator<Recipe>() {
            @Override
            public int compare(Recipe recipe1, Recipe recipe2) {
                return Float.compare(recipe2.rating, recipe1.rating);
            }
        });
    }


    @Override
    public void sortByDifficulty(ArrayList<Recipe> recipesList) {
        Collections.sort(recipesList, new Comparator<Recipe>() {
            @Override
            public int compare(Recipe recipe1, Recipe recipe2) {
                // Get the difficulty level strings for the two recipes
                String difficulty1 = recipe1.difficultyLevel;
                String difficulty2 = recipe2.difficultyLevel;

                // Define the order of difficulty levels
                List<String> difficultyOrder = Arrays.asList("Easy", "Moderate", "Hard");

                // Get the indices of the difficulty levels in the order list
                int index1 = difficultyOrder.indexOf(difficulty1);
                int index2 = difficultyOrder.indexOf(difficulty2);

                // Compare the indices to determine the order
                return Integer.compare(index1, index2);
            }
        });
    }


    @Override
    public void sortByTime(ArrayList<Recipe> recipesList) {
        Collections.sort(recipesList, new Comparator<Recipe>() {
            @Override
            public int compare(Recipe recipe1, Recipe recipe2) {
                return Float.compare(recipe1.preparationTime, recipe2.preparationTime);
            }
        });
    }

    @Override
    public void sortByFavourites(ArrayList<Recipe> recipesList) {
        recipesList.removeIf(recipe -> !recipe.favourite);
    }


    @Override
    public void sortManager(String selectedOption,ArrayList<Recipe> recipesList) {

        switch (selectedOption) {
            case "Lowest prepare time to Highest prepare time":
                sortByTime(recipesList);
                break;
            case "Highest rating to lowest rating":
                sortByRate(recipesList);
                break;
            case "Easy to Hard":
                sortByDifficulty(recipesList);
                break;
            case "Favourited":
                sortByFavourites(recipesList);
                break;

        }
    }
}
