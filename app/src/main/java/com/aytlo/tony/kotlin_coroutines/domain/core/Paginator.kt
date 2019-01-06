package com.aytlo.tony.kotlin_coroutines.domain.core

import androidx.lifecycle.LiveData

interface Paginator<Model> {

    fun loadNextPage()

    fun reload()

    fun liveData(): LiveData<PaginationState<Model>>

}