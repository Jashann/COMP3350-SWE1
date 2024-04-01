package com.example.simplicook;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.os.SystemClock;
import android.view.KeyEvent;

import androidx.appcompat.widget.SearchView;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.filters.LargeTest;

import com.example.simplicook.objects.Recipe;
import com.example.simplicook.presentation.HomePageActivity;
import com.example.simplicook.utils.TestUtils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
public class HomeViewAcceptanceTests {


    @Rule
    public ActivityScenarioRule<HomePageActivity> rule = new ActivityScenarioRule<>(HomePageActivity.class);
    private TestUtils utils;

    @Before
    public void utilSetUp() {
        utils = new TestUtils();
    }

    @Test
    public void SearchHomePage() {

        onView(withId(R.id.searchBar)).perform(click());
        SystemClock.sleep(1000);
        onView(instanceOf(SearchView.SearchAutoComplete.class)).perform(typeText("Chicken"),pressKey(KeyEvent.KEYCODE_ENTER));
        SystemClock.sleep(1000);

        onView(allOf(withId(R.id.homePageRecyclerView),hasToString("Garlic Chicken")));

        onView(allOf(withId(R.id.homePageRecyclerView))).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        SystemClock.sleep(1000);

        Recipe chickenRecipe = utils.getRecipePosition(3);
        onView(withId(R.id.textViewRecipeName)).check(matches(withText(chickenRecipe.title)));

    }

    @Test
    public void FavouriteRecipe() {
        SystemClock.sleep(1000);

        onView(allOf(withId(R.id.homePageRecyclerView))).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        SystemClock.sleep(1000);

        onView(withId(R.id.favourite_button)).check(matches(isDisplayed()));
        onView(withId(R.id.favourite_button)).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.buttonReturn)).perform(click());

        Recipe chickenRecipe = utils.getRecipePosition(0);
        assertTrue(chickenRecipe.favourite);

        onView(withId(R.id.sortBy)).perform(click());
        SystemClock.sleep(1000);
        onView(withText("Favourited")).perform(click());

        onView(allOf(withId(R.id.homePageRecyclerView),hasToString(chickenRecipe.title)));

        SystemClock.sleep(2000);

    }

    @Test
    public void SortRecipes() {
        onView(withId(R.id.sortBy)).perform(click());
        onView(withText("Lowest prepare time to Highest prepare time")).perform(click());
        SystemClock.sleep(2000);

        ArrayList<Recipe> sorted = utils.getAllRecipes();
        for(int i = 0; i < sorted.size() - 1; i++) {
            assertTrue(sorted.get(i).preparationTime <= sorted.get(i).preparationTime);
        }

        onView(withId(R.id.sortBy)).perform(click());
        onView(withText("Highest rating to lowest rating")).perform(click());
        SystemClock.sleep(2000);

        sorted = utils.getAllRecipes();
        for(int i = 0; i < sorted.size() - 1; i++) {
            assertTrue(sorted.get(i).rating >= sorted.get(i).rating);
        }

        onView(withId(R.id.sortBy)).perform(click());
        onView(withText("Easy to Hard")).perform(click());
        SystemClock.sleep(2000);


        onView(withId(R.id.sortBy)).perform(click());
        onView(withText("Favourited")).perform(click());
        SystemClock.sleep(2000);

    }

    @Test
    public void RateRecipe() {
        onView(allOf(withId(R.id.homePageRecyclerView))).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        SystemClock.sleep(1000);

        onView(withId(R.id.ratingBarFeedback)).perform(click());
        SystemClock.sleep(1000);

        Recipe recipe = utils.getRecipePosition(0);
        assertEquals(3,recipe.feedback);

        onView(withId(R.id.buttonReturn)).perform(click());
        SystemClock.sleep(500);

        onView(allOf(withId(R.id.homePageRecyclerView))).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        SystemClock.sleep(1000);

    }

    @Test
    public void PlanRecipe() {
        onView(allOf(withId(R.id.homePageRecyclerView))).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        SystemClock.sleep(1000);

        onView(withId(R.id.addPlans)).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.buttonReturn)).perform(click());
        SystemClock.sleep(500);

        onView(withId(R.id.ButtonToPlan)).perform(click());
        SystemClock.sleep(1000);

        ArrayList<Recipe> displayedPlan = utils.plannedRecipeOnDisplay();
        assertEquals(1,displayedPlan.size());

        onView(withId(R.id.planningContainer)).check(matches(isDisplayed()));

        onView(withText("Remove")).perform(click());
        displayedPlan = utils.plannedRecipeOnDisplay();
        assertEquals(0,displayedPlan.size());

    }

}

