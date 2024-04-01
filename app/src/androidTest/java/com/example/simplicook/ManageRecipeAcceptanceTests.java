package com.example.simplicook;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;

import android.os.SystemClock;
import android.view.KeyEvent;
import android.widget.EditText;

import androidx.appcompat.widget.SearchView;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import com.example.simplicook.presentation.HomePageActivity;
import com.example.simplicook.utils.TestUtils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
public class ManageRecipeAcceptanceTests {

    @Rule
    public ActivityScenarioRule<HomePageActivity> rule = new ActivityScenarioRule<>(HomePageActivity.class);
    private TestUtils utils;

    @Before
    public void utilSetUp() {
        utils = new TestUtils();
    }

    @Test
    public void AddNewRecipe() {

        String recipeTitle = "Green Apple";
        String type = "Fruit";
        String time = "2";
        String difficulty = "Hard";
        String description = "Grab apple from kitchen counter.";
        String ingredient = "Granny Smith";
        String unit = "1";

        onView(withId(R.id.ButtonAdd)).perform(click());

        onView(withId(R.id.editRecipeName)).perform(typeText(recipeTitle));
        onView(withId(R.id.editType)).perform(clearText(),typeText(type));
        onView(withId(R.id.preparationTime)).perform(typeText(String.valueOf(time)));
        onView(withId(R.id.difficultyLevel)).perform(click());
        onView(withText(difficulty)).perform(click());
        onView(withId(R.id.editRecipeDescription)).perform(clearText(),typeText(description));

        SystemClock.sleep(1000);
        onView(withId(R.id.addIngredientButton)).perform(click());
        onView(allOf(isAssignableFrom(EditText.class),
                withHint("Name of ingredient")))
                .perform(click(), typeText(ingredient));
        onView(allOf(isAssignableFrom(EditText.class),
                withHint("Unit of measurement")))
                .perform(click(), typeText(unit),ViewActions.closeSoftKeyboard());

        onView(withId(R.id.submitRecipeButton)).perform(click());
        SystemClock.sleep(1000);

        onView(allOf(withId(R.id.homePageRecyclerView),hasToString(recipeTitle)));

        SystemClock.sleep(1000);

        onView(withId(R.id.searchBar)).perform(click());
        SystemClock.sleep(1000);

        onView(instanceOf(SearchView.SearchAutoComplete.class)).perform(typeText(recipeTitle),pressKey(KeyEvent.KEYCODE_ENTER));

        onView(allOf(withId(R.id.homePageRecyclerView))).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));

        onView(withText(recipeTitle)).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.textViewRecipeName)).check(matches(withText(recipeTitle)));
        onView(withId(R.id.textViewRecipeType)).check(matches(withText(type)));
        onView(withId(R.id.textViewTimeNeeded)).check(matches(withText(containsString(time))));
        onView(withId(R.id.textViewDifficulty)).check(matches(withText(difficulty)));
        onView(withId(R.id.textViewRecipeDescription)).check(matches(withText(containsString(description))));
        onView(withId(R.id.textViewIngredients)).check(matches(withText(ingredient + " " + unit + "\n")));

    }

    @Test
    public void EditExistingRecipe() {

        String newTitle = "New title";
        String newType = "New type";
        String addToDescription = " added description ";

        onView(allOf(withId(R.id.homePageRecyclerView))).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        SystemClock.sleep(2000);
        onView(withId(R.id.buttonEdit)).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.editRecipeName)).perform(clearText(),typeText(newTitle));
        onView(withId(R.id.editType)).perform(clearText(),typeText(newType));
        onView(withId(R.id.editRecipeDescription)).perform(typeText(addToDescription),ViewActions.closeSoftKeyboard());
        SystemClock.sleep(1000);
        onView(withId(R.id.submitRecipeButton)).perform(click());
        SystemClock.sleep(1000);
        onView(allOf(withId(R.id.homePageRecyclerView))).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        SystemClock.sleep(2000);

        onView(allOf(withId(R.id.homePageRecyclerView),hasToString(newTitle)));

        SystemClock.sleep(1000);
        //Go back to view to check values have successfully updated.
        onView(withText(newTitle)).perform(click());
        SystemClock.sleep(1000);

        //Check updated strings
        onView(withId(R.id.textViewRecipeName)).check(matches(withText(newTitle)));
        onView(withId(R.id.textViewRecipeType)).check(matches(withText(newType)));
        onView(withId(R.id.textViewRecipeDescription)).check(matches(withText(containsString(addToDescription))));

    }

    @Test
    public void DeleteRecipe() {
        int currRecipeAmount = utils.numRecipes();

        onView(allOf(withId(R.id.homePageRecyclerView))).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        SystemClock.sleep(2000);
        onView(withId(R.id.buttonDelete)).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.confirmDelete)).perform(click());
        SystemClock.sleep(2000);

        utils = new TestUtils();
        int newAmount = utils.numRecipes();
        assertEquals(currRecipeAmount-1,newAmount);

    }

}
