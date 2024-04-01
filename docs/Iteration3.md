## SimpliCook

## Iteration 3 Release Plan

### Testing

Running Unit and Integration Tests:
- inside src > test [unitTest] > java > com.example.simplicook either
    - separately run "AllUnitTests.java" and "IntegrationTests.java"
    - or run both together with "AllTests.java"

Running Acceptance Tests:
- Simply run "AllAcceptanceTests.java" under src > androidTest > java > com.example.simplicook

### Updates

Introducing Recipe Planning. Grocery shopping just got easier!

While viewing a recipe you have the option to add the recipe to your plan, which will gather a list of ingredients to remind you about everything you need to successfully make the recipe. If you are done with the recipe you can simply remove it from your list, but don't worry as you can add it back any other time you want to make that recipe again!

We made a few changes to our recipe editor.
- Can successfully add ingredients to your recipe with a click of a button. There is no more need to write them all down with the recipe instructions.
- Now allows you to select between three difficulty options rather than type it in yourself.
- No more confusion about the time it takes to prepare the recipe as now you can only register digits to your recipes. NUMBERS ARE SUPERIOR TO WORDS!!!

Exceptions and Validations are now added to check that recipe forms are complete and up to standards before submission.

Our latest [Architecture Diagram updates](https://code.cs.umanitoba.ca/comp3350-winter2024/scaredtocompile-a01-8/-/blob/main/docs/ArchitectureSIMPLICOOK.md?ref_type=heads).

### Future Plans

Some features we pushed back included:

__Filtering for Recipes__
- There can always be more options to filter recipes. By adding more filtering options we can improve the ability to find interesting recipes. By allowing users more flexibility with filtering recipes, they can find recipes related to their needs such as diet.

__Block Recipe__
- The ability to block recipes is a goal to keep in mind for those with food allergies, religious concerns and diets. All of these are serious concerns and it would be nice for users to be able to find recipes without worrying about ingredients that may harm their health or beliefs. hide certain recipes through a block recipe option.

__Labels/Tags__
- Adding tags would help improve filtering and searching for ingredients. Users will be able to add tags and labels to their recipes, which can be highlighted while searching for recipes 
