package com.aytlo.tony.kotlin_coroutines.domain.model

import android.annotation.SuppressLint
import com.aytlo.tony.kotlin_coroutines.presentation.core.adapter.BaseItemModel
import com.aytlo.tony.kotlin_coroutines.presentation.ui.news_feed.adapter.NewsDelegateAdapter
import java.text.SimpleDateFormat
import java.util.*

class News(val id: String,
           val sectionId: String,
           val sectionName: String,
           val publicationDate: Date,
           val title: String,
           val webUrl: String,
           val apiUrl: String) : BaseItemModel {

    override fun getViewType() = NewsDelegateAdapter.VIEW_TYPE

    @SuppressLint("SimpleDateFormat")
    fun getStringDate() = SimpleDateFormat("dd.MM.yyyy").format(publicationDate)!!
}