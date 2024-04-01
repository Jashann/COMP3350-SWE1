package com.example.simplicook;
import com.example.simplicook.objects.Ingredient;

import org.junit.Test;
import static org.junit.Assert.*;

public class IngredientTest
{

    @Test
    public void testGetName()
    {
        System.out.println("\nBegin to test Ingredient class's name ");

        // Create an ingredient
        Ingredient ingredient = new Ingredient("Salt", "teaspoon");

        // Test name() method
        assertEquals("Name should be 'Salt'", "Salt", ingredient.name);

        System.out.println("Finished testing Ingredient class's name");
    }

    @Test
    public void testGetUnit() {
        System.out.println("\nBegin to test Ingredient class's unit");

        // Create an ingredient
        Ingredient ingredient = new Ingredient("Sugar", "cup");

        // Test unit() method
        assertEquals("Unit should be 'cup'", "cup", ingredient.unit);

        System.out.println("Finished testing Ingredient class's unit");
    }

    @Test
    public void testSetName() {
        System.out.println("\nBegin to test Ingredient class's setName");

        // Create an ingredient
        Ingredient ingredient = new Ingredient("Flour", "gram");

        // Set new name
        ingredient.name = "Egg";

        // Test name() method
        assertEquals("Name should be 'Egg'", "Egg", ingredient.name);

        System.out.println("Finished testing Ingredient class's name");
    }

    @Test
    public void testSetUnit() {
        System.out.println("\nBegin to test Ingredient class's unit");

        // Create an ingredient
        Ingredient ingredient = new Ingredient("Butter", "tablespoon");

        // Set new unit
        ingredient.unit = "ounce";

        // Test setUnit() method
        assertEquals("Unit should be 'ounce'", "ounce", ingredient.unit);

        System.out.println("Finished testing Ingredient class's setUnit");
    }

    @Test
    public void testToString() {
        System.out.println("\nBegin to test Ingredient class's toString");

        // Create an ingredient
        Ingredient ingredient = new Ingredient("Oil", "milliliter");

        // Test toString() method
        assertEquals("toString should return the expected string", "(name=Oil, unit=milliliter)", ingredient.toString());

        System.out.println("Finished testing Ingredient class's toString");
    }
}
