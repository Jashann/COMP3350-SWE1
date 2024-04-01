package com.example.simplicook;

import org.junit.Test;
import static org.junit.Assert.*;

import com.example.simplicook.objects.Ingredient;
import com.example.simplicook.objects.Recipe;
import com.example.simplicook.utilities.IdGenerator;

import java.util.ArrayList;

public class  RecipeTest {

    @Test
    public void testSetTitle() {
        System.out.println("\nBegin to test Recipe class's setTitle");

        // Create a recipe
        Recipe recipe = new Recipe(IdGenerator.generateUniqueId(),"Original Title", "Original Description", "Original Type");

        // Set new title
        recipe.title = "New Title";

        // Test setTitle() method
        assertEquals("Title should be 'New Title'", "New Title", recipe.title);

        System.out.println("Finished testing Recipe class's setTitle");
    }

    @Test
    public void testSetDescription() {
        System.out.println("\nBegin to test Recipe class's setDescription");

        // Create a recipe
        Recipe recipe = new Recipe(IdGenerator.generateUniqueId(), "Original Title", "Original Description", "Original Type");

        // Set new description
        recipe.description = "New Description";

        // Test setDescription() method
        assertEquals("Description should be 'New Description'", "New Description", recipe.description);

        System.out.println("Finished testing Recipe class's setDescription");
    }

    @Test
    public void testSetType() {
        System.out.println("\nBegin to test Recipe class's setType");

        // Create a recipe
        Recipe recipe = new Recipe(IdGenerator.generateUniqueId(), "Original Title", "Original Description", "Original Type");

        // Set new type
        recipe.type = "New Type";

        // Test setType() method
        assertEquals("Type should be 'New Type'", "New Type", recipe.type);

        System.out.println("Finished testing Recipe class's setType");
    }

    @Test
    public void testSetIngredients() {
        System.out.println("\nBegin to test Recipe class's setIngredients");

        // Create ingredients
        Ingredient ing1 = new Ingredient("Ingredient1", "100g");
        Ingredient ing2 = new Ingredient("Ingredient2", "200g");
        ArrayList<Ingredient> newIngredients = new ArrayList<>();
        newIngredients.add(ing1);
        newIngredients.add(ing2);

        // Create a recipe
        Recipe recipe = new Recipe(IdGenerator.generateUniqueId(), "Original Title", "Original Description", "Original Type");

        // Set new ingredients
        recipe.ingredients = newIngredients;

        // Test setIngredients() method
        assertEquals("Number of ingredients should be as expected", newIngredients.size(), recipe.ingredients.size());
        assertEquals("First ingredient should be set correctly", ing1, recipe.ingredients.get(0));
        assertEquals("Second ingredient should be set correctly", ing2, recipe.ingredients.get(1));

        System.out.println("Finished testing Recipe class's setIngredients");
    }
}
