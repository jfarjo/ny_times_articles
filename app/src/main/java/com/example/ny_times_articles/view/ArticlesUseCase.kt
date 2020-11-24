package com.example.ny_times_articles.view

import androidx.lifecycle.MutableLiveData
import com.example.ny_times_articles.service.repository.ArticlesRepository
import com.example.ny_times_articles.ui.articles.UiState

class ArticlesUseCase(
    private val repository: ArticlesRepository
) {

    suspend fun getArticles(isForceRefresh: Boolean, uiState: MutableLiveData<UiState>) {
        return repository.getArticles(isForceRefresh, uiState)
    }
}