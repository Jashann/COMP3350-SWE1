package com.example.simplicook;

import static org.junit.Assert.*;

import com.example.simplicook.business.ActionHelper;
import com.example.simplicook.business.RecipeManagement;
import com.example.simplicook.objects.Recipe;
import com.example.simplicook.objects.Ingredient;
import com.example.simplicook.utils.TestUtils;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ActionHelperIntegrationTest {
    private RecipeManagement recipeManagement;
    private File tempDB;

    @Before
    public void setUp() throws IOException {
        System.out.println("Starting integration test for ActionHelper");

        this.tempDB = TestUtils.copyDB();
        this.recipeManagement = new RecipeManagement(true);
        assertNotNull(this.recipeManagement);
    }

    //Testing request to pass recipe to add
    @Test
    public void testActionHelperAddRecipe() {
        System.out.println("\nStarting testInsertWithActionHelper");

        int initialSize = recipeManagement.getAllRecipes().size();
        int expectedSize = initialSize + 1;

        List<Ingredient> testIngredientList = new ArrayList<>();
        Ingredient ingredient = new Ingredient("apple","1");
        testIngredientList.add(ingredient);
        //Recipe recipe = Recipe("new dish title", "new dish description", "Dinner", "Moderate", testIngredientList, 20)
        ActionHelper.addRecipe("new dish title", "new dish description", "Dinner", "Moderate", testIngredientList, 20);
        int newSize =recipeManagement.getAllRecipes().size();
        assertEquals(expectedSize, newSize);

        System.out.println("Finished testInsertWithActionHelper");
    }

    //Testing the request to edit recipe with ActionHelper
    @Test
    public void testActionHelperEditRecipe() {
        System.out.println("\nStarting testActionHelperEditRecipe.");

        int initialSize = recipeManagement.getAllRecipes().size();

        Recipe recipeToEdit = recipeManagement.getRecipe(1);
        String title = "New Title";
        String desc = "New Description";
        String type = "New Type";

        ActionHelper.editRecipe(1, title, desc,type, "Easy",recipeToEdit.ingredients,4);

        Recipe editedRecipe = recipeManagement.getRecipe(1);

        assertEquals(initialSize, recipeManagement.getAllRecipes().size());
        assertEquals(editedRecipe.title, title);
        assertEquals(editedRecipe.description, desc);
        assertEquals(editedRecipe.type, type);
        assertEquals(editedRecipe.difficultyLevel,"Easy");
        assertEquals(editedRecipe.preparationTime,4);

        System.out.println("Finished testActionHelperEditRecipe.");

    }

    //Testing the request to delete recipe with ActionHelper
    @Test
    public void testActionHelperDeleteRecipe() {
        System.out.println("\nStarting testDeleteWithActionHelper");

        int initialSize = recipeManagement.getAllRecipes().size();
        int expectedSize = initialSize - 1;

        String myDeletedRecipeTitle = ActionHelper.deleteRecipe(1);
        assertEquals(expectedSize,recipeManagement.getAllRecipes().size());

        ArrayList<Recipe> currRecipes = recipeManagement.getAllRecipes();
        for(int i = 0; i < recipeManagement.getAllRecipes().size(); i++) {
            assertNotEquals(currRecipes.get(i).title,myDeletedRecipeTitle);
        }

        System.out.println("Finished testDeleteWithActionHelper");
    }

    //Testing getRecipe from ActionHelper
    @Test
    public void testGetRecipeActionHelper() {
        System.out.println("\nStarting testGetRecipeActionHelper");

        Recipe actualRecipe = recipeManagement.getRecipe(1);
        Recipe actionRecipe = ActionHelper.getRecipeById(actualRecipe.getId());
        System.out.print(actionRecipe.title + " " +actualRecipe.title);
        assertNotNull(actionRecipe);

        assertEquals(actualRecipe.title,actionRecipe.title);
        assertEquals(actualRecipe.description,actionRecipe.description);
        assertEquals(actualRecipe.type,actionRecipe.type);
        assertEquals(actualRecipe.difficultyLevel,actionRecipe.difficultyLevel);
        assertEquals(actualRecipe.preparationTime,actionRecipe.preparationTime);
        for(int i = 0; i < actualRecipe.ingredients.size() - 1; i++) {
            assertEquals(actualRecipe.ingredients.get(i).name,actionRecipe.ingredients.get(i).name);
            assertEquals(actualRecipe.ingredients.get(i).unit,actionRecipe.ingredients.get(i).unit);
        }

        System.out.println("Finished testGetRecipeActionHelper");
    }

}