package com.aytlo.tony.kotlin_coroutines.data.source

import android.content.Context
import com.aytlo.tony.kotlin_coroutines.R
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiKeyProvider @Inject constructor(private val context: Context) {

    fun getApiKey(): String = context.getString(R.string.API_KEY)
}