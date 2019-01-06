package com.aytlo.tony.kotlin_coroutines.presentation.core.adapter

import android.content.Context
import com.aytlo.tony.kotlin_coroutines.presentation.core.extension.postUi
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter

abstract class BasePaginatorAdapter(
        context: Context,
        retryAction: (() -> Unit)?,
        vararg delegates: AdapterDelegate<MutableList<BaseItemModel>>
) : ListDelegationAdapter<MutableList<BaseItemModel>>(), PaginationAdapter<MutableList<BaseItemModel>> {

    companion object {
        private const val NO_POSITION = -1
    }

    private var isLoadingAdded = false
    private var isErrorAdded = false

    init {
        delegatesManager.addDelegate(LoadingDelegateAdapter(context))
        retryAction?.let {
            delegatesManager.addDelegate(ErrorRetryDelegateAdapter(context, it))
        }
        delegates.forEach {
            delegatesManager.addDelegate(it)
        }
        initItems()
    }

    override fun showLoading() {
        if (isLoadingAdded) {
            return
        }
        insertAndNotify(LoadingDelegateAdapter.SINGLE_ITEM_LOADING)
        isLoadingAdded = true
    }

    override fun hideLoading() {
        if (!isLoadingAdded) {
            return
        }
        removeAndNotify(LoadingDelegateAdapter.SINGLE_ITEM_LOADING)
        isLoadingAdded = false
    }

    override fun showError() {
        if (isErrorAdded) {
            return
        }
        insertAndNotify(ErrorRetryDelegateAdapter.SINGLE_ITEM_ERROR)
        isErrorAdded = true
    }

    override fun hideError() {
        if (!isErrorAdded) {
            return
        }
        removeAndNotify(ErrorRetryDelegateAdapter.SINGLE_ITEM_ERROR)
        isErrorAdded = false
    }

    override fun addNewPage(page: MutableList<BaseItemModel>) {
        if (page.isEmpty()) {
            return
        }
        postUi {
            val oldItemCount = itemCount
            items.addAll(page)
            val newItemCount = itemCount - oldItemCount
            notifyItemRangeInserted(oldItemCount, newItemCount)
        }
    }

    override fun clearAll() {
        items.clear()
        notifyDataSetChanged()
    }

    private fun insertAndNotify(baseItem: BaseItemModel) {
        postUi {
            items.add(baseItem)
            notifyItemInserted(items.size - 1)
        }
    }

    private fun removeAndNotify(baseItem: BaseItemModel) {
        val positionItem = items.indexOf(baseItem)
        safeRemoveAndNotifyByPos(positionItem)
    }

    private fun safeRemoveAndNotifyByPos(position: Int) {
        if (position == NO_POSITION) {
            return
        }
        postUi {
            items.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    private fun initItems() {
        items = mutableListOf()
    }
}