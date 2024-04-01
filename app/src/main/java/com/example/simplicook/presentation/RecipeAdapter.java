package com.example.simplicook.presentation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simplicook.R;
import com.example.simplicook.objects.Recipe;

import java.util.ArrayList;

/**
 * Adapter for the RecyclerView that displays a list of recipes.
 * Each item in the list represents a single Recipe object.
 */
public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.MyViewHolder> {

    private ArrayList<Recipe> recipesList;

    /**
     * Constructor for the RecipeAdapter.
     * @param recipesList List of Recipe objects to be displayed in the RecyclerView.
     */
    public RecipeAdapter(ArrayList<Recipe> recipesList) {
        this.recipesList = recipesList;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView recipeTitle;

        /**
         * Creates new views
         */
        public MyViewHolder(final View view) {
            super(view);
            // Initialize recipeTitle TextView by finding its view from the itemView
            recipeTitle = view.findViewById(R.id.recipe_name);
            // Set OnClickListener to the itemView
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Check if the listener is not null and the clicked position is valid
                    if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                        // Call onItemClick method of the listener and pass the corresponding recipe
                        listener.onItemClick(recipesList.get(getAdapterPosition()));
                    }
                }
            });
        }
    }


    /**
     * Create ViewHolder by inflating the layout for each item
     */
    @NonNull
    @Override
    public RecipeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // Inflate the layout for the item view
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recipe_adapter, viewGroup, false);
        // Create and return a new instance of MyViewHolder with the inflated view
        return new MyViewHolder(itemView);

    }

    /**
     * Replaces the contents of a view (invoked by the layout manager).
     */
    @Override
    public void onBindViewHolder(@NonNull RecipeAdapter.MyViewHolder myViewHolder, int position) {
        // Get the recipe object at the given position
        Recipe recipe = recipesList.get(position);
        // Set the title of the recipe to the recipeTitle TextView
        myViewHolder.recipeTitle.setText(recipe.title + "\n\t\tLevel: "+recipe.difficultyLevel+"\t\tTime Cost: "+
                recipe.preparationTime+"mins"+"\t\t Rating: "+recipe.rating);
    }



    @Override
    public int getItemCount() {
        return recipesList.size();
    }
    public interface OnItemClickListener {
        void onItemClick(Recipe recipe);
    }
    private OnItemClickListener listener;
    /**
     * Sets the listener for item click events.
     * @param listener OnItemClickListener to set.
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}