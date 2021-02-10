package ch.iagentur.unity.testdogsproject.ui.screens.bogsBreeds

import android.app.Activity
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import ch.iagentur.unity.testdogsproject.R
import ch.iagentur.unity.testdogsproject.misc.test.EspressoIdlingResource
import ch.iagentur.unity.testdogsproject.ui.screens.main.MainActivity
import ch.iagentur.unity.testdogsproject.ui.viewHolder.DogBreedsViewHolder
import org.junit.After
import org.junit.Before
import org.junit.Test


class DogBreedsFragmentTest {

    lateinit var lActivity: Activity

    @Before
    fun registerIdlingResource() {
        val activityScenario: ActivityScenario<*> = ActivityScenario.launch(
            MainActivity::class.java
        )
        activityScenario.onActivity { activity ->
            lActivity = activity
            IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
        }
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun test() {
        onView(withId(R.id.fdbDogBreedsRecyclerView)).check(matches(isDisplayed()))
        val textFirstItemRecyclerViewString = lActivity
            .findViewById<RecyclerView>(R.id.fdbDogBreedsRecyclerView)
            ?.findViewHolderForAdapterPosition(0)?.itemView
            ?.findViewById<TextView>(R.id.rdbiDogBreedsNameTextView)?.text.toString()
        onView(withId(R.id.fdbDogBreedsRecyclerView)).perform(
            RecyclerViewActions
                .actionOnItemAtPosition<DogBreedsViewHolder>(0, click())
        )
        onView(withId(R.id.fdbiDogBreedNameTextView)).check(
            matches(
                withText(
                    textFirstItemRecyclerViewString
                )
            )
        )
    }
}