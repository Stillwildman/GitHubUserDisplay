package com.vincent.githubusers.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.vincent.githubusers.AppController
import com.vincent.githubusers.R
import com.vincent.githubusers.callbacks.OnLoadingCallback
import com.vincent.githubusers.callbacks.OnUserClickCallback
import com.vincent.githubusers.callbacks.UserProfileCallback
import com.vincent.githubusers.database.UserDatabase
import com.vincent.githubusers.databinding.FragmentUserDetailBinding
import com.vincent.githubusers.model.Const
import com.vincent.githubusers.model.items.ItemFollower
import com.vincent.githubusers.model.items.ItemUser
import com.vincent.githubusers.model.items.ItemUserDetail
import com.vincent.githubusers.presenter.FavoritePresenter
import com.vincent.githubusers.presenter.UserProfilePresenter
import com.vincent.githubusers.ui.adapters.FollowedUserListAdapter
import com.vincent.githubusers.ui.bases.BaseFragment

/**
 * Created by Vincent on 2020/9/28.
 */
class UiUserDetailFragment private constructor() : BaseFragment<FragmentUserDetailBinding>(), UserProfileCallback, OnLoadingCallback, OnUserClickCallback {

    private val userProfilePresenter by lazy { UserProfilePresenter(this, this) }
    private var userItem: ItemUser? = null
    private val favoritePresenter by lazy { FavoritePresenter() }

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
            userProfilePresenter.getUserDetail(it)
        }
    }

    override fun onUserDetailGet(userDetail: ItemUserDetail) {
        setUserDetail(userDetail)
        getBidirectionalFollowedUsers(userDetail)
    }

    private fun getBidirectionalFollowedUsers(userDetail: ItemUserDetail) {
        userProfilePresenter.getBidirectionalFollowedUsers(userDetail)
    }

    private fun setUserDetail(userDetail: ItemUserDetail) {
        mBinding.profile = userDetail
        showUserAvatar(userDetail.avatar_url)

        userItem = ItemUser(userDetail.avatar_url, userDetail.id, userDetail.login, userDetail.site_admin)
        observeUserFavoriteState(userDetail.login)

        setFavoriteClickListener(ItemUser(userDetail.avatar_url, userDetail.id, userDetail.login, userDetail.site_admin))
    }

    private fun showUserAvatar(avatarUrl: String) {
        val options = RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).placeholder(R.drawable.ic_place_holder_circle).fitCenter()

        Glide.with(this)
            .load(avatarUrl)
            .apply(options)
            .into(mBinding.imageAvatar)
    }

    private fun observeUserFavoriteState(login: String) {
        UserDatabase.getInstance().getUsersDao().isUserAddedByLogin(login).observe(this, {isAdded ->
            mBinding.buttonFavorite.isSelected = isAdded
        })
    }

    private fun setFavoriteClickListener(user: ItemUser) {
        mBinding.buttonFavorite.setOnClickListener {
            favoritePresenter.switchTheFavoriteState(user, mBinding.buttonFavorite)
        }
    }

    override fun onBidirectionalFollowedGet(followedList: ArrayList<ItemFollower>) {
        setFollowedNumberText(followedList.size)
        setFollowedUsersRecycler(followedList)
    }

    private fun setFollowedNumberText(number: Int) {
        mBinding.textFollowers.text = AppController.instance.getString(R.string.following_and_followed, number, mBinding.profile?.login)
    }

    private fun setFollowedUsersRecycler(followedList: ArrayList<ItemFollower>) {
        if (isResumed) {
            mBinding.recyclerFollower.run {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = FollowedUserListAdapter(followedList, this@UiUserDetailFragment)
            }
        }
    }

    override fun onLoginGet(login: String) {
        openFragment(newInstance(login), true, Const.BACK_USER_DETAIL)
    }

    override fun getLoadingView(): View? = getToolbarLoadingCircle()

    override fun onBackKeyPressed(): Boolean {
        userProfilePresenter.cancelComparing()
        return false
    }
}