package com.example.ny_times_articles.db

import android.content.Context
import androidx.room.Room

class DatabaseClient private constructor(mCtx: Context) {

    private val appDatabase: AppDatabase =
        Room.databaseBuilder(mCtx, AppDatabase::class.java, "MyArticles").build()

    fun getAppDatabase(): AppDatabase {
        return appDatabase
    }

    companion object {
        private var mInstance: DatabaseClient? = null

        @Synchronized
        fun getInstance(mCtx: Context): DatabaseClient? {
            if (mInstance == null) {
                mInstance = DatabaseClient(mCtx)
            }
            return mInstance
        }
    }
}