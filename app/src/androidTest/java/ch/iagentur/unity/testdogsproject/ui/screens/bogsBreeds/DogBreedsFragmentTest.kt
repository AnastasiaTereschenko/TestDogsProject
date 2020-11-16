package ch.iagentur.unity.testdogsproject.ui.screens.bogsBreeds

import android.content.Intent
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import ch.iagentur.unity.testdogsproject.R
import ch.iagentur.unity.testdogsproject.ui.screens.main.MainActivity
import ch.iagentur.unity.testdogsproject.ui.viewHolder.DogBreedsViewHolder
import org.junit.Before
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule


class DogBreedsFragmentTest {
    companion object {
        @get:Rule
        var fragmentRule = FragmentTestRule<MainActivity, Fragment>(
            MainActivity::class.java,
            DogBreedsFragment()
        )

        @get:Rule
        var activityTestRule = ActivityTestRule(
            MainActivity::class.java
        )

        @get: ClassRule
        var chain: TestRule =
            RuleChain.outerRule(activityTestRule)
                .around(fragmentRule)
    }

    @Before
    fun setUp() {
    }

    @Test
    fun test() {
        activityTestRule.launchActivity(Intent())
        Thread.sleep(2000)
        val textFirstItemRecyclerView = activityTestRule.activity
            .findViewById<RecyclerView>(R.id.fdbDogBreedsRecyclerView)
            .findViewHolderForAdapterPosition(0)?.itemView
            ?.findViewById<TextView>(R.id.rdbiDogBreedsNameTextView)?.text
        onView(withId(R.id.fdbDogBreedsRecyclerView)).perform(
            RecyclerViewActions
                .actionOnItemAtPosition<DogBreedsViewHolder>(0, click())
        )
        Thread.sleep(2000)
        onView(withId(R.id.fdbiDogBreedNameTextView)).check(matches(withText(textFirstItemRecyclerView.toString())))
    }
}