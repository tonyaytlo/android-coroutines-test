package com.aytlo.tony.kotlin_coroutines.presentation.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.aytlo.tony.kotlin_coroutines.di.ApplicationComponent
import com.aytlo.tony.kotlin_coroutines.presentation.AndroidApp
import com.aytlo.tony.kotlin_coroutines.presentation.core.extension.unsafeLazy
import javax.inject.Inject


abstract class BaseFragment : Fragment() {

    val appComponent: ApplicationComponent by unsafeLazy {
        (activity?.application as AndroidApp).appComponent
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(layoutId, container, false)


    fun showError(message: String) {
        Toast.makeText(activity ?: return, message, Toast.LENGTH_SHORT).show()
    }

    abstract val layoutId: Int

    open fun onBackPressed() {}
}