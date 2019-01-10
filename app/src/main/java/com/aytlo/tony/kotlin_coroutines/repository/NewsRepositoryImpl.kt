package com.aytlo.tony.kotlin_coroutines.repository

import com.aytlo.tony.kotlin_coroutines.data.core.NetworkConnection
import com.aytlo.tony.kotlin_coroutines.data.core.Result
import com.aytlo.tony.kotlin_coroutines.data.core.Result.Failure
import com.aytlo.tony.kotlin_coroutines.data.core.extension.await
import com.aytlo.tony.kotlin_coroutines.data.core.map
import com.aytlo.tony.kotlin_coroutines.data.model.mapper.NewsEntityMapper
import com.aytlo.tony.kotlin_coroutines.data.source.remote.NewsService
import com.aytlo.tony.kotlin_coroutines.domain.model.News
import com.aytlo.tony.kotlin_coroutines.presentation.util.NetworkHandler
import javax.inject.Inject


class NewsRepositoryImpl
@Inject constructor(
    private val newsService: NewsService,
    private val newsEntityMapper: NewsEntityMapper,
    private val networkHandler: NetworkHandler
) : NewsRepository {

    override suspend fun search(page: Int, pageSize: Int): Result<MutableList<News>, Throwable> {
        if (!networkHandler.isConnected) {
            return Failure(NetworkConnection())
        }
        return newsService.searchNews(page, pageSize).await()
            .map { newsEntityMapper.mapToNewsList(it.response.results) }
    }
}