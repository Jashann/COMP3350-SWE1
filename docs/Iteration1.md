## SimpliCook

## Iteration 1 Release Plan

### Testing

Run all Unit Tests in one file

- Run file named "AllUnitTests.java" under src > test [unitTest] > java > com.example.simplicook

### Updates

Added interface for home page, adding a recipe and viewing recipes.
- The home page activity displays recipes that users can look at. They have the option to search for recipes as well as add new recipes.
- Users can use the search bar on the home page to find recipes related to the words they search.
- The add recipe activity contains information users must fill out to fully create a recipe. Newly created recipes can be found through the search bar and the home page.
- While viewing a recipe users will have access to the recipe's information. They also will have the option to delete the recipe. In a future update, users will be able to edit their recipes (more on that later).

Link to our [Architecture Diagram for Iteration 1](https://code.cs.umanitoba.ca/comp3350-winter2024/scaredtocompile-a01-8/-/blob/Iteration1_release/docs/ArchitectureSIMPLICOOK.md?ref_type=tags).

### Moved Items
__devTask_edit__
- We are moving the ability to edit the recipe to Iteration 2 as it would have had a very similar structure to the activity_add_recipe_page.xml. This way we can avoid having multiple files with similar code. Once implemented, the option to delete a recipe will be moved inside edit for extra safety, to ensure that users are less likely to accidentally delete a recipe.

__devTask_Ingredient__
- Inside our AddRecipeActivity we were having problems when implementing an adjustable list for users to add an ingredient. We are creating a new developer task to focus on this list of ingredients. Until then users are encouraged to add ingredients with their instructions.

