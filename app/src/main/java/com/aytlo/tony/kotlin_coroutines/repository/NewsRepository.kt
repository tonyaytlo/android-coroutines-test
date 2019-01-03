package com.aytlo.tony.kotlin_coroutines.repository

import com.aytlo.tony.kotlin_coroutines.data.core.Result
import com.aytlo.tony.kotlin_coroutines.data.model.SearchResult

interface NewsRepository {

    fun search(): Result<SearchResult, Throwable>
}