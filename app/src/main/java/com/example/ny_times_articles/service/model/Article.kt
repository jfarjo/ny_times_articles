package com.example.ny_times_articles.service.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "articles")
class Article : Serializable {

    @PrimaryKey
    @SerializedName("id")
    @ColumnInfo(name = "id")
    var id = 0.toLong()

    @SerializedName("source")
    @ColumnInfo(name = "source")
    var source: String? = null

    @SerializedName("published_date")
    @ColumnInfo(name = "published_date")
    var published_date: String? = null

    @SerializedName("section")
    @ColumnInfo(name = "section")
    var section: String? = null

    @SerializedName("byline")
    @ColumnInfo(name = "byline")
    var byline: String? = null

    @SerializedName("type")
    @ColumnInfo(name = "type")
    var type: String? = null

    @SerializedName("title")
    @ColumnInfo(name = "title")
    var title: String? = null

    @SerializedName("abstract")
    @ColumnInfo(name = "abstract")
    var abstract: String? = null
}