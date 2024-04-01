package com.example.simplicook.presentation;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;

import android.util.Log;
import android.view.View;

import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.simplicook.R;
import com.example.simplicook.business.ActionHelper;
import com.example.simplicook.business.RecipeValidator;
import com.example.simplicook.business.exceptions.InvalidRecipeException;
import com.example.simplicook.business.exceptions.MissingIngredientException;
import com.example.simplicook.objects.Ingredient;
import com.example.simplicook.objects.Recipe;
import com.example.simplicook.utilities.IdGenerator;

import java.util.ArrayList;
import java.util.List;

public class AddRecipeActivity extends Activity {
    private int recipeID;
    private boolean isEdit; //Check if we are in add or edit mode

    private EditText title;
    private EditText type;

    private EditText preparationTime;

    private  Spinner difficultyLevel;

    private  EditText description;

    private   LinearLayout ingredientsContainer;

    private  Button addIngredientButton;
    private Button cancel;

    private  Button submit;

    /**
     * Activity for adding a new recipe or editing an existing one.
     * This screen allows the user to input recipe details and submit them.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set layout fot AddRecipe Activity
        setContentView(R.layout.activity_add_recipe_page);

        //get recipe
        Intent action = getIntent();
        String id = action.getStringExtra("recipeID");

        //Determine if we are editing or adding new recipe.
        isEdit = ActionHelper.editingRecipe(id);
        recipeSetup(id);

        cancel = findViewById(R.id.cancelAddRecipeButton);
        submit = findViewById(R.id.submitRecipeButton);
        addIngredientButton = findViewById(R.id.addIngredientButton);
        title = (EditText) findViewById(R.id.editRecipeName);
        description = (EditText) findViewById(R.id.editRecipeDescription);
        type = (EditText)findViewById(R.id.editType);
        preparationTime = (EditText) findViewById(R.id.preparationTime);
        difficultyLevel =(Spinner) findViewById(R.id.difficultyLevel);
        ingredientsContainer= findViewById(R.id.ingredients_container);;

        /**
         * Sets up the back button's onClick listener to navigate back to the home page when clicked.
         * This method is expected to be called during the activity setup, linking the back button
         * with the functionality to return to the home page.
         *
         * @param v The view that was clicked.
         */
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddRecipeActivity.this, HomePageActivity.class);
                startActivity(intent);
            }
        });

        /**
         * Sets up the add ingredient button's onClick listener to dynamically add new ingredient input fields
         * to the layout. This allows the user to enter multiple ingredients for a recipe.
         *
         * @param v The view that was clicked.
         */
        addIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Call the method to add new ingredient fields dynamically
                addNewIngredientView(ingredientsContainer);
            }
        });
    }

    /**
     * Adds a new ingredient input view to the specified container. This method dynamically creates
     * a horizontal LinearLayout containing two EditText fields, one for the ingredient name and
     * another for its unit of measurement. The user can fill these fields.
     *
     * @param container The LinearLayout container where the new ingredient view will be added.
     */

    private void addNewIngredientView(LinearLayout container) {
        // Create a new horizontal LinearLayout for ingredient name and unit
        LinearLayout newIngredientLayout = new LinearLayout(this);
        newIngredientLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMarginStart(50);
        layoutParams.setMarginEnd(50);
        newIngredientLayout.setLayoutParams(layoutParams);

        // Create EditText for ingredient name
        EditText ingredientName = new EditText(this);
        ingredientName.setLayoutParams(new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        ingredientName.setHint("Name of ingredient");

        // Create EditText for ingredient unit
        EditText ingredientUnit = new EditText(this);
        ingredientUnit.setLayoutParams(new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        ingredientUnit.setHint("Unit of measurement");

        // Add the EditTexts to the LinearLayout
        newIngredientLayout.addView(ingredientName);
        newIngredientLayout.addView(ingredientUnit);

        // Add the new LinearLayout to the container
        container.addView(newIngredientLayout);
    }
    /**
     * Adds a new ingredient input view to the specified container, initializing the fields with
     * provided name and unit. Similar to the other overload, but sets the EditText fields to
     * display the given name and unit values.
     *
     * @param container The LinearLayout container where the new ingredient view will be added.
     * @param name The name of the ingredient to be set in the name EditText field.
     * @param unit The unit of measurement for the ingredient to be set in the unit EditText field.
     */
    private void addNewIngredientView(LinearLayout container, String name, String unit) {
        // Create a new horizontal LinearLayout for ingredient name and unit
        LinearLayout newIngredientLayout = new LinearLayout(this);
        newIngredientLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMarginStart(50);
        layoutParams.setMarginEnd(50);
        newIngredientLayout.setLayoutParams(layoutParams);

        // Create EditText for ingredient name
        EditText ingredientName = new EditText(this);
        ingredientName.setLayoutParams(new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        ingredientName.setText(name);

        // Create EditText for ingredient unit
        EditText ingredientUnit = new EditText(this);
        ingredientUnit.setLayoutParams(new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        ingredientUnit.setText(unit);

        // Add the EditTexts to the LinearLayout
        newIngredientLayout.addView(ingredientName);
        newIngredientLayout.addView(ingredientUnit);

        // Add the new LinearLayout to the container
        container.addView(newIngredientLayout);
    }
    /**
     * Updates an existing recipe or adds a new one to the database based on the 'isEdit' flag state.
     * This method collects the recipe information from the provided EditText fields and the
     * 'allIngredients' ArrayList, then either adds a new recipe to the database or updates an existing
     * one. Upon successful completion, a toast message is displayed to inform the user of the success.
     *
     * @param editName        EditText field containing the recipe's name.
     * @param editDescription EditText field containing the recipe's description.
     * @param editType        EditText field containing the recipe's type.
     * @param difficulty      String indicating the difficulty level of the recipe.
     * @param allIngredients  ArrayList containing the ingredients of the recipe.
     * @param time            Integer indicating the preparation time of the recipe in minutes.
     */
    private void updateAddRecipe(EditText editName, EditText editDescription, EditText editType, String difficulty, ArrayList<Ingredient> allIngredients, int time) {
        //If we aren't editing we are adding a new recipe.
        if (!isEdit) {
            //Add new recipe to Database
            ActionHelper.addRecipe(editName.getText().toString(), editDescription.getText().toString(), editType.getText().toString(),difficulty,allIngredients,time);
            Toast.makeText(AddRecipeActivity.this, "Successfully created a new recipe", Toast.LENGTH_SHORT).show();
        } else {
            //Make changes to existing recipe.
            ActionHelper.editRecipe(recipeID, editName.getText().toString(), editDescription.getText().toString(), editType.getText().toString(),difficulty,allIngredients,time);
            Toast.makeText(AddRecipeActivity.this, "Recipe updated successfully", Toast.LENGTH_SHORT).show();

        }
    }

    /**
     * Configures the visibility and functionality of the delete button based on the current operation.
     * If editing an existing recipe (indicated by 'isEdit' being true), the delete button is shown and
     * configured to open the DeleteRecipeActivity upon click, passing the current recipe's ID.
     * If adding a new recipe, the delete button is hidden as it is not applicable.
     *
     * @param id The ID of the recipe being edited, or null if adding a new recipe.
     */
    private void isDeleteActive(String id) {
        Button delete = findViewById(R.id.buttonDelete);
        if(isEdit) {
            //set activity for delete recipe pop up
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AddRecipeActivity.this, DeleteRecipeActivity.class);
                    intent.putExtra("recipeID", id);
                    startActivity(intent);
                }
            });
        } else {
            //When adding a new recipe, delete button should NOT display
            delete.setVisibility(View.GONE);
        }
    }
    /**
     * Sets up the recipe editing form either by populating it with the details of an existing recipe
     * for editing or preparing it for adding a new recipe. It also decides whether to display the delete
     * button based on the editing status.
     *
     * @param id The ID of the recipe to edit; if null or adding a new recipe, deletion is not applicable.
     */
    private void recipeSetup(String id) {
        if(isEdit) {
            recipeID = Integer.parseInt(id);
            updateTextBoxes(recipeID);
        }
        //check if we are displaying delete button
        isDeleteActive(id);
    }

    /**
     * Populates the text fields of the recipe editing form with the details of an existing recipe.
     * This includes setting the recipe name, description, type, preparation time, and difficulty level,
     * as well as dynamically adding ingredient fields for each ingredient in the recipe.
     *
     * @param id The ID of the recipe whose details are to be fetched and displayed.
     */
    private void updateTextBoxes(int id) {
        Recipe recipe = ActionHelper.getRecipeById(id);
        final LinearLayout ingredientsContainer = findViewById(R.id.ingredients_container);
        ((EditText) findViewById(R.id.editRecipeName)).setText(recipe.title);
        ((EditText) findViewById(R.id.editRecipeDescription)).setText(recipe.description);
        ((EditText) findViewById(R.id.editType)).setText(recipe.type);
        ((EditText) findViewById(R.id.preparationTime)).setText(String.valueOf(recipe.preparationTime));
        ((Spinner) findViewById(R.id.difficultyLevel)).setSelection(updateSpinner(recipe.difficultyLevel));
        List<Ingredient> ingredientsArrayList = recipe.ingredients;
        for (Ingredient ingredient: ingredientsArrayList)
            addNewIngredientView(ingredientsContainer, ingredient.name, ingredient.unit);

    }

    private int updateSpinner(String difficulty) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.recipe_difficulty, android.R.layout.simple_spinner_item);
        Spinner spinner = findViewById(R.id.difficultyLevel);
        spinner.setAdapter(adapter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter.getPosition(difficulty);
    }

    /**
     * Handles the click event of the save button. Validates the new or edited recipe details,
     * constructs a Recipe object, and either saves the new recipe or updates an existing one in the database.
     * It navigates back to the home page if the operation is successful or shows an error message if validation fails.
     *
     * @param v The view that was clicked.
     */

    public void buttonSaveOnClick(View v)  {
        ArrayList<Ingredient> ingredientArrayList = extractIngredientsFromLayout();

        String difficulty = difficultyLevel.getSelectedItem().toString();
        int time = parsePreparationTime();

        Recipe newRecipe = new Recipe(IdGenerator.generateUniqueId(),
                title.getText().toString(),
                description.getText().toString(),
                type.getText().toString(),
                ingredientArrayList,
                0,
                difficulty,
                time);

         try {
            if (RecipeValidator.validRecipe(newRecipe)) {
                updateAddRecipe(title, description, type, difficulty, ingredientArrayList, time);
                navigateToHomePage();
            }
        } catch (InvalidRecipeException e) {
            Log.e("Recipe does not meet all requirements : " + newRecipe.title, e.getMessage());
            e.printStackTrace();
             showValidationErrorMessage(e.getMessage());

        }
    }

    /**
     * Extracts ingredient details from the dynamically added input fields in the layout and constructs an
     * ArrayList of Ingredient objects.
     *
     * @return ArrayList of Ingredient objects extracted from the input fields.
     */

    private ArrayList<Ingredient> extractIngredientsFromLayout() {
        ArrayList<Ingredient> ingredientArrayList = new ArrayList<>();

        for (int i = 0; i < ingredientsContainer.getChildCount(); i++) {
            View child = ingredientsContainer.getChildAt(i);
            if (child instanceof LinearLayout) {
                Ingredient newIngredient = extractIngredientFromLayout((LinearLayout) child);
                try {
                    if(RecipeValidator.validateIngredient(newIngredient)) {
                        ingredientArrayList.add(newIngredient);
                    }
                } catch (MissingIngredientException e){
                    Log.e("Missing Ingredient Parameter name: " + newIngredient.name + " or unit: " + newIngredient.unit, e.getMessage());
                    e.printStackTrace();
                }
            }
        }
        return ingredientArrayList;
    }
    /**
     * Extracts the name and unit of an ingredient from the provided LinearLayout.
     * Each ingredient layout is expected to contain two EditText fields: one for the name
     * and another for the unit of the ingredient.
     *
     * @param ingredientLayout The LinearLayout containing EditText fields for the ingredient's name and unit.
     * @return Ingredient object populated with the extracted name and unit.
     */
    private Ingredient extractIngredientFromLayout(LinearLayout ingredientLayout) {
        Ingredient newIngredient = new Ingredient();

        for (int j = 0; j < ingredientLayout.getChildCount(); j++) {
            View ingredientChild = ingredientLayout.getChildAt(j);
            if (ingredientChild instanceof EditText) {
                EditText editText = (EditText) ingredientChild;
                String text = editText.getText().toString().trim();

                switch (j) {
                    case 0:
                        newIngredient.name = text;
                        break;
                    case 1:
                        newIngredient.unit = text;
                        break;
                }
            }
        }
        return newIngredient;
    }
    /**
     * Parses the preparation time entered by the user in the EditText field. If the user input
     * is not an integer, it catches the NumberFormatException and logs an error, returning 0.
     *
     * @return The preparation time as an integer. Returns 0 if the input is invalid.
     */
    private int parsePreparationTime() {
        int time = 0;
        try {
            time = Integer.parseInt(preparationTime.getText().toString());
        } catch (NumberFormatException e) {
            System.out.println("User did not input int for time " + e);
        }
        return time;
    }
    /**
     * Navigates the user back to the HomePageActivity. This method is typically called
     * after a recipe has been successfully added or updated.
     */
    private void navigateToHomePage() {
        Intent intent = new Intent(AddRecipeActivity.this, HomePageActivity.class);
        startActivity(intent);
    }
    /**
     * Displays a toast message to the user indicating that some required fields were not
     * correctly filled. This method is called when the recipe validation fails.
     */
    private void showValidationErrorMessage(String message) {
        Toast.makeText(AddRecipeActivity.this, "Please fill all required fields correctly.", Toast.LENGTH_SHORT).show();
    }



}
