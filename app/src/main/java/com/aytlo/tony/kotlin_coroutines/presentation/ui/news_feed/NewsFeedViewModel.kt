package com.aytlo.tony.kotlin_coroutines.presentation.ui.news_feed

import androidx.lifecycle.MutableLiveData
import com.aytlo.tony.kotlin_coroutines.domain.core.PaginationState
import com.aytlo.tony.kotlin_coroutines.domain.interactor.NewsFeedInteractor
import com.aytlo.tony.kotlin_coroutines.domain.model.News
import com.aytlo.tony.kotlin_coroutines.presentation.core.BaseViewModel
import com.aytlo.tony.kotlin_coroutines.presentation.core.launchComposite
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.consumeEach
import javax.inject.Inject

@ObsoleteCoroutinesApi
class NewsFeedViewModel
@Inject constructor(private val feedInteractor: NewsFeedInteractor) : BaseViewModel() {

    private val mutableNextPageState = MutableLiveData<NextPageState>()
    private val mutableReloadState = MutableLiveData<ReloadState>()

    private var isLoading = false
    private var isNextPageLoadingFailure = false

    val nextPageState = mutableNextPageState
    val reloadState = mutableReloadState

    init {
        launchComposite {
            feedInteractor.sourceData().consumeEach {
                mapToNewsFeedState(it)
                isLoading = false
            }
        }
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
        isLoading = true
        launchComposite(backgroundContext) {
            feedInteractor.loadNextPage()
        }
    }

    private fun reload() {
        isLoading = true
        launchComposite(backgroundContext) {
            feedInteractor.reload()
        }
    }

    private fun mapToNewsFeedState(paginationState: PaginationState<News>) {
        if (paginationState.allLoaded) {
            mutableNextPageState.value = NextPageState.SuccessLoading(mutableListOf())
            return
        }
        isNextPageLoadingFailure = false
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
        }
    }
}