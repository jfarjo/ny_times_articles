package com.example.ny_times_articles

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.ny_times_articles.model.Article
import com.example.ny_times_articles.model.ArticlesResponse
import com.example.ny_times_articles.networking.APIEndpoints
import com.example.ny_times_articles.networking.RetrofitInstance
import com.example.ny_times_articles.utils.Constants
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.CountDownLatch

@RunWith(AndroidJUnit4::class)
class GetArticlesUseCaseTest {

    private val latch: CountDownLatch = CountDownLatch(1)
    private var apiManager: APIEndpoints? = null
    private var articles: List<Article>? = null

    @Before
    fun beforeTest() {
        apiManager = RetrofitInstance.getRetrofitInstance()?.create(APIEndpoints::class.java)!!
    }

    @Test
    @Throws(InterruptedException::class)
    fun test_login() {
        Assert.assertNotNull(apiManager)

        apiManager?.getArticles(Constants.NY_API_KEY)
            ?.enqueue(object : Callback<ArticlesResponse?> {
                override fun onResponse(
                    call: Call<ArticlesResponse?>,
                    response: Response<ArticlesResponse?>
                ) {
                    if (response.isSuccessful) {
                        articles = response.body()!!.articles
                        latch.countDown()
                    }
                }

                override fun onFailure(call: Call<ArticlesResponse?>, t: Throwable) {
                    latch.countDown()
                }
            })

        latch.await()
        Assert.assertNotNull(articles)
    }

    @After
    fun afterTest() {
        articles = null
    }
}