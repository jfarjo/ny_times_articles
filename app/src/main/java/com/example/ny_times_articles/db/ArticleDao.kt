package com.example.ny_times_articles.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ny_times_articles.model.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(article: Article)

    @Query("SELECT * FROM articles")
    fun getAll(): List<Article>
}