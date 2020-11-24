package com.example.ny_times_articles.ui.articles

import com.example.ny_times_articles.service.model.Article

sealed class UiState {
    object Loading : UiState()
    data class Success(
        val articles: List<Article>
    ) : UiState()

    data class Error(val message: String) : UiState()
}