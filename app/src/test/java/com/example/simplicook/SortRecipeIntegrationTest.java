package com.example.simplicook;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.example.simplicook.application.Main;
import com.example.simplicook.application.Services;
import com.example.simplicook.business.ISortRecipe;
import com.example.simplicook.business.RecipeManagement;
import com.example.simplicook.business.SortRecipe;
import com.example.simplicook.objects.Recipe;
import com.example.simplicook.persistence.RecipePersistence;
import com.example.simplicook.persistence.hsqldb.RecipePersistenceHSQLDB;
import com.example.simplicook.utils.TestUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SortRecipeIntegrationTest {
    private ISortRecipe sortRecipe;
    private RecipeManagement recipeManagement;
    private File tempDB;

    @Before
    public void setUp() throws IOException {
        System.out.println("Starting integration test for SortRecipe");

        this.tempDB = TestUtils.copyDB();

        this.recipeManagement = new RecipeManagement(true);
        this.sortRecipe = new SortRecipe();

        assertNotNull(this.recipeManagement);
        assertNotNull(this.sortRecipe);
    }

    @Test
    public void testSortByRate() {
        System.out.println("\nStarting testSortByRate");

        // get a copy of recipes
        ArrayList<Recipe> recipesCopy = recipeManagement.getAllRecipes();

        //sort it
        sortRecipe.sortByRate(recipesCopy);

        //verifiy rate is sorted high to low
        for (int i = 0; i < recipesCopy.size() - 1; i++) {
            int currentRate = recipesCopy.get(i).rating;
            int nextRate = recipesCopy.get(i + 1).rating;
            assertTrue("Sort rate high to low",currentRate-nextRate>=0);
        }

        System.out.println("Finished testSortByRate");
    }

    @Test
    public void testSortByDifficulty() {
        System.out.println("\nStarting testSortByDifficulty");

        // Get a copy of recipes
        ArrayList<Recipe> recipesCopy = recipeManagement.getAllRecipes();

        // Sort it
        sortRecipe.sortByDifficulty(recipesCopy);

        // Verify that recipes are sorted from easy to hard
        for (int i = 0; i < recipesCopy.size() - 1; i++) {
            String currentDifficulty = recipesCopy.get(i).difficultyLevel;
            String nextDifficulty = recipesCopy.get(i + 1).difficultyLevel;
            assertTrue("Sort difficulty from Easy to Modertate to Hard",
                    compareDifficulty(currentDifficulty, nextDifficulty) <= 0);
        }

        System.out.println("Finished testSortByDifficulty");
    }

    // Helper method to compare difficulty levels
    private int compareDifficulty(String difficulty1, String difficulty2) {
        List<String> difficultyOrder = Arrays.asList("Easy", "Moderate", "Hard");
        int index1 = difficultyOrder.indexOf(difficulty1);
        int index2 = difficultyOrder.indexOf(difficulty2);
        return Integer.compare(index1, index2);
    }

    @Test
    public void testSortByTime() {
        System.out.println("\nStarting testSortByTime");

        // get a copy of recipes
        ArrayList<Recipe> recipesCopy = recipeManagement.getAllRecipes();

        //sort it
        sortRecipe.sortByTime(recipesCopy);

        //verify rate is sorted high to low
        for (int i = 0; i < recipesCopy.size() - 1; i++) {
            int currentTime = recipesCopy.get(i).preparationTime;
            int nextTime = recipesCopy.get(i + 1).preparationTime;
            assertTrue("Sort time Low to High",currentTime-nextTime<=0);
        }

        System.out.println("Finished testSortByTime");
    }

    @Test
    public void testSortByFavourite() {
        System.out.println("\nStarting testSortByFavourite");

        ArrayList<Recipe> recipesCopy = recipeManagement.getAllRecipes();

        //American spelling sus
        recipesCopy.get(1).favourite = true;
        recipesCopy.get(2).favourite = true;

        sortRecipe.sortByFavourites(recipesCopy);

        int count = 0;
        for(int i = 0; i < recipesCopy.size(); i++) {
            assertTrue("Recipe is not favourite:",recipesCopy.get(i).favourite);
            count++;
        }
        assertEquals(2,count);

        System.out.println("Finished testSortByFavourite");
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
