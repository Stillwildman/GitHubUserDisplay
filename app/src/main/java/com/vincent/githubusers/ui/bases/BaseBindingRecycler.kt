package com.vincent.githubusers.ui.bases

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

/**
 * BaseBindingRecycler
 *
 * 讓有使用DataBinding的RecyclerView.Adapter繼承此類，可以更簡易地使用DataBinding
 *
 * 此版中將Adapter升級為PagedListAdapter，須pass一個DiffUtil.ItemCallback進來
 *
 * @param Item Data type of PagedList
 * @param BindingView 綁定的ViewDataBinding
 */
abstract class BaseBindingRecycler<Item, BindingView : ViewDataBinding> protected constructor(diffCallback: DiffUtil.ItemCallback<Item>)
    : PagedListAdapter<Item, RecyclerView.ViewHolder>(diffCallback) {

    protected abstract fun getLayoutId(): Int

    // 取代原本的onBindViewHolder，用意為把bindingView帶出來給子類別使用
    protected abstract fun onBindingViewHolder(holder: RecyclerView.ViewHolder, bindingView: BindingView, position: Int)

    // 同上，同時也順便把payload從List中取出
    protected abstract fun onBindingViewHolder(holder: RecyclerView.ViewHolder, bindingView: BindingView, position: Int, payload: Any?)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding: BindingView = DataBindingUtil.inflate(LayoutInflater.from(parent.context), getLayoutId(), parent, false)
        return BindingViewHolder(binding)
    }

    inner class BindingViewHolder(val bindingView: BindingView) : RecyclerView.ViewHolder(bindingView.root)

    @Suppress("UNCHECKED_CAST", "RemoveRedundantQualifierName")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        onBindingViewHolder(holder, (holder as BaseBindingRecycler<Item, BindingView>.BindingViewHolder).bindingView, position)
    }

    @Suppress("UNCHECKED_CAST", "RemoveRedundantQualifierName")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: List<Any>) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        }
        else {
            onBindingViewHolder(holder, (holder as BaseBindingRecycler<Item, BindingView>.BindingViewHolder).bindingView, position, payloads[0])
        }
    }
}