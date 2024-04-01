package com.example.simplicook.business;

import com.example.simplicook.application.Main;
import com.example.simplicook.application.Services;
import com.example.simplicook.objects.Ingredient;
import com.example.simplicook.objects.Planning;
import com.example.simplicook.objects.Recipe;
import com.example.simplicook.persistence.PlanningPersistence;
import com.example.simplicook.utilities.IdGenerator;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Manages planning operations including the retrieval and management of recipes and ingredients.
 */
public class PlanningManagement {
    // Instance variable for accessing the planning persistence layer.
    private final PlanningPersistence planningDB;

    /**
     * Default constructor that initializes the PlanningPersistence object using the database type defined in the Main class.
     * This allows for dynamic database management based on application settings.
     */
    public PlanningManagement() {
        this.planningDB = Services.getPlanningPersistence(Main.DB_TYPE);
    }

    /**
     * Constructor with persistence flag to initialize PlanningPersistence. It enables initializing
     * with different storage options (e.g., in-memory or database).
     *
     * @param isPersistence Specifies if persistence storage is enabled.
     */
    public PlanningManagement(Boolean isPersistence) {
        planningDB = Services.getPlanningPersistence(isPersistence);
    }


    public PlanningManagement(PlanningPersistence planningPersistence )
    {
        planningDB = planningPersistence;
    }
    /**
     * Retrieves all recipes currently in the planning database.
     *
     * @return An ArrayList of Recipe objects.
     */
    public ArrayList<Recipe> getRecipes() {
        return planningDB.getRecipes();
    }

    /**
     * Removes a specific recipe from the planning based on its ID.
     *
     * @param id The unique identifier of the recipe to be removed.
     */
    public void removeRecipe(int id) {
        planningDB.removeRecipe(id);
    }

    /**
     * Adds a new recipe to the planning using the provided recipe ID.
     *
     * @param recipeID The unique identifier of the recipe to be added.
     */
    public void addRecipe(int recipeID) {
        planningDB.addRecipe(recipeID);
    }



}