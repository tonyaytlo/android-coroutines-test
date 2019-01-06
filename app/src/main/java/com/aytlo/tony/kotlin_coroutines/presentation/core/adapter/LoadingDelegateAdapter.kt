package com.aytlo.tony.kotlin_coroutines.presentation.core.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aytlo.tony.kotlin_coroutines.R
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate


class LoadingDelegateAdapter(context: Context) : AdapterDelegate<MutableList<BaseItemModel>>() {

    companion object {
        const val VIEW_TYPE = 1

        val SINGLE_ITEM_LOADING by lazy { createItemModel() }

        private fun createItemModel(): BaseItemModel {
            return object : BaseItemModel {
                override fun getViewType() = LoadingDelegateAdapter.VIEW_TYPE
            }
        }
    }

    private val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        LoadingViewHolder(
            inflater.inflate(
                R.layout.item_base_loading,
                parent,
                false
            )
        )

    override fun isForViewType(items: MutableList<BaseItemModel>, position: Int): Boolean =
        items[position].getViewType() == VIEW_TYPE

    override fun onBindViewHolder(
        items: MutableList<BaseItemModel>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) {
        //nothing
    }

    internal class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
