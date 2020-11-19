package com.example.ny_times_articles.ui.articles

import com.example.ny_times_articles.data.model.Article

interface OnItemClickListener {
    fun onItemClick(article: Article)
}