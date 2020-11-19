package com.example.ny_times_articles.networking

import androidx.lifecycle.MutableLiveData
import com.example.ny_times_articles.model.ArticlesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticlesRepository {

    private val articlesAPI =
        RetrofitInstance.getRetrofitInstance()?.create(APIEndpoints::class.java)!!

    fun getArticles(apiKey: String): MutableLiveData<ArticlesResponse> {
        val articlesData = MutableLiveData<ArticlesResponse>()
        articlesAPI.getArticles(apiKey).enqueue(object : Callback<ArticlesResponse?> {
            override fun onResponse(
                call: Call<ArticlesResponse?>,
                response: Response<ArticlesResponse?>
            ) {
                if (response.isSuccessful) {
                    articlesData.value = response.body()
                }
            }

            override fun onFailure(call: Call<ArticlesResponse?>, t: Throwable) {
                articlesData.value = null
            }
        })
        return articlesData
    }

    companion object {
        private var articlesRepository: ArticlesRepository? = null
        val instance: ArticlesRepository?
            get() {
                if (articlesRepository == null) {
                    articlesRepository = ArticlesRepository()
                }
                return articlesRepository
            }
    }
}