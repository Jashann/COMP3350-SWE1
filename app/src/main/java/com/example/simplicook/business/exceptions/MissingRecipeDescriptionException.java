package com.example.simplicook.business.exceptions;

public class MissingRecipeDescriptionException extends InvalidRecipeException {
    public MissingRecipeDescriptionException(String errorMessage) {
        super("Recipe is missing description, please fill in instructions for your recipe. " + errorMessage);
    }
}
