package com.aytlo.tony.kotlin_coroutines.presentation.ui.news_feed.adapter

import android.content.Context
import com.aytlo.tony.kotlin_coroutines.presentation.core.adapter.BasePaginatorAdapter


class NewsPaginationAdapter(context: Context, retryAction: (() -> Unit)?) :
        BasePaginatorAdapter(context, retryAction, NewsDelegateAdapter(context))