package com.aytlo.tony.kotlin_coroutines.presentation.ui.news_feed

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.aytlo.tony.kotlin_coroutines.R
import com.aytlo.tony.kotlin_coroutines.presentation.core.BaseFragment
import com.aytlo.tony.kotlin_coroutines.presentation.core.adapter.BaseItemModel
import com.aytlo.tony.kotlin_coroutines.presentation.core.extension.viewModel
import com.aytlo.tony.kotlin_coroutines.presentation.ui.news_feed.adapter.NewsPaginationAdapter
import com.aytlo.tony.kotlin_coroutines.presentation.util.PaginationScrollListener
import kotlinx.android.synthetic.main.fragment_news_feed.*
import kotlinx.coroutines.ObsoleteCoroutinesApi

@ObsoleteCoroutinesApi
class NewsFeedFragment : BaseFragment() {

    private lateinit var newsFeedViewModel: NewsFeedViewModel
    private lateinit var newsPaginationAdapter: NewsPaginationAdapter

    override fun layoutId() = R.layout.fragment_news_feed

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
                newsPaginationAdapter.hideError()
                newsPaginationAdapter.showLoading()
            }
            is NextPageState.FailureLoading -> {
                srNews.isEnabled = true
                newsPaginationAdapter.hideLoading()
                newsPaginationAdapter.showError()

            }
            is NextPageState.SuccessLoading -> {
                srNews.isEnabled = true
                newsPaginationAdapter.hideLoading()
                newsPaginationAdapter.hideError()
                newsPaginationAdapter.addNewPage(state.news as MutableList<BaseItemModel>)
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
                newsPaginationAdapter.clearAll()
                newsPaginationAdapter.addNewPage(state.news as MutableList<BaseItemModel>)
            }
        }
    }

    private fun initUi() {
        rvNews.layoutManager = LinearLayoutManager(activity)
        rvNews.setHasFixedSize(true)
        rvNews.addOnScrollListener(
                PaginationScrollListener(rvNews.layoutManager as LinearLayoutManager) { newsFeedViewModel.onLoadMore() })
        srNews.setOnRefreshListener { newsFeedViewModel.onReload() }
        newsPaginationAdapter = NewsPaginationAdapter(activity!!) { newsFeedViewModel.retryLoadMore() }
        rvNews.adapter = newsPaginationAdapter
    }
}