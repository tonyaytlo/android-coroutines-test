package com.aytlo.tony.kotlin_coroutines.presentation.ui.news_feed

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.aytlo.tony.kotlin_coroutines.data.model.NewsEntity
import com.aytlo.tony.kotlin_coroutines.domain.core.PaginationState
import com.aytlo.tony.kotlin_coroutines.domain.interactor.NewsFeedInteractor
import com.aytlo.tony.kotlin_coroutines.domain.model.News
import javax.inject.Inject

class NewsFeedViewModel
@Inject constructor(private val feedInteractor: NewsFeedInteractor) : ViewModel() {

    private val paginationStateObserver: Observer<PaginationState<News>>
    private val mutableNextPageState = MutableLiveData<NextPageState>()
    private val mutableReloadState = MutableLiveData<ReloadState>()

    private var isLoading = false
    private var isNextPageLoadingFailure = false

    val nextPageState = mutableNextPageState
    val reloadState = mutableReloadState

    init {
        paginationStateObserver = Observer {
            mapToNewsFeedState(it)
            isLoading = false
        }
        feedInteractor.liveData().observeForever(paginationStateObserver)
    }

    override fun onCleared() {
        super.onCleared()
        feedInteractor.unsubscribe()
        feedInteractor.liveData().removeObserver(paginationStateObserver)
    }

    fun onLoadMore() {
        if (isLoading || isNextPageLoadingFailure) {
            return
        }
        loadNextPage()
    }

    fun retryLoadMore() {
        if (isLoading) {
            return
        }
        loadNextPage()
    }

    fun onReload() {
        if (isLoading) {
            return
        }
        reload()
    }

    private fun loadNextPage() {
        mutableNextPageState.value = NextPageState.Loading
        feedInteractor.loadNextPage().apply { isLoading = true }
    }

    private fun reload() {
        feedInteractor.reload().apply { isLoading = true }
    }

    private fun mapToNewsFeedState(paginationState: PaginationState<News>) {
        if (paginationState.allLoaded) {
            mutableNextPageState.value = NextPageState.SuccessLoading(mutableListOf())
            return
        }
        if (paginationState.hasError) {
            if (paginationState.reloaded) {
                mutableReloadState.value = ReloadState.FailureLoading
            } else {
                mutableNextPageState.value = NextPageState.FailureLoading
                isNextPageLoadingFailure = true
            }
        } else if (paginationState.reloaded) {
            mutableReloadState.value = ReloadState.SuccessLoading(paginationState.dataList.toMutableList())
        } else {
            mutableNextPageState.value = NextPageState.SuccessLoading(paginationState.dataList.toMutableList())
            isNextPageLoadingFailure = false
        }
    }
}