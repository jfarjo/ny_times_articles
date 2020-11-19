package com.example.ny_times_articles.ui.articles

import androidx.lifecycle.ViewModel
import com.example.ny_times_articles.data.model.Article
import com.example.ny_times_articles.data.repositories.ArticlesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ArticlesViewModel(
    private val repository: ArticlesRepository
) : ViewModel() {

    fun getCachedArticles() = repository.getCachedArticles()

    suspend fun getArticles(
        apiKey: String
    ) = withContext(Dispatchers.IO) { repository.getArticles(apiKey) }

    suspend fun saveArticles(articles: List<Article>) = repository.saveArticles(articles)

}