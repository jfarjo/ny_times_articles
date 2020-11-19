package com.example.ny_times_articles.adapters

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ny_times_articles.R
import com.example.ny_times_articles.activities.ArticleDetailsActivity
import com.example.ny_times_articles.model.Article
import kotlinx.android.synthetic.main.row_article.view.*

class ArticlesAdapter(private var activity: Activity, private var myDataSet: List<Article>) :
    RecyclerView.Adapter<ArticlesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(activity).inflate(R.layout.row_article, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = myDataSet[position]

        holder.tvTitle.text = article.title
        holder.tvBy.text = article.byline
        holder.tvDate.text = article.published_date

        holder.itemView.setOnClickListener {
            val intent = Intent(activity, ArticleDetailsActivity::class.java)
            intent.putExtra("Data", article)
            activity.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return myDataSet.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.tv_title
        val tvBy: TextView = itemView.tv_by
        val tvDate: TextView = itemView.tv_date
    }
}