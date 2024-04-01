package com.example.simplicook.business.exceptions;

public class MissingIngredientNameException extends MissingIngredientException {
    public MissingIngredientNameException(String errorMessage) {
        super("One of the ingredients is missing a name. " + errorMessage);
    }
}
