package com.aytlo.tony.kotlin_coroutines.presentation.ui.news_feed

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.aytlo.tony.kotlin_coroutines.data.model.News
import com.aytlo.tony.kotlin_coroutines.domain.core.PaginationState
import com.aytlo.tony.kotlin_coroutines.domain.interactor.NewsFeedInteractorImpl
import javax.inject.Inject

class NewsFeedViewModel
@Inject constructor(private val feedInteractorImpl: NewsFeedInteractorImpl) : ViewModel() {

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
        feedInteractorImpl.liveData().observeForever(paginationStateObserver)
    }

    override fun onCleared() {
        super.onCleared()
        feedInteractorImpl.liveData().removeObserver(paginationStateObserver)
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
        feedInteractorImpl.loadNextPage().apply { isLoading = true }
    }

    private fun reload() {
        feedInteractorImpl.reload().apply { isLoading = true }
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