package com.example.ny_times_articles.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ny_times_articles.service.model.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(article: List<Article>)

    @Query("SELECT * FROM articles")
    fun getArticles(): List<Article>
}