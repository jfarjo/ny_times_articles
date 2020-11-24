package com.example.ny_times_articles.service.repository

import androidx.lifecycle.MutableLiveData
import com.example.ny_times_articles.service.model.Article
import com.example.ny_times_articles.data.remote.RemoteDataSource
import com.example.ny_times_articles.data.local.LocalDataSource
import com.example.ny_times_articles.ui.articles.UiState
import com.example.ny_times_articles.utilities.Constants
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class ArticlesRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : SafeApiRequest() {

    suspend fun getArticles(isForceRefresh: Boolean, uiState: MutableLiveData<UiState>) {
        if (isForceRefresh) {
            getRemoteArticles(uiState)
        } else {
            val articles = getLocalArticles()
            if (articles.isNotEmpty()) {
                coroutineScope {
                    launch {
                        uiState.postValue(UiState.Success(articles))
                    }
                }
                getRemoteArticles(uiState)
            } else {
                getRemoteArticles(uiState)
            }
        }
    }

    private suspend fun getRemoteArticles(uiState: MutableLiveData<UiState>) {
        coroutineScope {
            launch {
                uiState.postValue(UiState.Loading)
            }
        }
        val articles = apiRequest { remoteDataSource.getArticles(Constants.NY_API_KEY) }.articles
        localDataSource.getArticleDao().insert(articles)
        coroutineScope {
            launch {
                uiState.postValue(UiState.Success(articles))
            }
        }
    }

    private fun getLocalArticles(): List<Article> {
        return localDataSource.getArticleDao().getArticles()
    }
}