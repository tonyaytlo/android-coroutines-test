package com.aytlo.tony.kotlin_coroutines.domain.interactor

import com.aytlo.tony.kotlin_coroutines.domain.core.Paginator
import com.aytlo.tony.kotlin_coroutines.domain.model.News
import javax.inject.Inject

class NewsFeedInteractor
@Inject constructor(private val newsPaginator: Paginator<News>) {

    suspend fun loadNextPage() {
        newsPaginator.loadNextPage()
    }

    suspend fun reload() {
        newsPaginator.reload()
    }

    fun sourceData() = newsPaginator.sourceData()
}