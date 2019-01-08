package com.aytlo.tony.kotlin_coroutines.domain.interactor

import androidx.lifecycle.LiveData
import com.aytlo.tony.kotlin_coroutines.domain.core.PaginationState
import com.aytlo.tony.kotlin_coroutines.domain.core.Paginator
import com.aytlo.tony.kotlin_coroutines.domain.model.News
import javax.inject.Inject

class NewsFeedInteractor
@Inject constructor(private val newsPaginator: Paginator<News>) {

    fun loadNextPage() {
        newsPaginator.loadNextPage()
    }

    fun reload() {
        newsPaginator.reload()
    }

    fun liveData(): LiveData<PaginationState<News>> = newsPaginator.liveData()
}