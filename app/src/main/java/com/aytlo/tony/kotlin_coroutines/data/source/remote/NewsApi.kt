package com.aytlo.tony.kotlin_coroutines.data.source.remote

import com.aytlo.tony.kotlin_coroutines.data.model.SearchResponse
import com.aytlo.tony.kotlin_coroutines.data.model.SearchResult
import retrofit2.Call
import retrofit2.http.GET

interface NewsApi {

    companion object {
        private const val SEARCH = "search"
    }

    @GET(SEARCH)
    fun searchNews(): Call<SearchResponse>
}