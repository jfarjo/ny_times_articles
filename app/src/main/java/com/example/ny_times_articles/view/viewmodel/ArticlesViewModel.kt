package com.example.ny_times_articles.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ny_times_articles.ui.articles.UiState
import com.example.ny_times_articles.view.ArticlesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ArticlesViewModel(
    private val useCase: ArticlesUseCase
) : ViewModel() {

    fun uiState(): LiveData<UiState> = uiState
    private val uiState: MutableLiveData<UiState> = MutableLiveData()

    suspend fun getArticles(isForceRefresh: Boolean = false) =
        withContext(Dispatchers.IO) { useCase.getArticles(isForceRefresh, uiState) }
}