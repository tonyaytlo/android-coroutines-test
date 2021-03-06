package com.aytlo.tony.kotlin_coroutines.presentation.core.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aytlo.tony.kotlin_coroutines.R
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import kotlinx.android.synthetic.main.item_base_error_retry.view.*

open class ErrorRetryDelegateAdapter(context: Context, protected val retryAction: () -> Unit) :
        AdapterDelegate<MutableList<BaseItemModel>>() {

    companion object {
        const val VIEW_TYPE = 2

        fun createItemModel(): BaseItemModel {
            return object : BaseItemModel {
                override fun getViewType() = ErrorRetryDelegateAdapter.VIEW_TYPE
            }
        }
    }

    protected val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
            ErrorRetryViewHolder(inflater.inflate(R.layout.item_base_error_retry,
                    parent, false)
                    .apply { btnRetry.setOnClickListener { retryAction() } }
            )

    final override fun isForViewType(items: MutableList<BaseItemModel>, position: Int): Boolean =
            items[position].getViewType() == VIEW_TYPE

    override fun onBindViewHolder(items: MutableList<BaseItemModel>, position: Int,
                                  holder: RecyclerView.ViewHolder, payloads: MutableList<Any>) {
        //nothing
    }

    internal class ErrorRetryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
