package com.aytlo.tony.kotlin_coroutines.presentation.core.extension


import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.Factory
import androidx.lifecycle.ViewModelProviders
import com.aytlo.tony.kotlin_coroutines.presentation.core.BaseFragment
import com.aytlo.tony.kotlin_coroutines.presentation.core.SingleFragmentActivity
import kotlinx.android.synthetic.main.activity_base_layout.*


inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) =
    beginTransaction().func().commit()

inline fun <reified T : ViewModel> Fragment.viewModel(factory: Factory) =
    ViewModelProviders.of(this, factory)[T::class.java]


fun BaseFragment.close() = fragmentManager?.popBackStack()

val BaseFragment.viewContainer: View get() = (activity as SingleFragmentActivity).fragmentContainer

val BaseFragment.appContext: Context get() = activity?.applicationContext!!
