package com.example.ny_times_articles

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.ny_times_articles.service.model.Article
import com.example.ny_times_articles.view.ui.ArticleDetailsFragment
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@LargeTest
@Config(sdk = [Build.VERSION_CODES.P])
@RunWith(AndroidJUnit4::class)
class ArticleDetailsActivityTest {
    @Test
    fun test_articleDetails_isDetailsDisplayed() {
        val article = Article()
        article.source = "New York Times"

        val bundle = Bundle()
        bundle.putSerializable("Data", article)

        val scenario = launchFragmentInContainer<ArticleDetailsFragment>(bundle)
        scenario.recreate()

        onView(withId(R.id.tv_source)).check(matches(withText("New York Times")))
    }
}