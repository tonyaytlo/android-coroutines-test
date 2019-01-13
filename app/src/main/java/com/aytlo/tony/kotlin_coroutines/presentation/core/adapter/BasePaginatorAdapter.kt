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
        const val NO_POSITION = -1
    }

    private val itemLoading = LoadingDelegateAdapter.createItemModel()
    private val itemErrorRetry = ErrorRetryDelegateAdapter.createItemModel()

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
        initEmptyItemsList()
    }

    override fun showLoading() {
        if (isLoadingAdded) {
            return
        }
        insertAndNotify(itemLoading)
        isLoadingAdded = true
    }

    override fun hideLoading() {
        if (!isLoadingAdded) {
            return
        }
        removeAndNotify(itemLoading)
        isLoadingAdded = false
    }

    override fun showError() {
        if (isErrorAdded) {
            return
        }
        insertAndNotify(itemErrorRetry)
        isErrorAdded = true
    }

    override fun hideError() {
        if (!isErrorAdded) {
            return
        }
        removeAndNotify(itemErrorRetry)
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
            notifyItemRangeInserted(oldItemCount + 1, newItemCount)
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
        postUi {
            val positionItem = items.indexOf(baseItem)
            safeRemoveAndNotifyByPos(positionItem)
        }
    }

    private fun safeRemoveAndNotifyByPos(position: Int) {
        if (position == NO_POSITION) {
            return
        }
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    private fun initEmptyItemsList() {
        items = mutableListOf()
    }
}