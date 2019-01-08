package com.aytlo.tony.kotlin_coroutines.domain.paginator

import com.aytlo.tony.kotlin_coroutines.domain.core.PaginationState
import com.aytlo.tony.kotlin_coroutines.domain.core.Paginator
import com.aytlo.tony.kotlin_coroutines.domain.model.News
import com.aytlo.tony.kotlin_coroutines.repository.NewsRepository
import kotlinx.coroutines.channels.Channel
import javax.inject.Inject

class NewsFeedPaginator
@Inject constructor(private val newsRepository: NewsRepository) : Paginator<News> {

    companion object {
        private const val FIRST_PAGE = 1
    }

    private val mutableSourceData: Channel<PaginationState<News>> = Channel()
    private val sourceData = mutableSourceData
    private val pageSize = 20
    private var currentPage = 0

    override suspend fun loadNextPage() {
        loadPage(currentPage + 1)
    }

    override suspend fun reload() {
        loadPage(FIRST_PAGE, true)
    }

    private suspend fun loadPage(page: Int, reload: Boolean = false) {
        newsRepository.search(page, pageSize)
                .takeSuspend({
                    updateCurrentPage(page)
                    postNewData(PaginationState(it, reload, page, false))
                },
                        { postNewData(PaginationState(mutableListOf(), reload, page, true)) })
    }

    private fun updateCurrentPage(newPage: Int) {
        currentPage = newPage
    }

    private suspend fun postNewData(paginationState: PaginationState<News>) {
        mutableSourceData.send(paginationState)
    }

    override fun sourceData(): Channel<PaginationState<News>> = sourceData

}
