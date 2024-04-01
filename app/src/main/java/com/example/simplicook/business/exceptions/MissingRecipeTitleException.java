package com.example.simplicook.business.exceptions;

public class MissingRecipeTitleException extends InvalidRecipeException {
    public MissingRecipeTitleException(String errorMessage) {
        super("Recipe needs a title, please fill in the missing information. " + errorMessage);
    }
}
