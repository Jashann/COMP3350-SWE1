package com.example.simplicook.persistence.stubs;

import com.example.simplicook.objects.Ingredient;
import com.example.simplicook.objects.Recipe;
import com.example.simplicook.persistence.RecipePersistence;
import com.example.simplicook.persistence.utils.RecipeUtils;

import java.util.HashMap;

import java.util.ArrayList;

public class RecipePersistenceStub implements RecipePersistence {

    private static HashMap<Integer, Recipe> recipes = null;

    public RecipePersistenceStub() {
        if (recipes == null) {
            recipes = new HashMap<>();
            initialRecipeDB();
        }
    }

    // Use this constructor while using DB for testing purposes so we have clean database to start with.
    public RecipePersistenceStub(boolean isEmptyDBForTesting) {
        recipes = new HashMap<>();
        if (!isEmptyDBForTesting)
            initialRecipeDB();
    }

    public void initialRecipeDB(){

        // Sandwich Recipe
        ArrayList<Ingredient> sandwichIngredients = new ArrayList<>();
        sandwichIngredients.add(new Ingredient("bacon", "1 pound"));
        sandwichIngredients.add(new Ingredient("lettuce", "3 leaves"));
        sandwichIngredients.add(new Ingredient("tomato", "2"));
        sandwichIngredients.add(new Ingredient("bread", "3"));
        String sandwichSteps = "1. Cook bacon.\n2. Arrange lettuce, tomato, and bacon between slices of bread.";
        String sandwichDifficulty = "Easy";
        int sandwichRating = 3;
        int    sandwichTime = 5;
        Recipe sandwichRecipe = new Recipe(0,"Sandwich", sandwichSteps, "Lunch", sandwichIngredients,sandwichRating,sandwichDifficulty,sandwichTime);
        recipes.put(sandwichRecipe.getId(),sandwichRecipe);

        // Pasta Recipe
        ArrayList<Ingredient> pastaIngredients = new ArrayList<>();
        pastaIngredients.add(new Ingredient("pasta", "250 grams"));
        pastaIngredients.add(new Ingredient("tomato sauce", "1 cup"));
        pastaIngredients.add(new Ingredient("garlic", "2 cloves"));
        pastaIngredients.add(new Ingredient("parmesan cheese", "1/2 cup"));
        String pastaSteps = "1. Cook pasta.\n2. Saute garlic, add tomato sauce.\n3. Mix with cooked pasta.\n4. Sprinkle parmesan cheese.";
        String pastaDifficulty = "Moderate";
        int pastaRating = 4;
        int    pastaTime = 20;
        Recipe pastaRecipe = new Recipe(1, "Pasta", pastaSteps, "Dinner", pastaIngredients,pastaRating,pastaDifficulty,pastaTime);
        recipes.put(pastaRecipe.getId(),pastaRecipe);

        // Salad Recipe
        ArrayList<Ingredient> saladIngredients = new ArrayList<>();
        saladIngredients.add(new Ingredient("lettuce", "1 head"));
        saladIngredients.add(new Ingredient("cucumber", "1"));
        saladIngredients.add(new Ingredient("carrot", "1"));
        saladIngredients.add(new Ingredient("dressing", "to taste"));
        String saladSteps = "1. Chop lettuce, cucumber, and carrot.\n2. Toss with dressing.";
        String saladDifficulty = "Easy";
        int saladRating = 2;
        int    saladTime = 3;
        Recipe saladRecipe = new Recipe(2, "Salad", saladSteps, "Lunch", saladIngredients,saladRating,saladDifficulty,saladTime);
        recipes.put(saladRecipe.getId(),saladRecipe);

        // Stir-Fry Recipe
        ArrayList<Ingredient> stirFryIngredients = new ArrayList<>();
        stirFryIngredients.add(new Ingredient("chicken", "300 grams"));
        stirFryIngredients.add(new Ingredient("broccoli", "1 cup"));
        stirFryIngredients.add(new Ingredient("soy sauce", "2 tablespoons"));
        stirFryIngredients.add(new Ingredient("rice", "1 cup"));
        String stirFrySteps = "1. Cook rice.\n2. Stir-fry chicken and broccoli.\n3. Add soy sauce.\n4. Serve over cooked rice.";
        String stirFryDifficulty = "Hard";
        int stirFryRating = 4;
        int    stirFryTime = 15;
        Recipe stirFryRecipe = new Recipe(3, "Stir-Fry", stirFrySteps, "Dinner", stirFryIngredients,stirFryRating,stirFryDifficulty,stirFryTime);
        recipes.put(stirFryRecipe.getId(),stirFryRecipe);

        // Dessert Recipe
        ArrayList<Ingredient> dessertIngredients = new ArrayList<>();
        dessertIngredients.add(new Ingredient("flour", "1 cup"));
        dessertIngredients.add(new Ingredient("sugar", "1/2 cup"));
        dessertIngredients.add(new Ingredient("butter", "1/4 cup"));
        dessertIngredients.add(new Ingredient("chocolate chips", "1/2 cup"));
        String dessertSteps = "1. Mix flour, sugar, and butter.\n2. Add chocolate chips.\n3. Bake until golden brown.";
        String dessertDifficulty = "Easy";
        int dessertRating = 1;
        int    dessertTime = 4;
        Recipe dessertRecipe = new Recipe(4, "Dessert", dessertSteps, "Dessert", dessertIngredients,dessertRating,dessertDifficulty,dessertTime);
        recipes.put(dessertRecipe.getId(),dessertRecipe);
    }

    @Override
    public void insertRecipe(Recipe currentRecipe) {
        if(validateRecipeData(currentRecipe))
            recipes.put(currentRecipe.getId(), currentRecipe);
    }

    @Override
    public void deleteRecipe(int recipeID) {
        recipes.remove(recipeID);
    }

    @Override
    public void editRecipe(Recipe currentRecipe) {
        int id = currentRecipe.getId();
        if(recipes.containsKey(id)){
            recipes.put(currentRecipe.getId(),currentRecipe);
        }else {
            System.out.println("Can not find this recipe.");
        }
    }

    @Override
    public Recipe getRecipe(int id) {
        return recipes.get(id);
    }

    public ArrayList<Recipe> getRecipes() {
        return RecipeUtils.getRecipesFromHashSet(recipes);
    }

    public int getSize() {
        return recipes.size();
    }

    // Clear all recipes from the Database
    // Usage: clearDB("CLEAR");
    // WARNING: DON'T USE IT OUTSIDE OF TESTING
    public void clearDB(String clear) {
        if (clear.equals("CLEAR")) // safety check
            recipes.clear();
    }

    //Make sure Recipe values are NOT EMPTY
    private boolean validateRecipeData(Recipe checkRecipe) {
        return !checkRecipe.title.equals("") &&
               !checkRecipe.description.equals("") &&
               !checkRecipe.type.equals("");
    }

}
