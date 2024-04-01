package com.example.simplicook.utilities;

import com.example.simplicook.objects.Ingredient;
import com.example.simplicook.objects.Planning;
import com.example.simplicook.objects.Recipe;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DatabaseUtilities {
    public static Recipe fromResultSetDBToRecipe(final ResultSet rs, boolean planningTableRecipe) throws SQLException {
        // Handles Planning Table and Recipe table Join
        int recipeID = planningTableRecipe ? rs.getInt("recipe_id") : rs.getInt("id");
        String recipeTitle = rs.getString("title");
        String recipeDescription = rs.getString("description");
        String recipeType = rs.getString("type");
        String recipeDifficultyLevel = rs.getString("difficulty");
        int recipePreparationTime = rs.getInt("cooking_time");
        boolean favourite = rs.getBoolean("favourite");
        int rating = rs.getInt("rating");
        int feedback = rs.getInt("feedback");
        ArrayList<Ingredient> ingredients = IngredientUtils.decodeStringToIngredientsList(rs.getString("ingredients"));
        return new Recipe(recipeID ,recipeTitle, rating, recipeType, recipeDifficultyLevel, recipeDescription, ingredients, recipePreparationTime,  favourite ,feedback) ;
    }


    public static Planning fromResultSetDBToPlanning(final ResultSet rs) throws SQLException {
        int planningID = rs.getInt("id");
        int recipeID   = rs.getInt("recipe_id");
        return new Planning(planningID, recipeID) ;
    }
}
