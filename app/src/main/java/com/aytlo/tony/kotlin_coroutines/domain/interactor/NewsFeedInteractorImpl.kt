package com.aytlo.tony.kotlin_coroutines.domain.interactor

import androidx.lifecycle.LiveData
import com.aytlo.tony.kotlin_coroutines.domain.core.PaginationState
import com.aytlo.tony.kotlin_coroutines.domain.core.Paginator
import com.aytlo.tony.kotlin_coroutines.domain.model.News
import javax.inject.Inject

class NewsFeedInteractorImpl
@Inject constructor(private val newsPaginator: Paginator<News>) : NewsFeedInteractor {

    override fun loadNextPage() {
        newsPaginator.loadNextPage()
    }

    override fun reload() {
        newsPaginator.reload()
    }

    override fun liveData(): LiveData<PaginationState<News>> = newsPaginator.liveData()
}