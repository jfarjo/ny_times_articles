package com.example.ny_times_articles.ui.articles

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
import com.example.ny_times_articles.data.model.Article
import com.example.ny_times_articles.utils.ApiException
import com.example.ny_times_articles.utils.Constants
import com.example.ny_times_articles.utils.DividerItemDecorator
import com.example.ny_times_articles.utils.NoInternetException
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.header_layout.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
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

        navController = Navigation.findNavController(view)

        viewModel = ViewModelProviders.of(this, factory).get(ArticlesViewModel::class.java)

        viewModel.getCachedArticles().observe(viewLifecycleOwner, { response ->
            articles.clear()
            articles.addAll(response)
            articlesAdapter.notifyDataSetChanged()
        })

        setupRecyclerView()
        getArticles()
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

    // region "API Calls"
    private fun getArticles() {
        lifecycleScope.launch {
            try {
                val authResponse = viewModel.getArticles(Constants.NY_API_KEY)
                GlobalScope.launch(Dispatchers.IO) {
                    viewModel.saveArticles(authResponse.articles)
                }
            } catch (e: ApiException) {
                Toast.makeText(activity, e.toString(), Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            } catch (e: NoInternetException) {
                Toast.makeText(activity, e.toString(), Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
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