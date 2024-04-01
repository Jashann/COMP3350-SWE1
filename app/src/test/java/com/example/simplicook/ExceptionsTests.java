package com.example.simplicook;

import static org.junit.Assert.assertTrue;

import com.example.simplicook.business.exceptions.InvalidRecipeException;
import com.example.simplicook.business.exceptions.MissingIngredientException;
import com.example.simplicook.business.exceptions.MissingIngredientNameException;
import com.example.simplicook.business.exceptions.MissingIngredientUnitException;
import com.example.simplicook.business.exceptions.MissingPrepTimeException;
import com.example.simplicook.business.exceptions.MissingRecipeDescriptionException;
import com.example.simplicook.business.exceptions.MissingRecipeTitleException;
import com.example.simplicook.business.exceptions.MissingRecipeTypeException;

import org.junit.Test;

public class ExceptionsTests {

    @Test
    public void testMissingIngredientException() {
        System.out.println("\nStarting testMissingIngredientException");
        boolean caught = false;

        try {
            throw new MissingIngredientException("Missing ingredients");
        } catch (MissingIngredientException e){
            caught = true;
        }
        assertTrue(caught);
        System.out.println("Finished testMissingIngredientException");
    }


    @Test
    public void testMissingIngredientNameException() {
        System.out.println("\nStarting testMissingIngredientNameException");
        boolean caught = false;

        try {
            throw new MissingIngredientNameException("Missing ingredients name");
        } catch (MissingIngredientNameException e){
            caught = true;
        }
        assertTrue(caught);
        System.out.println("Finished testMissingIngredientNameException");
    }

    @Test
    public void testMissingIngredientUnitException() {
        System.out.println("\nStarting testMissingIngredientUnitException");
        boolean caught = false;

        try {
            throw new MissingIngredientUnitException("Missing ingredients unit");
        } catch (MissingIngredientUnitException e){
            caught = true;
        }
        assertTrue(caught);
        System.out.println("Finished testMissingIngredientUnitException");
    }

    @Test
    public void testMissingPrepTimeException() {
        System.out.println("\nStarting testMissingIngredientUnitException");
        boolean caught = false;

        try {
            throw new MissingPrepTimeException("Missing prep time");
        } catch (MissingPrepTimeException e){
            caught = true;
        }
        assertTrue(caught);
        System.out.println("Finished testMissingIngredientUnitException");
    }

    @Test
    public void testMissingRecipeDescriptionException() {
        System.out.println("\nStarting testMissingRecipeDescriptionException");
        boolean caught = false;

        try {
            throw new MissingRecipeDescriptionException("Missing description");
        } catch (MissingRecipeDescriptionException e){
            caught = true;
        }
        assertTrue(caught);
        System.out.println("Finished testMissingRecipeDescriptionException");
    }

    @Test
    public void testMissingRecipeTitleException() {
        System.out.println("\nStarting testMissingRecipeTitleException");
        boolean caught = false;

        try {
            throw new MissingRecipeTitleException("Missing title");
        } catch (MissingRecipeTitleException e){
            caught = true;
        }
        assertTrue(caught);
        System.out.println("Finished testMissingRecipeTitleException");
    }

    @Test
    public void testMissingRecipeTypeException() {
        System.out.println("\nStarting testMissingRecipeTypeException");
        boolean caught = false;

        try {
            throw new MissingRecipeTypeException("Missing type");
        } catch (MissingRecipeTypeException e){
            caught = true;
        }
        assertTrue(caught);
        System.out.println("Finished testMissingRecipeTypeException");
    }

    @Test
    public void testInvalidRecipeException() {
        System.out.println("\nStarting testInvalidRecipeException");
        boolean caught = false;

        try {
            throw new InvalidRecipeException("Invalid recipe");
        } catch (InvalidRecipeException e){
            caught = true;
        }
        assertTrue(caught);
        System.out.println("Finished testInvalidRecipeException");
    }


}
