package com.example.ny_times_articles.activities

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import com.example.ny_times_articles.R
import com.example.ny_times_articles.adapters.ArticlesAdapter
import com.example.ny_times_articles.db.DatabaseClient
import com.example.ny_times_articles.model.Article
import com.example.ny_times_articles.utils.DividerItemDecorator
import com.example.ny_times_articles.viewmodels.ArticlesViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.header_layout.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var activity: Activity
    var articles: ArrayList<Article> = ArrayList()
    lateinit var articlesAdapter: ArticlesAdapter
    private var articlesViewModel: ArticlesViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv_toolbarTitle.text = getString(R.string.app_name)

        activity = this

        articlesViewModel = ViewModelProviders.of(this).get(ArticlesViewModel::class.java)
        articlesViewModel?.init()

        setupRecyclerView()
        getCashedArticles()

        // region "Listeners"
        srl_articles.setOnRefreshListener {
            getArticles()
        }
        // endregion
    }

    private fun setupRecyclerView() {
        articlesAdapter = ArticlesAdapter(activity, articles)
        rv_articles.addItemDecoration(
            DividerItemDecorator(
                ContextCompat.getDrawable(
                    activity,
                    R.drawable.list_divider
                )!!
            )
        )
        rv_articles.adapter = articlesAdapter
    }

    private fun getCashedArticles() {
        GlobalScope.launch(Dispatchers.IO) {
            val articlesList = DatabaseClient.getInstance(applicationContext)!!.getAppDatabase()
                .articleDao()
                .getAll()
            launch(Dispatchers.Main) {
                if (articlesList.isNullOrEmpty()) {
                    getArticles()
                } else {
                    articles.clear()
                    articles.addAll(articlesList)
                    articlesAdapter.notifyDataSetChanged()
                    getArticles()
                }
            }
        }
    }

    private fun insertToRoom(articles: List<Article>) {
        GlobalScope.launch(Dispatchers.IO) {
            val appDatabase = DatabaseClient.getInstance(applicationContext)!!.getAppDatabase()
            articles.forEach {
                appDatabase.articleDao().insert(it)
            }
        }
    }

    // region "API Calls"
    private fun getArticles() {
        srl_articles.isRefreshing = true
        articlesViewModel?.getArticlesRepository()?.observe(this) { articlesResponse ->
            srl_articles.isRefreshing = false
            insertToRoom(articlesResponse.articles)
            articles.clear()
            articles.addAll(articlesResponse.articles)
            articlesAdapter.notifyDataSetChanged()
        }
    }
    // endregion
}