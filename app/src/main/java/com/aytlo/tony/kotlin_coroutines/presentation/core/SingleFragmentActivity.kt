package com.aytlo.tony.kotlin_coroutines.presentation.core

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aytlo.tony.kotlin_coroutines.R
import com.aytlo.tony.kotlin_coroutines.presentation.core.extension.inTransaction
import kotlinx.android.synthetic.main.toolbar.*


abstract class SingleFragmentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_layout)
        setSupportActionBar(toolbar)
        addFragment(savedInstanceState)
    }

    override fun onBackPressed() {
        (supportFragmentManager.findFragmentById(R.id.fragmentContainer) as BaseFragment).onBackPressed()
        super.onBackPressed()
    }

    private fun addFragment(savedInstanceState: Bundle?) =
            savedInstanceState ?: supportFragmentManager.inTransaction {
                add(R.id.fragmentContainer, fragment())
            }

    abstract fun fragment(): BaseFragment

}