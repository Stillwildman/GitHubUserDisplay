package com.vincent.githubusers.ui.adapters

import androidx.paging.PagedList
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.vincent.githubusers.R
import com.vincent.githubusers.callbacks.OnUserClickCallback
import com.vincent.githubusers.databinding.InflateUserRowBinding
import com.vincent.githubusers.model.items.ItemUser
import com.vincent.githubusers.ui.bases.BaseBindingRecycler

/**
 * Created by Vincent on 2020/9/26.
 */
class UserListAdapter(private val clickCallback: OnUserClickCallback) : BaseBindingRecycler<ItemUser, InflateUserRowBinding>(
    object : DiffUtil.ItemCallback<ItemUser>() {
        override fun areItemsTheSame(oldItem: ItemUser, newItem: ItemUser): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ItemUser, newItem: ItemUser): Boolean {
            return oldItem.id == newItem.id && oldItem.login == newItem.login
        }
    }) {

    private val options: RequestOptions by lazy {
        RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).placeholder(R.drawable.ic_place_holder_circle).fitCenter()
    }

    override fun getLayoutId(): Int = R.layout.inflate_user_row

    fun updateList(pagedUserList: PagedList<ItemUser>?) {
        pagedUserList?.run {
            submitList(this)
        }
    }

    override fun onBindingViewHolder(holder: RecyclerView.ViewHolder, bindingView: InflateUserRowBinding, position: Int) {
        getItem(position)?.let {
            bindingView.user = it
            bindingView.clickCallback = clickCallback

            Glide.with(holder.itemView)
                .load(it.avatar_url)
                .apply(options)
                .into(bindingView.imageAvatar)
        }
    }

    override fun onBindingViewHolder(holder: RecyclerView.ViewHolder, bindingView: InflateUserRowBinding, position: Int, payload: Any?) {

    }
}