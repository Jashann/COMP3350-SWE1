package com.example.simplicook.objects;
import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Recipe implements Serializable {
	public boolean favourite;
	//basic attributes for a recipe
	private int id;
	public String title;
	public String description;
	public String type;
	public List<Ingredient> ingredients;

	public int rating;
	public String difficultyLevel;

	public int feedback;
	//in minutes
	public   int preparationTime;

	// Helper constructor to be used by other public constructors
	private Recipe(int id, String title, String description, String type, List<Ingredient> ingredients,
				   String difficultyLevel, int preparationTime, int rating, boolean favourite, int feedback) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.type = type;
		this.ingredients = ingredients;
		this.difficultyLevel = difficultyLevel;
		this.preparationTime = preparationTime;
		this.rating = rating;
		this.favourite = favourite;
		this.feedback = feedback;
	}

	// Public constructors calling the private constructor for initialization
	public Recipe(Recipe currentRecipe) {
		this(currentRecipe.id, currentRecipe.title, currentRecipe.description, currentRecipe.type,
				currentRecipe.ingredients, currentRecipe.difficultyLevel, currentRecipe.preparationTime,
				currentRecipe.rating, currentRecipe.favourite, currentRecipe.feedback);
	}

	public Recipe(int id, String title, String description, String type, List<Ingredient> allIngredient,
				  String difficultyLevel, int preparationTime) {
		this(id, title, description, type, allIngredient, difficultyLevel, preparationTime, 0, false, 0);
	}

	public Recipe(int id, String title, String description, String type) {
		this(id, title, description, type, new ArrayList<>(), "", 0, 0, false, 0);
	}

	public Recipe(int id, String title, String description, String type, List<Ingredient> ingredients) {
		this(id, title, description, type, ingredients, "", 0, 0, false, 0);
	}

	public Recipe(int id, String title, String description, String type, List<Ingredient> ingredients,
				  int rating, String difficultyLevel, int preparationTime) {
		this(id, title, description, type, ingredients, difficultyLevel, preparationTime, rating, false, 0);
	}

	public Recipe(int id, String title, int rating, String type, String difficultyLevel, String description,
				  List<Ingredient> ingredients, int preparationTime, boolean favourite, int feedback) {
		this(id, title, description, type, ingredients, difficultyLevel, preparationTime, rating, favourite, feedback);
	}

	public int getId() {
		return this.id;
	}

	public ArrayList<String> getIngredientNames() {
		ArrayList<String> names = new ArrayList<>();
		for (Ingredient currentIngredient :ingredients)
			names.add(currentIngredient.name);
		return names;
	}

}
