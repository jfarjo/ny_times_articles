package com.example.ny_times_articles.ui.articles

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.ny_times_articles.R
import com.example.ny_times_articles.data.model.Article
import kotlinx.android.synthetic.main.fragment_article_details.*
import kotlinx.android.synthetic.main.header_layout.*

class ArticleDetailsFragment : Fragment(R.layout.fragment_article_details) {

    private lateinit var activity: Activity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        iv_left.visibility = View.VISIBLE

        activity = getActivity() as FragmentActivity

        val data = arguments?.getSerializable("Data") as Article
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
            activity.onBackPressed()
        }
        // endregion
    }
}