package com.example.simplicook.presentation;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simplicook.R;
import com.example.simplicook.business.PlanningManagement;
import com.example.simplicook.objects.Recipe;

import java.util.List;
/**
 * PlanListActivity is responsible for displaying the list of recipes that the user has planned.
 * It allows users to view their planned recipes and provides an option to delete any recipe from their plan.
 */
public class PlanListActivity extends AppCompatActivity {
    List<Recipe> recipeList;// List to hold the planned recipes

    PlanningManagement planningManagement;// Business logic handler for planning features
    /**
     * Initializes the activity, sets the content view, and populates the list of planned recipes.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     *                           Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_list);

        planningManagement = new PlanningManagement();
        recipeList = planningManagement.getRecipes();

        populateRecipeViews();

        Button back = findViewById(R.id.backToHome);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlanListActivity.this, HomePageActivity.class);
                startActivity(intent);
            }
        });
    }
    /**
     * Dynamically creates and adds views for each recipe in the planned recipes list to the UI.
     */
    private void populateRecipeViews() {
        final LinearLayout planningContainer = findViewById(R.id.planningContainer);
        planningContainer.removeAllViews(); // Clear existing views

        for (Recipe currentRecipe : recipeList) {
            addNewRecipeView(planningContainer, currentRecipe);
        }
    }

    /**
     * Dynamically creates a view for a given recipe, including the recipe title and a delete button.
     * Also creates a sub-view to display the recipe's ingredients.
     * @param container The parent container where the recipe view will be added.
     * @param recipe The recipe object containing the information to be displayed.
     */
    private void addNewRecipeView(LinearLayout container, Recipe recipe) {
        // Context should be your Activity or Fragment context
        Context context = this; // or getActivity() if you're in a Fragment

        // Layout for recipe name and delete button
        LinearLayout recipeLayout = new LinearLayout(context);
        recipeLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams recipeLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        recipeLayout.setLayoutParams(recipeLayoutParams);

        // TextView for recipe name
        TextView recipeNameTextView = new TextView(context);
        recipeNameTextView.setLayoutParams(new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
        recipeNameTextView.setText(recipe.title);
        recipeNameTextView.setGravity(Gravity.CENTER_VERTICAL);
        recipeLayout.addView(recipeNameTextView);

        // Button for delete action
        Button deleteRecipeButton = new Button(context);
        LinearLayout.LayoutParams deleteButtonLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        deleteRecipeButton.setText("Remove");
        deleteRecipeButton.setId(View.generateViewId()); // Generate unique ID for the button
        deleteRecipeButton.setOnClickListener(v -> {
            // Handle recipe deletion
            planningManagement.removeRecipe(recipe.getId());
            // Refresh the recipe list
            recipeList = planningManagement.getRecipes();
            // Refresh the views
            populateRecipeViews();
        });

        recipeLayout.addView(deleteRecipeButton);

        // Adding the recipe layout to the container
        container.addView(recipeLayout);

        // Layout for ingredients
        LinearLayout ingredientsLayout = new LinearLayout(context);
        ingredientsLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams ingredientsLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ingredientsLayout.setLayoutParams(ingredientsLayoutParams);

        // Dynamically add ingredients as TextViews or any other view
        for (String ingredient : recipe.getIngredientNames()) {
            TextView ingredientTextView = new TextView(context);
            ingredientTextView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            String ingredientText = "   • " + ingredient;
            ingredientTextView.setText(ingredientText);
            ingredientsLayout.addView(ingredientTextView);
        }

        // Adding the ingredients layout to the container
        container.addView(ingredientsLayout);
    }


}

