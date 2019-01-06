package com.aytlo.tony.kotlin_coroutines.data.model

import com.aytlo.tony.kotlin_coroutines.presentation.core.adapter.BaseItemModel
import com.aytlo.tony.kotlin_coroutines.presentation.ui.news_feed.adapter.NewsDelegateAdapter

data class News(
        val id: String,
        val sectionId: String,
        val sectionName: String,
        val webPublicationDate: String,
        val webTitle: String,
        val webUrl: String,
        val apiUrl: String
) : BaseItemModel {
    override fun getViewType() = NewsDelegateAdapter.VIEW_TYPE
}