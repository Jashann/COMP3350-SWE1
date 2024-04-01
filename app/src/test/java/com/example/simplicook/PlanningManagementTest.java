package com.example.simplicook;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.simplicook.business.PlanningManagement;
import com.example.simplicook.business.RecipeManagement;
import com.example.simplicook.objects.Recipe;
import com.example.simplicook.persistence.PlanningPersistence;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class PlanningManagementTest {


    @Before
    public void setup() {
        System.out.println("Starting test for PlanningManagementTest");

    }

    @Test
    public void testGetRecipes() {
        // Mock PlanningPersistence
        PlanningPersistence planningPersistence = mock(PlanningPersistence.class);

        PlanningManagement toTest = new PlanningManagement(planningPersistence);
        List<Recipe> mockList = new ArrayList<>();
        String[] titles = {
                "zero one two three four",
                "one two three four",
                "two three four",
                "three four",
                "four"
        };

        for(int i =0; i<titles.length;i++)
            mockList.add(new Recipe(i+1,titles[i], "d", "t"));

        when(planningPersistence.getRecipes()).thenReturn((ArrayList<Recipe>) mockList);

        assertEquals(toTest.getRecipes(), mockList);
    }

    @Test
    public  void testAddRecipe()
    {
        PlanningPersistence planningPersistence = mock(PlanningPersistence.class);
        PlanningManagement toTest = new PlanningManagement(planningPersistence);
        int id = 123;
        doNothing().when(planningPersistence).addRecipe(id);
        toTest.addRecipe(id);
        verify(planningPersistence, times(1)).addRecipe(id);
    }

    @Test
    public  void testRemoveRecipe()
    {
        PlanningPersistence planningPersistence = mock(PlanningPersistence.class);
        PlanningManagement toTest = new PlanningManagement(planningPersistence);
        int id = 123;
        doNothing().when(planningPersistence).removeRecipe(id);
        toTest.removeRecipe(id);
        verify(planningPersistence, times(1)).removeRecipe(id);
    }

}
