package com.example.ny_times_articles.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ny_times_articles.model.ArticlesResponse
import com.example.ny_times_articles.networking.ArticlesRepository
import com.example.ny_times_articles.utils.Constants

class ArticlesViewModel : ViewModel() {

    private var mutableLiveData: MutableLiveData<ArticlesResponse>? = null
    private var articlesRepository: ArticlesRepository? = null

    fun init() {
        if (mutableLiveData != null) {
            return
        }
        articlesRepository = ArticlesRepository.instance
        mutableLiveData = articlesRepository?.getArticles(Constants.NY_API_KEY)
    }

    fun getArticlesRepository(): LiveData<ArticlesResponse>? {
        return mutableLiveData
    }
}