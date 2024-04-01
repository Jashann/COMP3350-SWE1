package com.example.simplicook;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        RecipeManagementIntegrationTest.class,
        SearchManagementIntegrationTest.class,
        SortRecipeIntegrationTest.class,
        PlanningIntegrationTest.class,
        ActionHelperIntegrationTest.class
})
public class AllIntegrationTests {
}
