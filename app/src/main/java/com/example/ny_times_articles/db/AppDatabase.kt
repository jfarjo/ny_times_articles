package com.example.ny_times_articles.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ny_times_articles.model.Article

@Database(entities = [Article::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
}