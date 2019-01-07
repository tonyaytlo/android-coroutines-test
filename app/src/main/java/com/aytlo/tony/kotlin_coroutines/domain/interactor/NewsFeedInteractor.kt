package com.aytlo.tony.kotlin_coroutines.domain.interactor

import androidx.lifecycle.LiveData
import com.aytlo.tony.kotlin_coroutines.domain.core.BaseInteractor
import com.aytlo.tony.kotlin_coroutines.domain.core.PaginationState
import com.aytlo.tony.kotlin_coroutines.domain.model.News

interface NewsFeedInteractor : BaseInteractor {

    fun loadNextPage()

    fun reload()

    fun liveData(): LiveData<PaginationState<News>>
}