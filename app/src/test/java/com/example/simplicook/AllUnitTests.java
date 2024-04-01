package com.example.simplicook;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        IngredientTest.class,
        RecipeTest.class,
        RecipeManagementTest.class,
        SearchManagementTest.class,
        PlanningManagementTest.class,
        ExceptionsTests.class

})

public class AllUnitTests {
}
