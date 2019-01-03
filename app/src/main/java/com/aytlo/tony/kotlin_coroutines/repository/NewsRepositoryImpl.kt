package com.aytlo.tony.kotlin_coroutines.repository

import com.aytlo.tony.kotlin_coroutines.data.core.Result
import com.aytlo.tony.kotlin_coroutines.data.core.Result.Failure
import com.aytlo.tony.kotlin_coroutines.data.core.Result.Success
import com.aytlo.tony.kotlin_coroutines.data.core.ServerError
import com.aytlo.tony.kotlin_coroutines.data.core.map
import com.aytlo.tony.kotlin_coroutines.data.model.SearchResult
import com.aytlo.tony.kotlin_coroutines.data.source.remote.NewsService
import javax.inject.Inject


class NewsRepositoryImpl
@Inject constructor(private val newsService: NewsService) : NewsRepository {

    override fun search(): Result<SearchResult, Throwable> {
        return try {
            val response = newsService.searchNews().execute()
            when (response.isSuccessful) {
                true -> Success(response.body()!!).map { it.response }
                false -> Failure(ServerError(response.errorBody()?.string() ?: "Response error"))
            }
        } catch (throwable: Throwable) {
            Failure(ServerError(throwable.message))
        }
    }
}