package com.example.simplicook.business.exceptions;

public class MissingRecipeTypeException extends InvalidRecipeException {
    public MissingRecipeTypeException(String errorMessage) {
        super("Recipe needs a type, please fill in a type. Example: 'Breakfast' " + errorMessage);
    }
}
