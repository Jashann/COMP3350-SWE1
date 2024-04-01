package com.example.simplicook.business;

import android.content.Context;
import android.widget.Toast;

import com.example.simplicook.business.exceptions.InvalidRecipeException;
import com.example.simplicook.business.exceptions.MissingIngredientException;
import com.example.simplicook.business.exceptions.MissingIngredientNameException;
import com.example.simplicook.business.exceptions.MissingPrepTimeException;
import com.example.simplicook.business.exceptions.MissingRecipeDescriptionException;
import com.example.simplicook.business.exceptions.MissingRecipeTitleException;
import com.example.simplicook.business.exceptions.MissingRecipeTypeException;
import com.example.simplicook.objects.Ingredient;
import com.example.simplicook.objects.Recipe;
import com.example.simplicook.presentation.AddRecipeActivity;

import java.util.Arrays;
import java.util.List;

public class RecipeValidator {

    // Set the context
    public static boolean validRecipe(Recipe recipe) throws InvalidRecipeException {
        boolean isTitleValid = validateTitle(recipe.title);
        boolean isDescriptionValid = validateDesc(recipe.description);
        boolean isTypeValid = validateType(recipe.type);
        boolean isPreparationTimeValid = validatePreparationTime(recipe.preparationTime);
        boolean isDifficultyLevelValid = validateDifficultyLevel(recipe.difficultyLevel);
        boolean areIngredientsValid = validateIngredients(recipe.ingredients);

        return isTitleValid && isDescriptionValid && isTypeValid &&
                isPreparationTimeValid && isDifficultyLevelValid && areIngredientsValid;
    }

    public static boolean validateField(String str) {
        return !(str == null || str.isEmpty());
    }

    private static boolean validateTitle(String title) throws MissingRecipeTitleException {
        return validateField(title);
    }

    private static boolean validateType(String type) throws MissingRecipeTypeException {
        return validateField(type);
    }

    private static boolean validateDesc(String desc) throws MissingRecipeDescriptionException  {
        return validateField(desc);
    }

    public static boolean validateIngredient(Ingredient ingredient) throws MissingIngredientException {
        return validateIngredientName(ingredient.name) && validateIngredientUnit(ingredient.unit);
    }

    private static boolean validateIngredientName(String name) throws MissingIngredientNameException {
        return validateField(name);
    }

    private static boolean validateIngredientUnit(String unit) throws MissingIngredientNameException {
        return validateField(unit);
    }

    private static boolean validatePreparationTime(int preparationTime) throws MissingPrepTimeException {
        return preparationTime >= 0;
    }

    private static boolean validateDifficultyLevel(String difficultyLevel) {
        List<String> validLevels = Arrays.asList("Easy", "Hard", "Moderate");
        return validLevels.contains(difficultyLevel);
    }

    private static boolean validateIngredients(List<Ingredient> ingredients) throws MissingIngredientException {
        return ingredients != null && !ingredients.isEmpty();
    }



}
