package com.example.simplicook.business.exceptions;

public class MissingPrepTimeException extends InvalidRecipeException {
    public MissingPrepTimeException(String errorMessage) {
        super("Prep time missing. Enter a time it takes to prepare your meal. " +  errorMessage);
    }
}
