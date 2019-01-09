package com.aytlo.tony.kotlin_coroutines.presentation.ui.news_feed

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.aytlo.tony.kotlin_coroutines.R
import com.aytlo.tony.kotlin_coroutines.presentation.core.BaseFragment
import com.aytlo.tony.kotlin_coroutines.presentation.core.extension.unsafeLazy
import com.aytlo.tony.kotlin_coroutines.presentation.core.extension.viewModel
import com.aytlo.tony.kotlin_coroutines.presentation.ui.news_feed.adapter.NewsPaginationAdapter
import com.aytlo.tony.kotlin_coroutines.presentation.util.PaginationScrollListener
import kotlinx.android.synthetic.main.fragment_news_feed.*
import kotlinx.coroutines.ObsoleteCoroutinesApi

@ObsoleteCoroutinesApi
class NewsFeedFragment : BaseFragment() {
    override val layoutId = R.layout.fragment_news_feed

    private lateinit var newsFeedViewModel: NewsFeedViewModel

    private val adapter: NewsPaginationAdapter by unsafeLazy { createNewsFeedAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

        newsFeedViewModel = viewModel(viewModelFactory)
        newsFeedViewModel.nextPageState.observe(this, Observer {
            val state = it
            renderNextPage(state)
        })
        newsFeedViewModel.reloadState.observe(this, Observer {
            val state = it
            renderReload(state)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = getString(R.string.title_news_feed)
        initUi()
        newsFeedViewModel.onLoadMore()
    }

    private fun renderNextPage(state: NextPageState) {
        when (state) {
            is NextPageState.Loading -> {
                srNews.isEnabled = false
                adapter.hideError()
                adapter.showLoading()
            }
            is NextPageState.FailureLoading -> {
                srNews.isEnabled = true
                adapter.hideLoading()
                adapter.showError()

            }
            is NextPageState.SuccessLoading -> {
                srNews.isEnabled = true
                adapter.hideLoading()
                adapter.hideError()
                adapter.addNewPage(state.page)
            }
        }
    }

    private fun renderReload(state: ReloadState) {
        when (state) {
            is ReloadState.FailureLoading -> {
                srNews.isRefreshing = false
                showError(getString(R.string.error_reload_failed))
            }
            is ReloadState.SuccessLoading -> {
                srNews.isRefreshing = false
                adapter.clearAll()
                adapter.addNewPage(state.page)
            }
        }
    }

    private fun initUi() {
        val layoutManager = LinearLayoutManager(activity)
        rvNews.layoutManager = layoutManager
        rvNews.setHasFixedSize(true)
        rvNews.addOnScrollListener(
                PaginationScrollListener(layoutManager) { newsFeedViewModel.onLoadMore() }
        )
        rvNews.adapter = adapter

        srNews.setColorSchemeResources(R.color.colorPrimary)
        srNews.setOnRefreshListener { newsFeedViewModel.onReload() }
    }

    private fun createNewsFeedAdapter(): NewsPaginationAdapter =
            NewsPaginationAdapter(activity!!) { newsFeedViewModel.retryLoadMore() }
}