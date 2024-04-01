package com.example.simplicook.business.exceptions;

public class InvalidRecipeException extends Exception {
    public InvalidRecipeException(String errorMessage) {
        super("Recipe is missing information, cannot update recipe. " + errorMessage);
    }


}
