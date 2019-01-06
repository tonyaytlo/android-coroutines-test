package com.aytlo.tony.kotlin_coroutines.domain.paginator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aytlo.tony.kotlin_coroutines.data.model.News
import com.aytlo.tony.kotlin_coroutines.domain.core.PaginationState
import com.aytlo.tony.kotlin_coroutines.domain.core.Paginator
import com.aytlo.tony.kotlin_coroutines.repository.NewsRepository
import javax.inject.Inject

class NewsFeedPaginator
@Inject constructor(private val newsRepository: NewsRepository) : Paginator<News> {

    private val liveData: MutableLiveData<PaginationState<News>> = MutableLiveData()
    private val pageSize = 20
    private var currentPage = 0

    override fun loadNextPage() {
        loadPage(currentPage + 1)
    }

    override fun reload() {
        loadPage(1, true)
    }

    override fun liveData(): LiveData<PaginationState<News>> {
        return liveData
    }

    private fun loadPage(page: Int, reload: Boolean = false) {
        newsRepository.search(page, pageSize)
                .take({
                    this.currentPage = page
                    postNewState(PaginationState(it.results, reload, page, false))
                },
                        { postNewState(PaginationState(mutableListOf(), reload, page, true)) })
    }

    private fun postNewState(paginationState: PaginationState<News>) {
        liveData.postValue(paginationState)
    }
}
