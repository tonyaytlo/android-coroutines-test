package com.aytlo.tony.kotlin_coroutines.presentation.main

import com.aytlo.tony.kotlin_coroutines.presentation.core.SingleFragmentActivity
import com.aytlo.tony.kotlin_coroutines.presentation.core.BaseFragment

class MainActivity : SingleFragmentActivity() {

    override fun fragment(): BaseFragment {
        return MainFragment()
    }
}
