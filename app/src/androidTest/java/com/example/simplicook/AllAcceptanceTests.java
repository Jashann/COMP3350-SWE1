package com.example.simplicook;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        HomeViewAcceptanceTests.class,
        ManageRecipeAcceptanceTests.class,

})
public class AllAcceptanceTests {
}
