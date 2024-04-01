package com.example.simplicook.business.exceptions;

public class MissingIngredientException extends InvalidRecipeException{
    public MissingIngredientException(String errorMessage) {
        super("Not all ingredients are filled in! " + errorMessage);
    }
}
