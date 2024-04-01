package com.example.simplicook;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AllUnitTests.class,
        AllIntegrationTests.class
})

public class AllTests {
}
