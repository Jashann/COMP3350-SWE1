package com.example.simplicook;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import com.example.simplicook.business.RecipeManagement;
import com.example.simplicook.objects.Ingredient;
import com.example.simplicook.objects.Recipe;
import com.example.simplicook.persistence.RecipePersistence;
import com.example.simplicook.persistence.stubs.RecipePersistenceStub;
import com.example.simplicook.utilities.IdGenerator;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RecipeManagementTest {


    private  RecipeManagement recipeManagement ;

    @Before
    public void setup() {
        System.out.println("Starting test for RecipeManagement");
        recipeManagement =  new RecipeManagement(false);
    }

    @Test
    public void insertRecipe_withTitleDescriptionType() {
        // 1. Arrange
        String title = "Test Recipe";
        String description = "Test Description";
        String type = "Test Type";
        int id = IdGenerator.generateUniqueId();

//        ArrayList<Ingredient> ingredients = new ArrayList<>();
//        ingredients.add(new Ingredient("name", "1"));
        // 2. Act
        Ingredient ingredient = new Ingredient("Test","x1");
        ArrayList<Ingredient> ingredientArrayList = new ArrayList<>();
        ingredientArrayList.add(ingredient);
        recipeManagement.insertRecipe(id, title, description, type, "Hard", ingredientArrayList,0);
        Recipe recipe = recipeManagement.getRecipe(id);

        // 3. Assert
        assertEquals("Recipe title should be same", title, recipe.title);
        assertEquals("Recipe description should be same", description, recipe.description);
        assertEquals("Recipe type should be same", type, recipe.type);

    }

    @Test
    public void insertRecipe_withTitleDescriptionType_Ingredients() {
        // 1. Arrange
        String title = "Test Recipe";
        String description = "Test Description";
        String type = "Test Type";
        int id = IdGenerator.generateUniqueId();
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("Bacon", "1 lb"));
        ingredients.add(new Ingredient("Cheese", "0.2 lb"));

        // 2. Act
        recipeManagement.insertRecipe(id, title, description, type,"Easy",ingredients,2);
        Recipe recipe = recipeManagement.getRecipe(id);

        // 3. Assert
        assertEquals("Recipe title should be same", title, recipe.title);
        assertEquals("Recipe description should be same", description, recipe.description);
        assertEquals("Recipe type should be same", type, recipe.type);
        assertEquals("Recipe ingredients should be same", ingredients, recipe.ingredients);
    }

    @Test
    public void insertEmptyRecipe() {
        // 1. Arrange
        int id = IdGenerator.generateUniqueId();
        int initialSize = recipeManagement.getAllRecipes().size();

        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("name", "1"));

        // 2. Act Try Adding Variety of Recipes with Empty Strings Data
        recipeManagement.insertRecipe(id,"","","","Easy",null,0);
        ingredients.add(new Ingredient("Cheese", "0.2 lb"));
        recipeManagement.insertRecipe(id,"Title","Description","","Easy",ingredients,0);
        recipeManagement.insertRecipe(id,"","Description","Type","Easy",ingredients,0);
        recipeManagement.insertRecipe(id,"Title","","Type","Easy",ingredients,0);
        recipeManagement.insertRecipe(id,"Title","Description","Type","Easy",null,0);

        // 3. Assert
        assertEquals(initialSize, recipeManagement.getAllRecipes().size());

    }

    @Test
    public void deleteRecipe() {

        // 1. Arrange
        String title = "Test Recipe";
        String description = "Test Description";
        String type = "Test Type";
        String difficulty = "Easy";
        int time = 5;
        int id = IdGenerator.generateUniqueId();
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("name", "1"));

        List<Recipe> initialList = recipeManagement.getAllRecipes();

        int initialSize = initialList.size();

        // 2. Act
        recipeManagement.insertRecipe(id, title, description, type, difficulty, ingredients,time);
        assertEquals(initialSize+1, recipeManagement.getAllRecipes().size());
        recipeManagement.deleteRecipe(id);

        // 3. Assert
        assertEquals(initialSize, recipeManagement.getAllRecipes().size());

    }

    @Test
    public void editRecipe_withBasicDetails() {
        // 1. Arrange
        String title = "New Title";
        String description = "New Description";
        String type = "New Type";
        String difficulty = "Easy";
        int time = 5;
        int id = IdGenerator.generateUniqueId();

        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("name", "1"));

        // 2. Act
        recipeManagement.insertRecipe(id, title, description, type, difficulty, ingredients,time);

        title = "Changed Title";
        difficulty = "Hard";
        time = 20;
        recipeManagement.editRecipe(id, title, description, type, difficulty, ingredients,time);
        Recipe recipe = recipeManagement.getRecipe(id);

        // 3. Assert
        assertEquals("Recipe title should be same", title, recipe.title);
        assertEquals("Recipe description should be same", description, recipe.description);
        assertEquals("Recipe type should be same", type, recipe.type);
        assertEquals("Recipe difficulty should be same", difficulty, recipe.difficultyLevel);
        assertEquals("Recipe prep time should be same", time, recipe.preparationTime);
    }
//
    @Test
    public void editRecipe_withIngredients() {
        // 1. Arrange
        String title = "New Title";
        String description = "New Description";
        String type = "New Type";
        String difficulty = "Easy";
        int time = 5;
        int id = IdGenerator.generateUniqueId();

        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("name", "1"));

        // 2. Act
        recipeManagement.insertRecipe(id, title, description, type, difficulty, ingredients,time);
        ingredients = new ArrayList<>();
        Ingredient bacon = new Ingredient("Bacon", "1 lb");
        ingredients.add(bacon);
        ingredients.add(new Ingredient("Salt", "0.2 lb"));
        title = "Changed Title";
        recipeManagement.editRecipe(id, title, description, type, difficulty,ingredients, time);
        Recipe recipe = recipeManagement.getRecipe(id);

        // 3. Assert
        assertEquals("Recipe title should be same", title, recipe.title);
        assertEquals("Recipe description should be same", description, recipe.description);
        assertEquals("Recipe type should be same", type, recipe.type);
        assertEquals("Recipe ingredients should be same", ingredients, recipe.ingredients);
        assertTrue(recipe.ingredients.contains(bacon));
    }

}
