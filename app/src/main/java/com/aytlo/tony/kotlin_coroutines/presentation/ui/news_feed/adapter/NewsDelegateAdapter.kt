package com.aytlo.tony.kotlin_coroutines.presentation.ui.news_feed.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aytlo.tony.kotlin_coroutines.R
import com.aytlo.tony.kotlin_coroutines.data.model.News
import com.aytlo.tony.kotlin_coroutines.presentation.core.adapter.BaseItemModel
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import kotlinx.android.synthetic.main.item_news.view.*


class NewsDelegateAdapter(context: Context) : AdapterDelegate<MutableList<BaseItemModel>>() {

    companion object {
        const val VIEW_TYPE = 0
    }

    private val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
            NewsViewHolder(inflater.inflate(R.layout.item_news, parent, false))

    override fun isForViewType(items: MutableList<BaseItemModel>, position: Int): Boolean =
            items[position].getViewType() == VIEW_TYPE

    override fun onBindViewHolder(items: MutableList<BaseItemModel>, position: Int,
                                  holder: RecyclerView.ViewHolder, payloads: MutableList<Any>) {
        (holder as NewsViewHolder).bind(items[position] as News)
    }

    internal class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(news: News) {
            itemView.tvTitle.text = news.webTitle
            itemView.tvPubDate.text = news.webPublicationDate
            itemView.tvSection.text = news.sectionName
        }
    }
}
