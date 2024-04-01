package com.example.simplicook.persistence.hsqldb;

import android.util.Log;

import com.example.simplicook.objects.Planning;
import com.example.simplicook.objects.Recipe;
import com.example.simplicook.persistence.PlanningPersistence;
import com.example.simplicook.persistence.utils.RecipeUtils;
import com.example.simplicook.utilities.DatabaseUtilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Implementation of PlanningPersistence interface for HSQLDB.
 * Handles the database operations related to planning, such as loading and managing recipes within plans.
 */
public class PlanningPersistenceHSQLDB implements PlanningPersistence {

    private final String dbPath;
    private static HashMap<Integer, Recipe> recipes = null;
    private static ArrayList<Planning> planningArrayList = null;

    /**
     * Constructor that initializes the database path and loads recipes from the database.
     * @param dbPath The filesystem path to the HSQLDB database file.
     */
    public PlanningPersistenceHSQLDB(String dbPath) {
        this.dbPath = dbPath;
        recipes = new HashMap<>();
        loadRecipes();
    }

    /**
     * Establishes and returns a connection to the HSQLDB database.
     * @return A Connection object to the database.
     * @throws SQLException if a database access error occurs.
     */
    private Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    /**
     * Loads all recipes from the HSQLDB database into the local cache by joining PLANNING and RECIPES tables.
     */
    private void loadRecipes() {
        try (Connection connection = connect()) {
            final Statement statement = connection.createStatement();
            final String query = "SELECT * FROM PUBLIC.PLANNING INNER JOIN PUBLIC.RECIPES ON PUBLIC.PLANNING.recipe_id = PUBLIC.RECIPES.id";
            final ResultSet resultSet = statement.executeQuery(query);
            recipes = new HashMap<>();

            while (resultSet.next()) {
                final Recipe recipe = DatabaseUtilities.fromResultSetDBToRecipe(resultSet, true);
                recipes.put(recipe.getId(), recipe);
            }
        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }
    }

    /**
     * Loads all planning entries from the database into a local ArrayList.
     */
    private void loadPlannings() {
        try (Connection connection = connect()) {
            final Statement statement = connection.createStatement();
            final String query = "SELECT * FROM PUBLIC.PLANNING";
            final ResultSet resultSet = statement.executeQuery(query);
            planningArrayList = new ArrayList<>();

            while (resultSet.next()) {
                final Planning planning = DatabaseUtilities.fromResultSetDBToPlanning(resultSet);
                planningArrayList.add(planning);
            }
        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }
    }

    /**
     * Retrieves all recipes involved in planning.
     * @return An ArrayList of Recipe objects.
     */
    public ArrayList<Recipe> getRecipes() {
        loadRecipes();
        return RecipeUtils.getRecipesFromHashSet(recipes);
    }

    /**
     * Retrieves all planning entries.
     * @return An ArrayList of Planning objects.
     */
    public ArrayList<Planning> getPlannings() {
        loadPlannings();
        return planningArrayList;
    }

    /**
     * Removes a recipe from planning based on its ID.
     * @param id The ID of the recipe to remove.
     */
    @Override
    public void removeRecipe(int id) {
        try (Connection connection = connect()) {
            final PreparedStatement ps = connection.prepareStatement("DELETE FROM PUBLIC.PLANNING WHERE recipe_id = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }
    }


    @Override
    public void addRecipe(int recipeID) {
        try (Connection connection = connect()) {
        // Don't add a duplicate recipe to planning if it's already in planning
            if (!recipeAlreadyExistsInPlanning(recipeID)) {
                final PreparedStatement statement = connection.prepareStatement("INSERT INTO PUBLIC.PLANNING (recipe_id) VALUES (?)");
                statement.setInt(1, recipeID);
                statement.executeUpdate();
            }
        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }
    }


    private boolean recipeAlreadyExistsInPlanning(int recipeID) {
        boolean alreadyExists = false;
        ArrayList<Recipe> recipeArrayList = getRecipes();

        // check if already in the database
        for (int i=0; i<recipeArrayList.size() && !alreadyExists; i++) {
            Recipe currentRecipe = recipeArrayList.get(i);
            if (currentRecipe.getId() == recipeID)
                alreadyExists = true;
        }

        return alreadyExists;
    }

}
