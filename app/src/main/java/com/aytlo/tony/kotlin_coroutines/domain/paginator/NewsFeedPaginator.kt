package com.aytlo.tony.kotlin_coroutines.domain.paginator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aytlo.tony.kotlin_coroutines.domain.core.PaginationState
import com.aytlo.tony.kotlin_coroutines.domain.core.Paginator
import com.aytlo.tony.kotlin_coroutines.domain.model.News
import com.aytlo.tony.kotlin_coroutines.repository.NewsRepository
import javax.inject.Inject

class NewsFeedPaginator
@Inject constructor(private val newsRepository: NewsRepository) : Paginator<News> {

    companion object {
        private const val FIRST_PAGE = 1
    }

    private val liveData: MutableLiveData<PaginationState<News>> = MutableLiveData()
    private val pageSize = 20
    private var currentPage = 0

    override fun loadNextPage() {
        loadPage(currentPage + 1)
    }

    override fun reload() {
        loadPage(FIRST_PAGE, true)
    }

    override fun liveData(): LiveData<PaginationState<News>> {
        return liveData
    }

    private fun loadPage(page: Int, reload: Boolean = false) {
        newsRepository.search(page, pageSize)
                .take({
                    updateCurrentPage(page)
                    postNewState(PaginationState(it, reload, page, false))
                },
                        { postNewState(PaginationState(mutableListOf(), reload, page, true)) })
    }

    private fun updateCurrentPage(newPage: Int) {
        currentPage = newPage
    }

    private fun postNewState(paginationState: PaginationState<News>) {
        liveData.postValue(paginationState)
    }
}
