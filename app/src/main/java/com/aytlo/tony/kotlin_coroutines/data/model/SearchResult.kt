package com.aytlo.tony.kotlin_coroutines.data.model

data class SearchResult(
        val status: String,
        val userTier: String,
        val total: Int,
        val startIndex: Int,
        val pageSize: Int,
        val currentPage: Int,
        val pages: Int,
        val orderBy: String,
        val results: MutableList<News>
)
