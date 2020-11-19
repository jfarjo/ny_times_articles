package com.example.ny_times_articles.activities

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.ny_times_articles.R
import com.example.ny_times_articles.model.Article
import kotlinx.android.synthetic.main.activity_article_details.*
import kotlinx.android.synthetic.main.header_layout.*

class ArticleDetailsActivity : AppCompatActivity() {

    lateinit var activity: Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_details)

        iv_left.visibility = View.VISIBLE

        activity = this

        val data = intent.getSerializableExtra("Data") as Article
        tv_toolbarTitle.text = data.title

        tv_source.text = data.source
        tv_published_date.text = data.published_date
        tv_section.text = data.section
        tv_by.text = data.byline
        tv_type.text = data.type
        tv_articleTtitle.text = data.title
        tv_abstract.text = data.abstract

        // region "Listeners"
        iv_left.setOnClickListener {
            finish()
        }
        // endregion
    }
}