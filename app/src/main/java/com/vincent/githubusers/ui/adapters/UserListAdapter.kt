package com.vincent.githubusers.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.vincent.githubusers.R
import com.vincent.githubusers.callbacks.FavoriteListInterface
import com.vincent.githubusers.callbacks.OnFavoriteClickCallback
import com.vincent.githubusers.callbacks.OnUserClickCallback
import com.vincent.githubusers.databinding.InflateUserRowBinding
import com.vincent.githubusers.model.items.ItemUser
import com.vincent.githubusers.ui.bases.BaseBindingRecycler

/**
 * Created by Vincent on 2020/9/26.
 */
class UserListAdapter(
    private val clickCallback: OnUserClickCallback,
    private val favoriteCallback: OnFavoriteClickCallback,
    private val favoriteListInterface: FavoriteListInterface
) : BaseBindingRecycler<InflateUserRowBinding>()
{
    private val userList = mutableListOf<ItemUser>()

    private val options: RequestOptions by lazy {
        RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).placeholder(R.drawable.ic_place_holder_circle).fitCenter()
    }

    override fun getLayoutId(): Int = R.layout.inflate_user_row

    fun updateList(userList: List<ItemUser>?) {
        userList?.run {
            this@UserListAdapter.userList.let {
                if (it.isEmpty()) {
                    it.addAll(this)
                }
                else {
                    it.retainAll(this)
                }
            }
        }
        notifyDataSetChanged()
    }

    override fun onBindingViewHolder(holder: RecyclerView.ViewHolder, bindingView: InflateUserRowBinding, position: Int) {
        userList[position].let {
            bindingView.run {
                user = it
                clickCallback = this@UserListAdapter.clickCallback
                favoriteCallback = this@UserListAdapter.favoriteCallback
                buttonFavorite.isSelected = it.isUserAdded(favoriteListInterface)
            }

            Glide.with(holder.itemView)
                .load(it.avatar_url)
                .apply(options)
                .into(bindingView.imageAvatar)
        }
    }

    override fun onBindingViewHolder(holder: RecyclerView.ViewHolder, bindingView: InflateUserRowBinding, position: Int, payload: Any?) {
        userList[position].let {
            bindingView.buttonFavorite.isSelected = it.isUserAdded(favoriteListInterface)
        }
    }

    override fun getItemCount(): Int = userList.size
}