package com.vincent.githubusers.ui.adapters

import android.graphics.Paint
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.vincent.githubusers.R
import com.vincent.githubusers.callbacks.OnUserClickCallback
import com.vincent.githubusers.databinding.InflateFollowedUserRowBinding
import com.vincent.githubusers.model.items.ItemFollower
import com.vincent.githubusers.ui.bases.BaseBindingRecycler

/**
 * Created by Vincent on 2020/9/28.
 */
class FollowedUserListAdapter(
    private val userList: List<ItemFollower>,
    private val userClickCallback: OnUserClickCallback
) : BaseBindingRecycler<InflateFollowedUserRowBinding>()
{
    private val options: RequestOptions by lazy {
        RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).placeholder(R.drawable.ic_place_holder_circle).fitCenter()
    }

    override fun getLayoutId(): Int = R.layout.inflate_followed_user_row

    override fun onBindingViewHolder(holder: RecyclerView.ViewHolder, bindingView: InflateFollowedUserRowBinding, position: Int) {
        bindingView.let {
            it.textFollowedUser.paintFlags = Paint.UNDERLINE_TEXT_FLAG

            it.userName = userList[position].login
            it.userClickCallback = userClickCallback

            Glide.with(holder.itemView)
                .load(userList[position].avatar_url)
                .apply(options)
                .into(it.imageFollowerAvatar)
        }
    }

    override fun onBindingViewHolder(holder: RecyclerView.ViewHolder, bindingView: InflateFollowedUserRowBinding, position: Int, payload: Any?) {

    }

    override fun getItemCount(): Int = userList.size
}