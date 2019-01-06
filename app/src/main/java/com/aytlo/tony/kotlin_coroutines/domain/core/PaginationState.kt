package com.aytlo.tony.kotlin_coroutines.domain.core

data class PaginationState<Model> constructor(
    val dataList: List<Model>,
    val reloaded: Boolean,
    val offset: Int,
    val hasError: Boolean,
    val allLoaded: Boolean = false
)