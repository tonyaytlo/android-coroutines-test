package com.aytlo.tony.kotlin_coroutines.domain.core

import kotlinx.coroutines.channels.Channel

interface Paginator<Model> {

    suspend fun loadNextPage()

    suspend fun reload()

    fun sourceData(): Channel<PaginationState<Model>>

}