package com.aytlo.tony.kotlin_coroutines.data.source.remote

import com.aytlo.tony.kotlin_coroutines.data.model.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    companion object {
        private const val SEARCH = "search"
        private const val QUERY_PAGE = "page"
        private const val QUERY_PAGE_SIZE = "page-size"
    }

    @GET(SEARCH)
    fun searchNews(@Query(QUERY_PAGE) page: Int, @Query(QUERY_PAGE_SIZE) pageSize: Int): Call<SearchResponse>
}