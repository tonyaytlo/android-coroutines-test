package com.aytlo.tony.kotlin_coroutines.presentation.util

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class PaginationScrollListener(
        private val layoutManager: LinearLayoutManager,
        private val onScrolledEnd: () -> Unit
) :
        RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 1) {
            onScrolledEnd()
        }
    }
}