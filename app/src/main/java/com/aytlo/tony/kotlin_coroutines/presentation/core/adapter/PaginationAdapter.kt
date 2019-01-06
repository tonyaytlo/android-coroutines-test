package com.aytlo.tony.kotlin_coroutines.presentation.core.adapter

interface PaginationAdapter<T> {

    fun showLoading()

    fun hideLoading()

    fun showError()

    fun hideError()

    fun addNewPage(page: T)

    fun clearAll()
}