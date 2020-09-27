package com.vincent.githubusers.ui.fragments

import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.vincent.githubusers.R
import com.vincent.githubusers.callbacks.OnDataGetCallback
import com.vincent.githubusers.callbacks.OnLoadingCallback
import com.vincent.githubusers.databinding.FragmentUserDetailBinding
import com.vincent.githubusers.model.Const
import com.vincent.githubusers.model.items.ItemUserDetail
import com.vincent.githubusers.network.DataCallbacks
import com.vincent.githubusers.ui.bases.BaseFragment
import com.vincent.githubusers.utilities.Utility

/**
 * Created by Vincent on 2020/9/28.
 */
class UiUserDetailFragment private constructor() : BaseFragment<FragmentUserDetailBinding>(), OnLoadingCallback {

    companion object {
        fun newInstance(login: String): UiUserDetailFragment {
            return UiUserDetailFragment().apply {
                arguments = Bundle().also { it.putString(Const.BUNDLE_LOGIN, login) }
            }
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_user_detail

    override fun init() {
        getUserDetail()
    }

    private fun getUserDetail() {
        arguments?.getString(Const.BUNDLE_LOGIN)?.let {
            DataCallbacks.getUserDetail(it, object : OnDataGetCallback<ItemUserDetail> {
                override fun onDataGet(item: ItemUserDetail?) {
                    item?.run {
                        setUserDetail(this)
                    }
                }

                override fun onDataGetFailed(errorMessage: String?) {
                    errorMessage?.run {
                        Utility.toastShort(this)
                    }
                }
            }, this)
        }
    }

    private fun setUserDetail(userDetail: ItemUserDetail) {
        mBinding.profile = userDetail
        showUserAvatar(userDetail.avatar_url)
    }

    private fun showUserAvatar(avatarUrl: String) {
        val options = RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).placeholder(R.drawable.ic_place_holder_circle).fitCenter()

        Glide.with(this)
            .load(avatarUrl)
            .apply(options)
            .into(mBinding.imageAvatar)
    }

    override fun onLoadingStart() {
        showLoading()
    }

    override fun onLoadingEnds() {
        hideLoading()
    }

    override fun onBackKeyPressed(): Boolean {
        return false
    }
}