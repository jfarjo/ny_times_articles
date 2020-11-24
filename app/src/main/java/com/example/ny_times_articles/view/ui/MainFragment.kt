package com.example.ny_times_articles.view.ui

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.VisibleForTesting
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.ny_times_articles.R
import com.example.ny_times_articles.view.adapter.ArticlesAdapter
import com.example.ny_times_articles.service.model.Article
import com.example.ny_times_articles.ui.articles.UiState
import com.example.ny_times_articles.utilities.DividerItemDecorator
import com.example.ny_times_articles.view.callback.OnItemClickListener
import com.example.ny_times_articles.view.viewmodel.ArticlesViewModel
import com.example.ny_times_articles.view.viewmodel.ArticlesViewModelFactory
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.header_layout.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class MainFragment : Fragment(R.layout.fragment_main), OnItemClickListener, KodeinAware {

    override val kodein by kodein()
    private val factory: ArticlesViewModelFactory by instance()

    lateinit var activity: Activity
    var articles: ArrayList<Article> = ArrayList()
    lateinit var articlesAdapter: ArticlesAdapter

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    lateinit var viewModel: ArticlesViewModel
    var navController: NavController? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tv_toolbarTitle.text = getString(R.string.app_name)

        activity = getActivity() as FragmentActivity

        navController = Navigation.findNavController(activity, R.id.nav_host_fragment)

        viewModel = ViewModelProviders.of(this, factory).get(ArticlesViewModel::class.java)

        viewModel.uiState().observe(viewLifecycleOwner, { uiState ->
            if (uiState != null) {
                render(uiState)
            }
        })

        setupRecyclerView()
        getArticles()

        // region "Listeners"
        srl_articles.setOnRefreshListener {
            getArticles(true)
        }
        // endregion
    }

    private fun setupRecyclerView() {
        articlesAdapter = ArticlesAdapter(activity, articles, this)
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

    private fun render(uiState: UiState) {
        when (uiState) {
            is UiState.Loading -> {
                srl_articles.isRefreshing = true
            }
            is UiState.Success -> {
                srl_articles.isRefreshing = false
                articles.clear()
                articles.addAll(uiState.articles)
                articlesAdapter.notifyDataSetChanged()
            }
            is UiState.Error -> {
                srl_articles.isRefreshing = false
                Toast.makeText(activity, getString(R.string.sth_wrong), Toast.LENGTH_SHORT).show()
            }
        }
    }

    // region "API Calls"
    private fun getArticles(isForceRefresh: Boolean = false) {
        lifecycleScope.launch {
            viewModel.getArticles(isForceRefresh)
        }
    }
    // endregion

    // region "Override Functions"
    override fun onItemClick(article: Article) {
        val bundle = Bundle()
        bundle.putSerializable("Data", article)
        navController?.navigate(R.id.action_mainFragment_to_articleDetailsFragment, bundle)
    }
    // endregion
}