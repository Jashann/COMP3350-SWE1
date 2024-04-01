package com.example.simplicook.business;

import android.util.Log;

import com.example.simplicook.application.Main;
import com.example.simplicook.application.Services;
import com.example.simplicook.business.exceptions.InvalidRecipeException;
import com.example.simplicook.objects.Ingredient;
import com.example.simplicook.persistence.RecipePersistence;
import com.example.simplicook.objects.Recipe;
import com.example.simplicook.utilities.IdGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages recipe operations including creation, deletion, and editing of recipes.
 */
public class RecipeManagement
{
    private RecipePersistence recipeDB;

    /**
     * Constructs a RecipeManagement with a specified RecipeID.
     */
    public RecipeManagement() {
        this.recipeDB = Services.getRecipePersistence(Main.DB_TYPE);
    }

    /**
     *  Constructor that initializes RecipePersistence based on a persistence flag.
     *
     * @param isPersistence a flag indicating if persistent storage should be used
     */
    public RecipeManagement(Boolean isPersistence) {
        recipeDB = Services.getRecipePersistence(isPersistence);
    }


    //Initializing a new recipe
    //Used for basic unit tests.
    public void insertRecipe(int id, String title, String description, String type, String difficultyLevel,List<Ingredient> allIngredient, int preparationTime) {
        Recipe newRecipe = new Recipe(id, title, description, type, allIngredient,difficultyLevel,preparationTime);
        try {
            if(RecipeValidator.validRecipe(newRecipe)) {
                recipeDB.insertRecipe(newRecipe);
            }
        } catch (InvalidRecipeException e) {
            Log.e("Missing Recipe Information: ", e.getMessage());
            e.printStackTrace();
        }


    }

    public void insertRecipe(String title, String description, String type, String difficultyLevel,List<Ingredient> allIngredient, int preparationTime) {
        Recipe newRecipe = new Recipe(IdGenerator.generateUniqueId(), title, description, type, allIngredient,difficultyLevel,preparationTime);
        try {
            if(RecipeValidator.validRecipe(newRecipe)) {
                recipeDB.insertRecipe(newRecipe);
            }
        } catch (InvalidRecipeException e) {
            Log.e("Missing Recipe Information: ", e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Deletes a recipe by its ID.
     *
     * @param recipeID the ID of the recipe to delete
     */
    public void deleteRecipe(int recipeID) {
        recipeDB.deleteRecipe(recipeID);
    }

    /**
     * Edits an existing recipe's data with altering its ingredients.
     *
     * @param recipeID    the ID of the recipe to edit
     * @param title       the new title of the recipe
     * @param desc the new description of the recipe
     * @param type        the new type or category of the recipe
     * @param allIngredients  the new ingredients
     */
    public void editRecipe(int  recipeID, String title, String desc, String type, String difficulty, List<Ingredient> allIngredients, int time) {
        Recipe currRecipe = recipeDB.getRecipe(recipeID);
        currRecipe.title = title;
        currRecipe.description = desc;
        currRecipe.difficultyLevel = difficulty;
        currRecipe.ingredients = allIngredients;
        currRecipe.preparationTime = time;
        currRecipe.type = type;

        try {
            if(RecipeValidator.validRecipe(currRecipe)) {
                recipeDB.editRecipe(currRecipe);
            }
        } catch (InvalidRecipeException e) {
            Log.e("Missing Recipe Information: " , e.getMessage());
            e.printStackTrace();
        }

    }

    public void setRecipeAsFavorite(int recipeId, boolean isFavorite) {
        Recipe currentRecipe = getRecipe(recipeId);
        currentRecipe.favourite = isFavorite;
        recipeDB.editRecipe(currentRecipe);
    }

    public void setRecipeFeedback(int recipeId, int feedback) {
        Recipe currentRecipe = getRecipe(recipeId);
        currentRecipe.feedback = feedback;
        recipeDB.editRecipe(currentRecipe);
    }

    /**
     * Retrieves a recipe by its ID.
     *
     * @param recipeID the ID of the recipe to retrieve
     * @return the requested Recipe object, or null if not found
     */
    public Recipe getRecipe(Integer recipeID) {
        return recipeDB.getRecipe(recipeID);
    }

    public ArrayList<Recipe> getAllRecipes() {
        return recipeDB.getRecipes();
    }
}
