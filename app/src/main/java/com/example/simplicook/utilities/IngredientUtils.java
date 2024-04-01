package com.example.simplicook.utilities;

import com.example.simplicook.objects.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class IngredientUtils {

    private static final String OBJECT_DELIMITER = ";;"; // Delimiter between objects
    private static final String FIELD_DELIMITER = "::";  // Delimiter between fields of an object

    public static String encodeIngredientsListToString(List<Ingredient> ingredientsList) {
        StringBuilder encoded = new StringBuilder();
        for (Ingredient ingredient : ingredientsList) {
            if (encoded.length() > 0) {
                encoded.append(OBJECT_DELIMITER);
            }
            encoded.append(ingredient.name).append(FIELD_DELIMITER).append(ingredient.unit);
        }
        return encoded.toString();
    }

    public static ArrayList<Ingredient> decodeStringToIngredientsList(String encodedIngredients) {
        ArrayList<Ingredient> ingredientsList = new ArrayList<>();
        String[] ingredientsArray = encodedIngredients.split(OBJECT_DELIMITER);
        for (String ingredientStr : ingredientsArray) {
            String[] fields = ingredientStr.split(FIELD_DELIMITER);
            if (fields.length == 2) {
                ingredientsList.add(new Ingredient(fields[0], fields[1]));
            }
        }
        return ingredientsList;
    }
}

