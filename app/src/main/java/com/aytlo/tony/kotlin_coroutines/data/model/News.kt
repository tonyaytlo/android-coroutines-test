package com.aytlo.tony.kotlin_coroutines.data.model

data class News(
    val id: String,
    val sectionId: String,
    val sectionName: String,
    val webPublicationDate: String,
    val webUrl: String,
    val apiUrl: String
)