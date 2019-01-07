package com.aytlo.tony.kotlin_coroutines.data.model.mapper

import android.annotation.SuppressLint
import com.aytlo.tony.kotlin_coroutines.data.model.NewsEntity
import com.aytlo.tony.kotlin_coroutines.domain.model.News
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class NewsEntityMapper @Inject constructor() {

    fun mapToNewsList(listNewsEntity: MutableList<NewsEntity>): MutableList<News> {
        val newsList = mutableListOf<News>()
        listNewsEntity.forEach {
            newsList.add(mapToNews(it))
        }
        return newsList
    }

    fun mapToNews(newsEntity: NewsEntity): News {
        return News(newsEntity.id, newsEntity.sectionId, newsEntity.sectionName,
                mapDateStringToDate(newsEntity.webPublicationDate), newsEntity.webTitle,
                newsEntity.webUrl, newsEntity.apiUrl)
    }

    @SuppressLint("SimpleDateFormat")
    fun mapDateStringToDate(dateString: String, format: String = "yyyy-MM-dd"): Date {
        val dateFormat = SimpleDateFormat(format)
        return dateFormat.parse(dateString)
    }
}