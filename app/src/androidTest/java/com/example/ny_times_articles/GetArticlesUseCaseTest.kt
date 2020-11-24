package com.example.ny_times_articles

import androidx.fragment.app.testing.launchFragment
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.navigation.Navigation
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.ny_times_articles.view.ui.ArticleDetailsFragment
import com.example.ny_times_articles.view.ui.MainFragment
import com.squareup.okhttp.mockwebserver.MockResponse
import com.squareup.okhttp.mockwebserver.MockWebServer
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import java.io.File
import java.net.HttpURLConnection

@RunWith(AndroidJUnit4::class)
open class GetArticlesUseCaseTest : KoinTest {

    private lateinit var mMockServerInstance: MockWebServer
    private var mShouldStart = false

    @Before
    open fun setUp() {
        startMockServer(true)
    }

    private fun mockNetworkResponseWithFileContent(fileName: String, responseCode: Int) =
        mMockServerInstance.enqueue(
            MockResponse()
                .setResponseCode(responseCode)
                .setBody(getJson(fileName))
        )

    private fun getJson(path: String): String {
        val uri = javaClass.classLoader!!.getResource(path)
        val file = File(uri.path)
        return String(file.readBytes())
    }

    private fun startMockServer(shouldStart: Boolean) {
        if (shouldStart) {
            mShouldStart = shouldStart
            mMockServerInstance = MockWebServer()
            mMockServerInstance.start()
        }
    }

    fun getMockWebServerUrl() = mMockServerInstance.url("/").toString()

    private fun stopMockServer() {
        if (mShouldStart) {
            mMockServerInstance.shutdown()
        }
    }

    @Test
    fun test_articles_repo_retrieves_expected_data(): Unit = runBlocking {
        val scenario = launchFragmentInContainer<MainFragment>()
        scenario.moveToState(Lifecycle.State.CREATED)
        scenario.onFragment { fragment ->
            mockNetworkResponseWithFileContent("text.json", HttpURLConnection.HTTP_OK)
            assertNotNull(fragment.articles)
            assertEquals(fragment.articles[0].source, "New York Times")
        }
    }

    @After
    open fun tearDown() {
        //Stop Mock server
        stopMockServer()
    }
}