package com.aytlo.tony.kotlin_coroutines.presentation.ui.news_feed

import com.aytlo.tony.kotlin_coroutines.presentation.core.BaseFragment
import com.aytlo.tony.kotlin_coroutines.presentation.core.SingleFragmentActivity

class NewsFeedActivity : SingleFragmentActivity() {

    override fun fragment(): BaseFragment {
        return NewsFeedFragment()
    }
}
