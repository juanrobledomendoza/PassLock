package com.example.passlock;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class LandingPageTest {

    @Before
    public void setUp() {
        // Clear shared preferences before each test
        Context context = ApplicationProvider.getApplicationContext();
        SharedPreferences prefs = context.getSharedPreferences(MainActivity.PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().clear().commit();
    }

    @Test
    public void testUserLandingPage_DisplaysUserButtons() {
        // Set user preferences
        Context context = ApplicationProvider.getApplicationContext();
        SharedPreferences prefs = context.getSharedPreferences(MainActivity.PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("isAdmin", false);
        editor.putString("username", "testUser");
        editor.commit();

        // Launch the activity after setting preferences
        try (ActivityScenario<LandingPage> scenario = ActivityScenario.launch(LandingPage.class)) {
            // Check that user buttons are displayed
            onView(withId(R.id.testPasswordBtn)).check(matches(isDisplayed()));
            onView(withId(R.id.suggestPassLockBtn)).check(matches(isDisplayed()));
            onView(withId(R.id.previousPassLockBtn)).check(matches(isDisplayed()));
            onView(withId(R.id.comparePassLockBtn)).check(matches(isDisplayed()));

            // Check that admin buttons do NOT exist
            onView(withId(R.id.viewUserPasslocksBtn)).check(doesNotExist());
            onView(withId(R.id.editSuggestedPasslocksBtn)).check(doesNotExist());
            onView(withId(R.id.compareUserPasslocksBtn)).check(doesNotExist());
        }
    }
}
