package com.example.simplicook.presentation;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.simplicook.R;
import com.example.simplicook.business.ActionHelper;

import java.util.Objects;

public class DeleteRecipeActivity extends Activity {

    /**
     * Called when the activity is starting. This is where most initialization should go:
     * calling setContentView(int) to inflate the activity's UI, using findViewById to programmatically
     * interact with widgets in the UI, setting up listeners, and initializing class-level variables.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     *                           Note: Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_recipe_popup);

        setUpWindowDisplay();

        Button confirm = findViewById(R.id.confirmDelete);
        Button back = findViewById(R.id.cancelDelete);
        String id = getIntent().getStringExtra("recipeID");

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int recipeId = Integer.parseInt(id);

                //delete recipe
                String recipeName = ActionHelper.deleteRecipe(recipeId);

                //display message (change to recipe name)
                Toast.makeText(getApplicationContext(), recipeName + " was deleted.", Toast.LENGTH_LONG).show();

                // Go back to home page
                Intent intent = new Intent(DeleteRecipeActivity.this, HomePageActivity.class);
                startActivity(intent);
            }
        });

        //Go back to viewing recipe
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //Close popup message
                finish();
            }
        });

    }

    /**
     * Configures the display settings for the activity's window, specifically setting its size.
     * This method is intended to make the activity appear as a pop-up window with specific dimensions
     * relative to the device's screen size.
     */
    private void setUpWindowDisplay() {
        //Popup display
        DisplayMetrics display = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(display);

        //Set popup window size
        int width = display.widthPixels;
        int height = display.heightPixels;
        getWindow().setLayout((int) (width * 0.9), (int) (height * 0.5));
    }

}
