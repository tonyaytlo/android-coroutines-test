package com.aytlo.tony.kotlin_coroutines.presentation.ui.news_feed

import com.aytlo.tony.kotlin_coroutines.domain.model.News


sealed class NextPageState {
    object Loading : NextPageState()
    object FailureLoading : NextPageState()
    class SuccessLoading(val news: MutableList<News>) : NextPageState()
}

sealed class ReloadState {
    object FailureLoading : ReloadState()
    class SuccessLoading(val news: MutableList<News>) : ReloadState()
}