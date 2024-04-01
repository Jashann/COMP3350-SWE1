package com.example.simplicook.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.simplicook.R;
import com.example.simplicook.application.Main;
import com.example.simplicook.business.ActionHelper;
import com.example.simplicook.business.PlanningManagement;
import com.example.simplicook.business.RecipeManagement;
import com.example.simplicook.objects.Ingredient;
import com.example.simplicook.objects.Recipe;

import java.util.ArrayList;
import java.util.List;
/**
 * Activity to display the details of a selected recipe. Allows users to edit, delete, add to plan,
 * mark as favorite, and rate the recipe.
 */
public class RecipeDisplayActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        RecipeManagement recipeManagement = new RecipeManagement(Main.DB_TYPE);
        PlanningManagement planningManagement = new PlanningManagement(Main.DB_TYPE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_display);


        // Retrieve data passed from HomePageActivity
        String id = getIntent().getStringExtra("recipeID");

        //Setup page for view
        setupRecipeForView(Integer.parseInt(id));

        //set activity for edit button
        Button editButton = findViewById(R.id.buttonEdit);
        Button addToPlan = findViewById(R.id.addPlans);

        addToPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                planningManagement.addRecipe(Integer.parseInt(id));
                ArrayList<Recipe> recipes = planningManagement.getRecipes();
                Toast.makeText(RecipeDisplayActivity.this,  "Recipe was added to plan!", Toast.LENGTH_SHORT).show();
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecipeDisplayActivity.this, AddRecipeActivity.class);
                intent.putExtra("recipeID", id);
                startActivity(intent);
            }
        });


        //set activity for delete button
        Button delete = findViewById(R.id.buttonDelete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecipeDisplayActivity.this, DeleteRecipeActivity.class);
                intent.putExtra("recipeID", id);
                startActivity(intent);
            }
        });

        //RETURN BUTTON BACK TO MAIN PAGE
        Button buttonReturn = findViewById(R.id.buttonReturn);
        //set activity for return button
        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(RecipeDisplayActivity.this, HomePageActivity.class);
                startActivity(intent);
            }
        });

        //change state of the favourite button
        CheckBox favourite = findViewById(R.id.favourite_button);
        int recipeID = Integer.parseInt(id);
        Recipe selectedRecipe = recipeManagement.getRecipe(recipeID);
        favourite.setChecked(selectedRecipe.favourite);

        // Set an OnCheckedChangeListener on the 'favourite' CheckBox to handle when it is checked or unchecked.
        favourite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isFavourited) {
                //handle the state change of the favourite button
                if(isFavourited)
                {
                    Toast.makeText(RecipeDisplayActivity.this, "Added to Favourites", Toast.LENGTH_SHORT).show();
                    recipeManagement.setRecipeAsFavorite(recipeID,true);

                }
                else
                //handle the actions for when it is unfavourited
                {
                    Toast.makeText(RecipeDisplayActivity.this, "Removed from Favourites", Toast.LENGTH_SHORT).show();
                    recipeManagement.setRecipeAsFavorite(recipeID,false);
                }
            }
        });

        RatingBar feedback = findViewById(R.id.ratingBarFeedback);
        // Set an OnRatingBarChangeListener on the feedback RatingBar.
        feedback.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                int integerRating = Math.round(rating);
                int recipeID = Integer.parseInt(id);
                Toast.makeText(RecipeDisplayActivity.this, "Thank you for feedback", Toast.LENGTH_SHORT).show();
                recipeManagement.setRecipeFeedback(recipeID,integerRating);
            }
        });

    }

    /**
     * Fetches the recipe by ID and sets up the UI to display its details.
     */
    private void setupRecipeForView(int id) {
        Recipe view = ActionHelper.getRecipeById(id);
        setupDisplay(view);
    }

    /**
     * Populates the activity's views with the details of the provided recipe.
     */
    private void setupDisplay(Recipe recipe) {
        // Set data to the corresponding TextViews and RatingBar
        TextView textViewRecipeName = findViewById(R.id.textViewRecipeName);
        textViewRecipeName.setText(recipe.title);

        TextView textViewRecipeType = findViewById(R.id.textViewRecipeType);
        textViewRecipeType.setText(recipe.type);

        TextView textViewRecipeDescription = findViewById(R.id.textViewRecipeDescription);
        textViewRecipeDescription.setText(recipe.description);

        RatingBar ratingBar  = findViewById(R.id.ratingBarRecipe);
        ratingBar.setRating(recipe.rating);
        ratingBar = findViewById(R.id.ratingBarFeedback);
        ratingBar.setRating(recipe.feedback);
        TextView textViewRecipeDifficulty = findViewById(R.id.textViewDifficulty);
        textViewRecipeDifficulty.setText(recipe.difficultyLevel);

        TextView textViewIngredients = findViewById(R.id.textViewIngredients);

        List<Ingredient> ingredientArrayList = recipe.ingredients;
        StringBuffer stringBuffer = new StringBuffer();

        for (Ingredient ingredient: ingredientArrayList) {
            stringBuffer.append(ingredient.name).append(" ").append(ingredient.unit).append("\n");
        }

        if (!ingredientArrayList.isEmpty())
            textViewIngredients.setText(stringBuffer.toString());

        TextView textViewRecipeTime = findViewById(R.id.textViewTimeNeeded);
        textViewRecipeTime.setText(recipe.preparationTime + ": mins");

    }

}
