package com.example.ny_times_articles.view.callback

import com.example.ny_times_articles.service.model.Article

interface OnItemClickListener {
    fun onItemClick(article: Article)
}