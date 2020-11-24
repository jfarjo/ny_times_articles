package com.example.ny_times_articles.data.remote

import com.example.ny_times_articles.service.model.ArticlesResponse
import com.example.ny_times_articles.utilities.NetworkConnectionInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteDataSource {

    @GET("mostpopular/v2/mostviewed/all-sections/7.json")
    suspend fun getArticles(@Query("api-key") apiKey: String): Response<ArticlesResponse>

    companion object {
        private const val BASE_URL = "http://api.nytimes.com/svc/"

        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ): RemoteDataSource {

            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

            val okkHttpclient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okkHttpclient)
                .client(client)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RemoteDataSource::class.java)
        }
    }
}