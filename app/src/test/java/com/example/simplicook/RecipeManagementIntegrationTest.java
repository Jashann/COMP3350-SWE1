
package com.example.simplicook;

import static org.junit.Assert.*;

import com.example.simplicook.application.Services;
import com.example.simplicook.business.RecipeManagement;
import com.example.simplicook.objects.Ingredient;
import com.example.simplicook.objects.Recipe;
import com.example.simplicook.utils.TestUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RecipeManagementIntegrationTest {
    private RecipeManagement recipeManagement;
    private File tempDB;

    @Before
    public void setUp() throws IOException {
        System.out.println("Starting integration test for RecipeManagement");

        this.tempDB = TestUtils.copyDB();
        this.recipeManagement = new RecipeManagement(true);

        assertNotNull(this.recipeManagement);
    }

    @Test
    public void testInsertRecipeForm() {
        System.out.println("\nStarting testInsertRecipeForm");

        int initialSize = recipeManagement.getAllRecipes().size();
        int expectedSize = initialSize + 1;

        List<Ingredient> testIngredientList = new ArrayList<>();
        Ingredient testIngredientOne = new Ingredient("test ingredientOne name", " 1 gram");
        Ingredient testIngredientTwo = new Ingredient("test ingredientTwo name", " 2 gram");
        testIngredientList.add(testIngredientOne);
        testIngredientList.add(testIngredientTwo);

        recipeManagement.insertRecipe("new dish title", "new dish description", "Dinner", "Moderate", testIngredientList, 20);

        assertEquals(expectedSize, recipeManagement.getAllRecipes().size());

        ArrayList<Recipe> allRecipe = recipeManagement.getAllRecipes();
        Recipe newRecipe = allRecipe.get(0);
        for(int i = 0; i < allRecipe.size(); i++) {
            if(allRecipe.get(i).title.equals("new dish title")) {
                newRecipe = allRecipe.get(i);
            }
        }
        assertEquals("new dish title",newRecipe.title);

        System.out.println("Finished testInsertRecipeForm");
    }

    @Test
    public void testInsertRecipeInvalidInformation() {
        System.out.println("\nStarting testInsertRecipeInvalidInformation");

        int initialSize = recipeManagement.getAllRecipes().size();

        List<Ingredient> testIngredientList = new ArrayList<>();
        Ingredient testIngredientOne = new Ingredient("test ingredientOne name", " 1 gram");
        Ingredient testIngredientTwo = new Ingredient("test ingredientTwo name", " 2 gram");
        testIngredientList.add(testIngredientOne);
        testIngredientList.add(testIngredientTwo);
        recipeManagement.insertRecipe("", "new dish description", "Dinner","Easy",testIngredientList,5);
        recipeManagement.insertRecipe("new dish title", "", "Dinner","Easy",testIngredientList,5);
        recipeManagement.insertRecipe("new dish title", "new dish description", "","Easy",testIngredientList,5);
        recipeManagement.insertRecipe("new dish title", "new dish description", "Dinner","",testIngredientList,5);
        recipeManagement.insertRecipe("new dish title", "new dish description", "Dinner","Easy",testIngredientList,-1);

        assertEquals(initialSize, recipeManagement.getAllRecipes().size());

        System.out.println("Finished testInsertRecipeInvalidInformation");
    }

    @Test
    public void testSetRecipeAsFavorite()
    {
        System.out.println("\nStarting testSetRecipeAsFavorite");

        Recipe recipe = recipeManagement.getRecipe(1);
        if(recipe!=null)
        {
            recipe.favourite = false;

            assertFalse("Recipe was not unfavourited.", recipe.favourite);

            recipe.favourite = true;

            assertTrue("Recipe was not set to favourite.", recipe.favourite);
        }

        System.out.println("Finished testSetRecipeAsFavorite");
    }

    @Test
    public void testSetRecipeFeedback()
    {
        System.out.println("\nStarting testSetRecipeFeedback");

        int expected = 2;
        int recipeID = 1;
        recipeManagement.setRecipeFeedback(recipeID,expected);
        int feedback = recipeManagement.getRecipe(recipeID).feedback;

        assertEquals("Feedback should be the same as expected", expected, feedback);
        System.out.println("Finished testSetRecipeFeedback");
    }

    @Test
    public void testDeleteRecipe() {
        System.out.println("\nStarting testDeleteRecipe");

        int initialSize = recipeManagement.getAllRecipes().size();
        int expectedSize = initialSize - 1;

        Recipe deletedRecipe = recipeManagement.getRecipe(1);

        recipeManagement.deleteRecipe(1);
        assertEquals(expectedSize, recipeManagement.getAllRecipes().size());

        for (int i = 0; i < recipeManagement.getAllRecipes().size(); i++) {
            assertNotEquals(recipeManagement.getAllRecipes().get(i),deletedRecipe);
        }

        //Try deleting a non existent recipe
        recipeManagement.deleteRecipe(1);
        assertEquals(expectedSize, recipeManagement.getAllRecipes().size());

        System.out.println("Finished testDeleteRecipe");
    }

    @Test
    public void testEditRecipe()
    {
        System.out.println("\nStarting testEditRecipe");
        int initialSize = recipeManagement.getAllRecipes().size();
        Recipe oldRecipe = recipeManagement.getRecipe(1);

        oldRecipe.difficultyLevel = "Hard";
        List<Ingredient> ingredients = oldRecipe.ingredients;
        ingredients.get(1).name = "Baking SODA";
        ingredients.get(1).unit = "40 tbs";
        recipeManagement.editRecipe(1,oldRecipe.title,oldRecipe.description,oldRecipe.type,oldRecipe.difficultyLevel,oldRecipe.ingredients,oldRecipe.preparationTime);

        assertEquals(initialSize, recipeManagement.getAllRecipes().size());

        Recipe editedRecipe = recipeManagement.getRecipe(1);
        assertNotNull("Recipe no longer exists",editedRecipe);
        assertNotSame("Recipe Did not Change",oldRecipe, editedRecipe);
        assertEquals("Hard",editedRecipe.difficultyLevel);

        List<Ingredient> newIngredients = editedRecipe.ingredients;
        assertEquals("Baking SODA",newIngredients.get(1).name);
        assertEquals("40 tbs",newIngredients.get(1).unit);
        System.out.println("Finished testEditRecipe");
    }

    @Test
    public void testGetRecipe() {
        System.out.println("\nStarting testGetRecipe");
        Recipe targetRecipe = recipeManagement.getRecipe(1);

        assertEquals("Chocolate Chip Cookies", targetRecipe.title);
        assertEquals("Asian", targetRecipe.type);
        assertEquals("Easy",targetRecipe.difficultyLevel);
        assertEquals(30, targetRecipe.preparationTime);
        //Test General Recipe Information
        assertEquals("Chocolate Chip Cookies", targetRecipe.title);
        assertEquals("Asian", targetRecipe.type);
        assertEquals("Easy",targetRecipe.difficultyLevel);
        assertEquals(30, targetRecipe.preparationTime);
        assertEquals("Crisp edges, chewy middles, and so, so easy to make. Try this wildly-popular chocolate chip cookie recipe for yourself.",targetRecipe.description);
        assertEquals(1,targetRecipe.rating);
        assertFalse(targetRecipe.favourite);

        //Test Ingredients
        List<Ingredient> ingredients = targetRecipe.ingredients;
        assertNotNull(targetRecipe.ingredients);
        int numIngredients = targetRecipe.ingredients.size();

        assertEquals(8,numIngredients);
        for(int i = 0; i < numIngredients; i++) {
            assertNotEquals("",ingredients.get(i).name);
            assertNotNull(ingredients.get(i).name);
            assertNotEquals("",ingredients.get(i).unit);
            assertNotNull(ingredients.get(i).unit);
        }

        assertEquals("flour",ingredients.get(0).name);
        assertEquals("1/4 cups",ingredients.get(0).unit);


        System.out.println("Finished testGetRecipe");

    }

    @Test
    public void testGetAllRecipes() {
        System.out.println("\nStarting testGetAllRecipe");

        ArrayList<Recipe> allRecipes = recipeManagement.getAllRecipes();
        assertNotNull(allRecipes);
        assertEquals(4,allRecipes.size());

        Recipe currRecipe;
        for(int i = 0; i < allRecipes.size(); i++) {
            currRecipe = allRecipes.get(i);
            assertNotNull(currRecipe);
        }

        System.out.println("Finished testGetAllRecipes");
    }

    @After
    public void tearDown() {
        System.out.println("Reset database.");
        // reset DB
        this.tempDB.delete();

        // clear Services
        Services.clean();
    }


}
