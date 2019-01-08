package com.aytlo.tony.kotlin_coroutines.repository

import com.aytlo.tony.kotlin_coroutines.data.core.NetworkConnection
import com.aytlo.tony.kotlin_coroutines.data.core.Result
import com.aytlo.tony.kotlin_coroutines.data.core.Result.Failure
import com.aytlo.tony.kotlin_coroutines.data.core.Result.Success
import com.aytlo.tony.kotlin_coroutines.data.core.ServerError
import com.aytlo.tony.kotlin_coroutines.data.core.map
import com.aytlo.tony.kotlin_coroutines.data.model.mapper.NewsEntityMapper
import com.aytlo.tony.kotlin_coroutines.data.source.remote.NewsService
import com.aytlo.tony.kotlin_coroutines.domain.model.News
import com.aytlo.tony.kotlin_coroutines.presentation.util.NetworkHandler
import javax.inject.Inject


class NewsRepositoryImpl
@Inject constructor(private val newsService: NewsService,
                    private val newsEntityMapper: NewsEntityMapper,
                    private val networkHandler: NetworkHandler) : NewsRepository {

    override suspend fun search(page: Int, pageSize: Int): Result<MutableList<News>, Throwable> {
        if (!networkHandler.isConnected) {
            return Failure(NetworkConnection())
        }
        return try {
            val response = newsService.searchNews(page, pageSize).execute()
            when (response.isSuccessful) {
                true -> Success(response.body()!!).map { it.response }
                        .map { newsEntityMapper.mapToNewsList(it.results) }
                false -> Failure(ServerError())
            }
        } catch (throwable: Throwable) {
            Failure(ServerError())
        }
    }
}