package com.example.ny_times_articles.data.model

import com.google.gson.annotations.SerializedName

class ArticlesResponse {

    @SerializedName("status")
    var status = ""

    @SerializedName("copyright")
    var copyright = ""

    @SerializedName("num_results")
    var num_results = 0

    @SerializedName("results")
    var articles: List<Article> = emptyList()
}