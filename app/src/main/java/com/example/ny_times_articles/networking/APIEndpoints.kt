package com.example.ny_times_articles.networking

import com.example.ny_times_articles.model.ArticlesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIEndpoints {

    @GET("mostpopular/v2/mostviewed/all-sections/7.json")
    fun getArticles(@Query("api-key") apiKey: String): Call<ArticlesResponse>
}