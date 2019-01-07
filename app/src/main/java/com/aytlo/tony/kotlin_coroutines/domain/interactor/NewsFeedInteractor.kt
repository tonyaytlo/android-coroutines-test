package com.aytlo.tony.kotlin_coroutines.domain.interactor

import androidx.lifecycle.LiveData
import com.aytlo.tony.kotlin_coroutines.data.model.News
import com.aytlo.tony.kotlin_coroutines.domain.core.BaseInteractor
import com.aytlo.tony.kotlin_coroutines.domain.core.PaginationState

interface NewsFeedInteractor : BaseInteractor {

    fun loadNextPage()

    fun reload()

    fun liveData(): LiveData<PaginationState<News>>
}