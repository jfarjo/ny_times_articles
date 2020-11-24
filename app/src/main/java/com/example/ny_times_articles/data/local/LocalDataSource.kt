package com.example.ny_times_articles.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ny_times_articles.service.model.Article

@Database(
    entities = [Article::class],
    version = 1
)
abstract class LocalDataSource : RoomDatabase() {

    abstract fun getArticleDao(): ArticleDao

    companion object {

        @Volatile
        private var instance: LocalDataSource? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                LocalDataSource::class.java,
                "MyDatabase.db"
            ).build()
    }
}