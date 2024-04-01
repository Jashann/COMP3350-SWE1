package com.example.simplicook;

import static com.example.simplicook.business.SearchManagement.matchKeyword;
import static com.example.simplicook.business.SearchManagement.matchKeywordWithIngredients;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.example.simplicook.application.Services;
import com.example.simplicook.business.RecipeManagement;
import com.example.simplicook.business.SearchManagement;
import com.example.simplicook.business.SortRecipe;

import com.example.simplicook.objects.Recipe;
import com.example.simplicook.utils.TestUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SearchManagementIntegrationTest {

    private RecipeManagement recipeManagement;
    private File tempDB;

    @Before
    public void setUp() throws IOException {
        System.out.println("Starting integration test for SortRecipe");

        this.tempDB = TestUtils.copyDB();

        this.recipeManagement = new RecipeManagement(true);

        assertNotNull(this.recipeManagement);

    }

    @Test
    public void testSearchByText() {
        System.out.println("\nStarting testSearchByText");

        // get a copy of recipes
        ArrayList<Recipe> recipesCopy = recipeManagement.getAllRecipes();

        String keyword = "Chocolate";
        //sort it
        ArrayList<Recipe>  recipesContainChocolate =SearchManagement.searchByText(keyword);

        boolean contains = false;
        //verify rate is sorted high to low
        for (int i = 0; i < recipesContainChocolate.size() - 1; i++) {

            if (    matchKeyword(recipesContainChocolate.get(i).title, keyword) ||
                    matchKeyword(recipesContainChocolate.get(i).type, keyword) ||
                    matchKeyword(recipesContainChocolate.get(i).description, keyword) ||
                    matchKeywordWithIngredients(recipesContainChocolate.get(i).ingredients, keyword)
            )
                contains = true;
            assertTrue("Recipe contain KeyWord Chocolate",contains);

        }

        System.out.println("Finished testSearchByText");
    }

    @Test
    public void testMatchKeywords() {

        System.out.println("\nStarting testMatchKeywords");

        Recipe recipe = recipeManagement.getAllRecipes().get(0);
        System.out.print(recipe.title);

        String title = recipe.title;
        String keyword = "Yummy";
        String expectedTitle = "cookie";
        String expectedIngredient = "flour";

        assertFalse(SearchManagement.matchKeyword(recipe.title,keyword));
        assertTrue(SearchManagement.matchKeyword(recipe.title, expectedTitle));

        assertFalse(SearchManagement.matchKeywordWithIngredients(recipe.ingredients,keyword));
        assertTrue(SearchManagement.matchKeywordWithIngredients(recipe.ingredients,expectedIngredient));

        System.out.println("Finished testMatchKeywords");
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
