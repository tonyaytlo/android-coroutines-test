package com.aytlo.tony.kotlin_coroutines.presentation.main

import android.os.Bundle
import android.util.Log
import android.view.View
import com.aytlo.tony.kotlin_coroutines.R
import com.aytlo.tony.kotlin_coroutines.domain.core.UseCase
import com.aytlo.tony.kotlin_coroutines.domain.interactor.NewsInteractor
import com.aytlo.tony.kotlin_coroutines.presentation.core.BaseFragment
import javax.inject.Inject

class MainFragment : BaseFragment() {

    @Inject
    lateinit var newsInteractor: NewsInteractor

    override fun layoutId() = R.layout.fragment_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsInteractor(UseCase.None()) { it.take({ Log.d("MEIN", "SUCCESS") }, { Log.d("MEIN", "ERROR") }) }
    }
}