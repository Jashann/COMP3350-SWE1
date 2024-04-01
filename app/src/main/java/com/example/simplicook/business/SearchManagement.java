package com.example.simplicook.business;

import com.example.simplicook.application.Main;
import com.example.simplicook.application.Services;
import com.example.simplicook.objects.Ingredient;
import com.example.simplicook.objects.Recipe;
import com.example.simplicook.persistence.RecipePersistence;
import com.example.simplicook.persistence.stubs.RecipePersistenceStub;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Provides functionality to search through recipes based on text input.
 * It can search within recipe titles, types, descriptions, and ingredient names.
 *
 * Usage examples:
 *   SearchManagement.searchByText("dressing cucumber parmesan") - fetches pasta & salad recipes.
 *   SearchManagement.searchByText("ssert") - fetches Dessert recipes.
 *   SearchManagement.searchByText("") - fetches all recipes.
 */
public class SearchManagement {
    private static RecipePersistence db = Services.getRecipePersistence(Main.DB_TYPE);

    public SearchManagement(boolean isTesting)
    {
        if (!isTesting)
            db = Services.getRecipePersistence(Main.DB_TYPE);
        else
            db = new RecipePersistenceStub();
    }

    /**
     * Searches for recipes that match the given text input. The search considers the recipe's title,
     * type, description, and ingredients. It avoids duplicate results by using a HashMap.
     *
     * @param text the user input from the search box to match against recipe attributes.
     * @return an ArrayList of Recipe objects that match the search criteria.
     */
    public static ArrayList<Recipe> searchByText(String text) {
        ArrayList<Recipe> recipesArrayList = db.getRecipes();
        ArrayList<Recipe> filteredRecipes = new ArrayList<>();
        String[] keywords = text.toLowerCase().split("\\s+");

        for (Recipe recipe : recipesArrayList) {
            // Concatenate all fields and lower case them for case-insensitive search
            String recipeTitle = recipe.title;
            String recipeType = recipe.type;
            String recipeDescription = recipe.description;
            String ingredientsNamesLower = recipe.ingredients.stream()
                    .map(ingredient -> ingredient.name.toLowerCase())
                    .collect(Collectors.joining(" "));

            String combinedFields = recipeTitle.toLowerCase() + " " + recipeType.toLowerCase() + " "
                    + recipeDescription.toLowerCase() + " " + ingredientsNamesLower;

            // Check if combinedFields contains any of the keywords
            boolean matchesAnyKeyword = Arrays.stream(keywords).anyMatch(combinedFields::contains);

            if (matchesAnyKeyword)
                filteredRecipes.add(recipe);
        }

        return filteredRecipes;
    }



    /**
     * Helper method to check if a keyword is contained within a given string.
     *
     * @param longString the string to search within.
     * @param keyword    the keyword to search for.
     * @return true if the keyword is found in the longString, false otherwise.
     */
    public static boolean matchKeyword(String longString, String keyword) {
        boolean result = false;

        if (longString != null && keyword != null) {
            String longStringLower = longString.toLowerCase();
            result = longStringLower.contains(keyword);
        }

        return result;
    }

    /**
     * Helper method to check if a keyword matches any ingredient names within a list of ingredients.
     *
     * @param ingredients the list of Ingredient objects to search within.
     * @param keyword     the keyword to match against ingredient names.
     * @return true if the keyword is found in any of the ingredient names, false otherwise.
     */
    public static boolean matchKeywordWithIngredients(List<Ingredient> ingredients, String keyword) {
        boolean result = false;

        if (ingredients != null && keyword != null) {
            for (Ingredient ingredient : ingredients) {
                String ingredientName = ingredient.name;
                String ingredientNameLower = ingredientName.toLowerCase();
                if (ingredientNameLower.contains(keyword)) {
                    result = true;
                    break;
                }
            }
        }

        return result;
    }

}
