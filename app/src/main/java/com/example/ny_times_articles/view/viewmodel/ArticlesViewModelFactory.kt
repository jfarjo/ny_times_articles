package com.example.ny_times_articles.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ny_times_articles.view.ArticlesUseCase

@Suppress("UNCHECKED_CAST")
class ArticlesViewModelFactory(
    private val useCase: ArticlesUseCase
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ArticlesViewModel(useCase) as T
    }
}