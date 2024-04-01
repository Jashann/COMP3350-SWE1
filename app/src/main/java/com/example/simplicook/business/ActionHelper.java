package com.example.simplicook.business;


import com.example.simplicook.objects.Ingredient;
import com.example.simplicook.objects.Recipe;

import java.util.ArrayList;
import java.util.List;

public class ActionHelper {

    private static final RecipeManagement recipeManagement =  new RecipeManagement();

    //Return to recipe we are wanting to act upon.
    public static Recipe getRecipeById(int id) {
        return recipeManagement.getRecipe(id);
    }

    //Request to add a new recipe.
    public static void addRecipe(String title, String desc, String type, String difficulty, List<Ingredient> allIngredients, int time) {
        //Call upon manager for request to add new recipe
        recipeManagement.insertRecipe(title,desc,type,difficulty,allIngredients,time);
    }

    //Request to edit existing recipe.
    public static void editRecipe(int id, String title, String desc, String type, String difficulty, List<Ingredient> allIngredients, int time) {
        //Call upon manager for request to edit recipe
        recipeManagement.editRecipe(id,title,desc,type,difficulty,allIngredients,time);
    }

    //Request to delete existing recipe.
    public static String deleteRecipe(int id) {
        //get name of recipe title for message
        String title = getRecipeTitle(id);
        //Call upon manager for request to delete recipe
        recipeManagement.deleteRecipe(id);
        return title;
    }

    //Request to retrieve recipe name before deletion.
    private static String getRecipeTitle(int id) {
        return recipeManagement.getRecipe(id).title;
    }

    //Check if we are receiving a recipe, if not we are adding a recipe.
    public static boolean editingRecipe(String id) {
        return (id != null);
    }

}
