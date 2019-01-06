package com.aytlo.tony.kotlin_coroutines.data.source.remote

import com.aytlo.tony.kotlin_coroutines.data.model.SearchResponse
import retrofit2.Call
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsService
@Inject constructor(private val newsApi: NewsApi) : NewsApi {

    override fun searchNews(page: Int, pageSize: Int): Call<SearchResponse> {
        return newsApi.searchNews(page, pageSize)
    }
}