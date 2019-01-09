package com.aytlo.tony.kotlin_coroutines.presentation.ui.news_feed

import com.aytlo.tony.kotlin_coroutines.presentation.core.adapter.BaseItemModel


sealed class NextPageState {
    object Loading : NextPageState()
    object FailureLoading : NextPageState()
    class SuccessLoading(val page: MutableList<BaseItemModel>) : NextPageState()
}

sealed class ReloadState {
    object FailureLoading : ReloadState()
    class SuccessLoading(val page: MutableList<BaseItemModel>) : ReloadState()
}