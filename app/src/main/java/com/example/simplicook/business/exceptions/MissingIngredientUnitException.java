package com.example.simplicook.business.exceptions;

public class MissingIngredientUnitException extends MissingIngredientException {
    public MissingIngredientUnitException(String errorMessage) {
        super("One of the ingredients is missing a quantity or measurement. " + errorMessage);
    }
}
