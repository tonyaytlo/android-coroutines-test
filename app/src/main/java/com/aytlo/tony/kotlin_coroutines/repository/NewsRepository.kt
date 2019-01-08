package com.aytlo.tony.kotlin_coroutines.repository

import com.aytlo.tony.kotlin_coroutines.data.core.Result
import com.aytlo.tony.kotlin_coroutines.domain.model.News

interface NewsRepository {

    suspend fun search(page: Int, pageSize: Int): Result<MutableList<News>, Throwable>
}