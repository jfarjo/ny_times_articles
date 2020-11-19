package com.example.ny_times_articles

import android.os.Build
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import com.example.ny_times_articles.activities.MainActivity
import com.example.ny_times_articles.adapters.ArticlesAdapter
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@LargeTest
@Config(sdk = [Build.VERSION_CODES.P])
@RunWith(RobolectricTestRunner::class)
class ArticleDetailsActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_selectListItem_isArticlesRecycleViewVisible() {
        // Click list item #LIST_ITEM_IN_TEST
        onView(withId(R.id.rv_articles)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ArticlesAdapter.ViewHolder>(1, click())
        )

        pressBack()

        // Confirm Articles RecycleView iis visible
        onView(withId(R.id.rv_articles)).check(matches(isDisplayed()))
    }
}