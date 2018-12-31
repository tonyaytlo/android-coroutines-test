package com.aytlo.tony.kotlin_coroutines.presentation.core

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.aytlo.tony.kotlin_coroutines.di.ApplicationComponent
import com.aytlo.tony.kotlin_coroutines.presentation.AndroidApp
import javax.inject.Inject


abstract class BaseFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    abstract fun layoutId(): Int

    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        (activity?.application as AndroidApp).appComponent
    }

    open fun onBackPressed() {}
}