package com.example.simplicook.utils;

import com.example.simplicook.business.PlanningManagement;
import com.example.simplicook.business.SearchManagement;
import com.example.simplicook.business.RecipeManagement;
import com.example.simplicook.business.SortRecipe;
import com.example.simplicook.objects.Recipe;

import java.util.ArrayList;

public class TestUtils {

    private RecipeManagement rm;
    private PlanningManagement pm;
    private SortRecipe sort;

    public TestUtils() {
        rm = new RecipeManagement();
        pm = new PlanningManagement();
        sort = new SortRecipe();


    }

    public ArrayList<Recipe> plannedRecipeOnDisplay(){
        return pm.getRecipes();
    }

    public int numRecipes() {
        return rm.getAllRecipes().size();
    }



    public ArrayList<Recipe> getAllRecipes() {
//        ArrayList<Recipe> recipes = rm.getAllRecipes();
//        sort.sortByTime(recipes);
        return rm.getAllRecipes();
    }

    public Recipe getRecipePosition(int position) {
        return rm.getAllRecipes().get(position);
    }

}
