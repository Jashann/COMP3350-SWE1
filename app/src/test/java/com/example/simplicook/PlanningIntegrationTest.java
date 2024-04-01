package com.example.simplicook;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.example.simplicook.application.Services;
import com.example.simplicook.business.PlanningManagement;
import com.example.simplicook.business.RecipeManagement;
import com.example.simplicook.objects.Recipe;
import com.example.simplicook.utils.TestUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PlanningIntegrationTest {

    private PlanningManagement planManagement;
    private RecipeManagement recipeManagement;
    private File tempDB;

    @Before
    public void setUp() throws IOException {
        System.out.println("Starting integration test for PlanningManagement");

        this.tempDB = TestUtils.copyDB();
        this.planManagement = new PlanningManagement(true);
        this.recipeManagement = new RecipeManagement((true));

        assertNotNull(this.planManagement);
        assertNotNull(this.recipeManagement);
    }

    @Test
    public void testAddRecipePlan() {
        System.out.println("\nStarting testAddRecipePlan");

        ArrayList<Recipe> plannedRecipes = planManagement.getRecipes();
        //Reminder: NO plans are present on fresh start
        assertEquals(0,plannedRecipes.size());
        //Recipe id's start at 1
        for(int i = 1; i < 5; i++) {
            planManagement.addRecipe(i);
            //Refresh list
            plannedRecipes = planManagement.getRecipes();
            assertNotNull(plannedRecipes);
            assertEquals(i,plannedRecipes.size());
        }

        System.out.println("Finished testAddRecipePlan");
    }

    @Test
    public void testRemoveRecipePlan() {
        System.out.println("\nStarting testAddRecipePlan");

        ArrayList<Recipe> plannedRecipes = planManagement.getRecipes();
        //Reminder: NO plans are present on fresh start
        assertEquals(0,plannedRecipes.size());

        planManagement.addRecipe(1);
        planManagement.addRecipe(3);
        //Refresh list
        plannedRecipes = planManagement.getRecipes();
        //Recipe id's start at 1
        assertNotNull(plannedRecipes);
        assertEquals(2,plannedRecipes.size());

        //Remove one recipe
        planManagement.removeRecipe(1);
        //Also check if that we don't remove a recipe from plan ID is not in plan
        planManagement.removeRecipe(2);
        plannedRecipes = planManagement.getRecipes();
        assertNotNull(plannedRecipes);
        assertEquals(1,plannedRecipes.size());

        System.out.println("Finished testAddRecipePlan");
    }

    @Test
    public void testGetPlanRecipes() {
        System.out.println("\nStarting testGetPlanRecipes");

        ArrayList<Recipe> plannedRecipes = planManagement.getRecipes();
        //NO plans are present on fresh start
        assertNotNull(plannedRecipes);
        assertEquals(0,plannedRecipes.size());

        planManagement.addRecipe(1);
        planManagement.addRecipe(2);

        plannedRecipes = planManagement.getRecipes();
        assertNotNull(plannedRecipes);
        assertEquals(2,plannedRecipes.size());

        System.out.println("Finished testGetPlanRecipes");

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
