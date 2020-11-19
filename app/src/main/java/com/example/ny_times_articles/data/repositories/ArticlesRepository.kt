package com.example.ny_times_articles.data.repositories

import com.example.ny_times_articles.db.AppDatabase
import com.example.ny_times_articles.data.model.Article
import com.example.ny_times_articles.data.model.ArticlesResponse
import com.example.ny_times_articles.data.network.MyApi

class ArticlesRepository(
    private val api: MyApi,
    private val db: AppDatabase
) : SafeApiRequest() {

    suspend fun getArticles(apiKey: String): ArticlesResponse {
        return apiRequest { api.getArticles(apiKey) }
    }

    suspend fun saveArticles(articles: List<Article>) = db.getArticleDao().insert(articles)

    fun getCachedArticles() = db.getArticleDao().getArticles()

}