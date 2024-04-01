package com.example.simplicook.persistence.hsqldb;

import android.util.Log;

import com.example.simplicook.objects.Recipe;
import com.example.simplicook.persistence.RecipePersistence;
import com.example.simplicook.persistence.utils.RecipeUtils;
import com.example.simplicook.utilities.DatabaseUtilities;
import com.example.simplicook.utilities.IngredientUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Implementation of RecipePersistence interface for HSQLDB.
 * Manages the CRUD operations for recipes in an HSQLDB database.
 */
public class RecipePersistenceHSQLDB implements RecipePersistence {

    private final String dbPath;
    private static HashMap<Integer, Recipe> recipes = null;

    /**
     * Constructor to initialize database path and load recipes from the database into the cache.
     * @param dbPath The filesystem path to the HSQLDB database file.
     */
    public RecipePersistenceHSQLDB(String dbPath) {
        this.dbPath = dbPath;
        recipes = new HashMap<>();
        loadRecipes();
    }

    /**
     * Establishes and returns a connection to the HSQLDB database.
     * @return A Connection object to the HSQLDB database.
     * @throws SQLException if a database access error occurs.
     */
    private Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    /**
     * Loads all recipes from the HSQLDB database into the local cache.
     * This method is called during initialization and after any modification to the database.
     */
    private void loadRecipes() {
        try (Connection connection = connect()) {
            final Statement statement = connection.createStatement();
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM PUBLIC.RECIPES");
            recipes = new HashMap<>();

            while (resultSet.next()) {
                final Recipe recipe = DatabaseUtilities.fromResultSetDBToRecipe(resultSet, false);
                recipes.put(recipe.getId(), recipe);
            }
        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }
    }


    /**
     * Inserts a new recipe into the HSQLDB database.
     * @param currentRecipe The recipe object to be inserted into the database.
     */
    @Override
    public void insertRecipe(Recipe currentRecipe) {
        final String insertSQL = "INSERT INTO PUBLIC.RECIPES VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(insertSQL)) { // PreparedStatement is now a resource in the try-with-resources
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            statement.setInt(1, currentRecipe.getId());
            statement.setString(2, currentRecipe.title);
            statement.setInt(3, currentRecipe.rating);
            statement.setString(4, currentRecipe.type);
            statement.setString(5, currentRecipe.difficultyLevel);
            statement.setString(6, currentRecipe.description);
            statement.setString(7, IngredientUtils.encodeIngredientsListToString(currentRecipe.ingredients));
            statement.setInt(8, currentRecipe.preparationTime);
            statement.setInt(9, 0);
            statement.setTimestamp(10, currentTimestamp);
            statement.setInt(11, currentRecipe.feedback);
            statement.executeUpdate();
        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }
    }

    /**
     * Edits an existing recipe in the HSQLDB database.
     * @param currentRecipe The updated recipe object.
     */
    @Override
    public void editRecipe(Recipe currentRecipe) {
        try (Connection connection = connect()) {
            int recipeId = currentRecipe.getId();
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            final PreparedStatement statement = connection.prepareStatement("UPDATE PUBLIC.RECIPES SET title = ?, rating = ?, type = ?, difficulty = ?, description = ?, ingredients = ?, cooking_time = ?, favourite = ?, creation_date = ?, feedback = ? WHERE id = ?");
            statement.setString(1, currentRecipe.title);
            statement.setInt(2, currentRecipe.rating);
            statement.setString(3, currentRecipe.type);
            statement.setString(4, currentRecipe.difficultyLevel);
            statement.setString(5, currentRecipe.description);
            statement.setString(6, IngredientUtils.encodeIngredientsListToString(currentRecipe.ingredients));
            statement.setInt(7, currentRecipe.preparationTime);
            statement.setBoolean(8, currentRecipe.favourite);
            statement.setTimestamp(9, currentTimestamp);
            statement.setInt(10, currentRecipe.feedback);
            // Set the ID parameter for the WHERE clause to specify which recipe to update
            statement.setInt(11, recipeId);
            statement.executeUpdate();
            loadRecipes(); // Assuming this method refreshes the list of recipes
        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }
    }

    /**
     * Deletes a recipe from the HSQLDB database by its ID.
     * @param recipeID The ID of the recipe to delete.
     */
    @Override
    public void deleteRecipe(int recipeID) {
        try (Connection connection = connect()) {
            final PreparedStatement sc = connection.prepareStatement("DELETE FROM PUBLIC.RECIPES WHERE id = ?");
            sc.setInt(1, recipeID);
            sc.executeUpdate();
        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }
    }

    /**
     * Fetches a single recipe by its ID from the local cache.
     * @param id The ID of the recipe to retrieve.
     * @return The recipe object if found, null otherwise.
     */
    @Override
    public Recipe getRecipe(int id) {
        loadRecipes();
        return recipes.get(id);
    }

    /**
     * Returns a list of all recipes from the local cache.
     * @return An ArrayList of Recipe objects.
     */
    public ArrayList<Recipe> getRecipes() {
        loadRecipes();
        return RecipeUtils.getRecipesFromHashSet(recipes);
    }


    public int getSize() {
        loadRecipes();
        return recipes.size();
    }


    // Clear all recipes from the Database
    // Usage: clearDB("CLEAR");
    // WARNING: DON'T USE IT OUTSIDE OF TESTING
    public void clearDB(String clear) {
        if (clear.equals("CLEAR")) // safety check
            recipes.clear();
    }
}
