package com.aytlo.tony.kotlin_coroutines.repository

import com.aytlo.tony.kotlin_coroutines.data.core.NetworkConnection
import com.aytlo.tony.kotlin_coroutines.data.core.Result
import com.aytlo.tony.kotlin_coroutines.data.core.Result.Failure
import com.aytlo.tony.kotlin_coroutines.data.core.Result.Success
import com.aytlo.tony.kotlin_coroutines.data.core.ServerError
import com.aytlo.tony.kotlin_coroutines.data.core.map
import com.aytlo.tony.kotlin_coroutines.data.model.SearchResult
import com.aytlo.tony.kotlin_coroutines.data.source.remote.NewsService
import com.aytlo.tony.kotlin_coroutines.presentation.util.NetworkHandler
import javax.inject.Inject


class NewsRepositoryImpl
@Inject constructor(private val newsService: NewsService, private val networkHandler: NetworkHandler) : NewsRepository {

    override fun search(page: Int, pageSize: Int): Result<SearchResult, Throwable> {
        if (!networkHandler.isConnected) {
            return Failure(NetworkConnection)
        }
        return try {
            val response = newsService.searchNews(page, pageSize).execute()
            when (response.isSuccessful) {
                true -> Success(response.body()!!).map { it.response }
                false -> Failure(ServerError(response.errorBody()?.string() ?: "Response failure"))
            }
        } catch (throwable: Throwable) {
            Failure(ServerError(throwable.message))
        }
    }
}