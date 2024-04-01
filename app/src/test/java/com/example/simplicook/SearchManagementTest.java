

package com.example.simplicook;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.example.simplicook.business.RecipeManagement;
import com.example.simplicook.business.SearchManagement;
import com.example.simplicook.objects.Ingredient;
import com.example.simplicook.objects.Recipe;
import com.example.simplicook.persistence.RecipePersistence;
import com.example.simplicook.persistence.stubs.RecipePersistenceStub;
import com.example.simplicook.utilities.IdGenerator;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class SearchManagementTest {
    private final RecipePersistence recipeDB = new RecipePersistenceStub(true);
    private final RecipeManagement recipeManagement = new RecipeManagement(false);

    // setUp is used for clean set up.
    @Before
    public void setUp() {
        // Initialize with a some sample database before each test
        recipeDB.clearDB("CLEAR");

        // Sandwich Recipe
        ArrayList<Ingredient> sandwichIngredients = new ArrayList<>();
        sandwichIngredients.add(new Ingredient("bacon", "1 pound"));
        sandwichIngredients.add(new Ingredient("lettuce", "3 leaves"));
        sandwichIngredients.add(new Ingredient("tomato", "2"));
        sandwichIngredients.add(new Ingredient("bread", "3"));
        String sandwichSteps = "1. Cook bacon.\n2. Arrange lettuce, tomato, and bacon between slices of bread.";
        recipeManagement.insertRecipe("Sandwich", sandwichSteps, "Lunch", "Easy", sandwichIngredients, 4);

        // Pasta Recipe
        ArrayList<Ingredient> pastaIngredients = new ArrayList<>();
        pastaIngredients.add(new Ingredient("pasta", "250 grams"));
        pastaIngredients.add(new Ingredient("tomato sauce", "1 cup"));
        pastaIngredients.add(new Ingredient("garlic", "2 cloves"));
        pastaIngredients.add(new Ingredient("parmesan cheese", "1/2 cup"));
        String pastaSteps = "1. Cook pasta.\n2. Saute garlic, add tomato sauce.\n3. Mix with cooked pasta.\n4. Sprinkle parmesan cheese.";
        recipeManagement.insertRecipe("Pasta", pastaSteps, "Dinner","Moderate" ,pastaIngredients, 20);
    }

    @Test
    public void insertRecipe_withTitleDescriptionType() {
        // 1. Arrange
        // Already done in setUp()
        SearchManagement searchManagement = new SearchManagement(true);

        // 2. Act
        ArrayList<Recipe> recipes = SearchManagement.searchByText(""); // Should return all
        ArrayList<Recipe> recipes2 = SearchManagement.searchByText("parmesan"); // Should contain only pasta recipe (ingredient: parmesan)
        ArrayList<Recipe> recipes3 = SearchManagement.searchByText("wich dinner"); // Should contain both (1: title=sandwich 2: type=dinner)
        ArrayList<Recipe> recipes4 = SearchManagement.searchByText("IAMNOTHERE"); // Should return empty list

        // 3. Assert
        assertEquals("This should be all recipes (being 2) in this case",2, recipes.size());
        assertEquals("This should be only pasta recipe",1, recipes2.size());
        assertEquals("This should be only pasta recipe","Pasta", recipes2.get(0).title);
        assertEquals("This should be both (Sandwich & Pasta)",2, recipes3.size());
        assertEquals("This should be empty list",0, recipes4.size());

    }

}
