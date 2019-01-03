package com.aytlo.tony.kotlin_coroutines.presentation.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.aytlo.tony.kotlin_coroutines.di.ApplicationComponent
import com.aytlo.tony.kotlin_coroutines.presentation.AndroidApp
import com.aytlo.tony.kotlin_coroutines.presentation.core.extension.unsafeLazy
import javax.inject.Inject


abstract class BaseFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    abstract fun layoutId(): Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(layoutId(), container, false)

    val appComponent: ApplicationComponent by unsafeLazy {
        (activity?.application as AndroidApp).appComponent
    }

    open fun onBackPressed() {}
}