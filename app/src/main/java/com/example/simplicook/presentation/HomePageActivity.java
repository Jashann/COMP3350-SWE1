package com.example.simplicook.presentation;

import android.content.Intent;

import com.example.simplicook.R;
import com.example.simplicook.business.ISortRecipe;
import com.example.simplicook.business.PlanningManagement;
import com.example.simplicook.business.RecipeManagement;
import com.example.simplicook.business.SearchManagement;
import com.example.simplicook.business.SortRecipe;
import com.example.simplicook.objects.Recipe;
import com.example.simplicook.persistence.hsqldb.DBHelper;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.AdapterView;

import android.widget.Toast;
import androidx.appcompat.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
/**
 * HomePageActivity serves as the main activity that users first encounter upon launching the app.
 * It displays a list of recipes, provides options to search, sort, view plans, and add new recipes.
 */
public class HomePageActivity extends AppCompatActivity implements RecipeAdapter.OnItemClickListener  {
    private SearchView searchBar;

    private ArrayList<Recipe> recipesList;
    private RecyclerView recyclerView;
    private static RecipeManagement recipeManagement;


    private ISortRecipe interfaceForSort;
    /**
     * Constructor initializes the interface used for sorting recipes.
     */
    public HomePageActivity() {
        interfaceForSort = new SortRecipe();
    }
    /**
     * Called when the activity is starting. This method initializes the activity,
     * inflates the UI, sets up interaction elements and data management components.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     *                           Otherwise, it is null.
     */
    protected void onCreate(Bundle savedInstanceState) {
        /**
         * Initializes and sets up UI components like buttons, search bar, and the recipes list view.
         */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        DBHelper.copyDatabaseToDevice(this);
        recipeManagement = new RecipeManagement();
        // Don't delete planningManagement from HomePage
        PlanningManagement planningManagement = new PlanningManagement();
        recipesList = recipeManagement.getAllRecipes();

        //set button from layout
        Button addNewRecipe = findViewById(R.id.ButtonAdd);
        Button viewPlan = findViewById(R.id.ButtonToPlan);
        /**
         * Sets up the search functionality to filter recipes as per the text entered in the search bar.
         */
        searchBar = findViewById(R.id.searchBar);
        searchBar.clearFocus();

        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Only filter the list for non-empty queries
                if (!query.isEmpty()) {
                    Toast.makeText(HomePageActivity.this, "Searching for: " + query, Toast.LENGTH_SHORT).show();
                    recipesList = SearchManagement.searchByText(query);
                } else {
                    recipesList = recipeManagement.getAllRecipes();
                }
                setAdapter(); // Always refresh the adapter
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Handle empty query case
                if (newText.isEmpty()) {
                    recipesList = recipeManagement.getAllRecipes();
                    setAdapter(); // Refresh adapter when query is cleared
                }
                return true;
            }
        });

        //set activity for ADD button
        addNewRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, AddRecipeActivity.class);
                startActivity(intent);
            }
        });

        viewPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePageActivity.this, PlanListActivity.class));
            }
        });

        //display recipe from view
        recyclerView = findViewById(R.id.homePageRecyclerView);
        recipesList = new ArrayList<>();
        onResume();
        setAdapter();
        /**
         * Sets up the functionality to sort recipes based on the selection from a spinner (dropdown menu).
         */

        //drop box for sort
        Spinner spinner = findViewById(R.id.sortBy);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String selectedOption = adapterView.getItemAtPosition(position).toString();
                refreshRecipesList();
                if(!selectedOption.equals("Recommendations")) {
                    interfaceForSort.sortManager(selectedOption, recipesList);
                }
                setAdapter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Do something when nothing is selected
            }
        });

    }
    /**
     * Refreshes the list of recipes displayed.
     */
    protected void onResume() {
        super.onResume();
        refreshRecipesList();
        setAdapter();
    }
    /**
     * Sets or updates the adapter for the RecyclerView managing the list of recipes.
     * This method ensures the UI reflects the current state of the recipes list.
     */
    private void setAdapter() {
        RecipeAdapter adapter = new RecipeAdapter(recipesList);
        adapter.setOnItemClickListener(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
    /**
     * Refreshes the list of recipes displayed, fetching the latest collection from the database.
     */
    private void refreshRecipesList() {
        recipesList.clear();
        recipesList = recipeManagement.getAllRecipes();
    }

    /**
     * Handles clicks on items within the RecyclerView, navigating to the RecipeDisplayActivity
     * to show detailed information about the selected recipe.
     *
     * @param recipe The recipe object corresponding to the clicked item.
     */
    @Override
    public void onItemClick(Recipe recipe) {
        // Handle the item click here
        // Start the ActivityRecipeDisplay with the selected recipe data
        Intent intent = new Intent(HomePageActivity.this, RecipeDisplayActivity.class);
        intent.putExtra("recipeID", (Integer.toString(recipe.getId())));
        startActivity(intent);
    }



}

