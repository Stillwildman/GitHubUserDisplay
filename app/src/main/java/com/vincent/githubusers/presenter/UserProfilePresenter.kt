package com.vincent.githubusers.presenter

import android.util.Log
import com.vincent.githubusers.callbacks.OnDataGetCallback
import com.vincent.githubusers.callbacks.OnLoadingCallback
import com.vincent.githubusers.callbacks.UserProfileCallback
import com.vincent.githubusers.model.Const
import com.vincent.githubusers.model.items.ItemFollower
import com.vincent.githubusers.model.items.ItemUserDetail
import com.vincent.githubusers.network.DataCallbacks
import com.vincent.githubusers.utilities.Utility
import kotlin.math.ceil

/**
 * Created by Vincent on 2020/9/28.
 */
class UserProfilePresenter(
    private val userProfileCallback: UserProfileCallback,
    private val loadingCallback: OnLoadingCallback? = null
) {
    private val followingList = arrayListOf<ItemFollower>()
    private val followersList = arrayListOf<ItemFollower>()

    private var isFollowingAllGet = false
    private var isFollowersAllGet = false

    private var isCancelled = false

    fun getUserDetail(login: String) {
        DataCallbacks.getUserDetail(login, object : OnDataGetCallback<ItemUserDetail> {
            override fun onDataGet(item: ItemUserDetail?) {
                item?.let {
                    userProfileCallback.onUserDetailGet(it)
                }
            }

            override fun onDataGetFailed(errorMessage: String?) {
                showErrorMessage(errorMessage)
            }
        }, loadingCallback)
    }

    fun getBidirectionalFollowedUsers(userDetail: ItemUserDetail) {
        if (userDetail.mightHaveBidirectionalFollowed()) {
            val followingPages = ceil(userDetail.following.toDouble() / Const.MAX_SIZE_OF_PER_PAGE).toInt()
            val followersPages = ceil(userDetail.followers.toDouble() / Const.MAX_SIZE_OF_PER_PAGE).toInt()

            getFollowingList(userDetail.login, followingPages, 1)
            getFollowersList(userDetail.login, followersPages, 1)
        }
    }

    private fun getFollowingList(login: String, pages: Int, pageStartFrom: Int) {
        DataCallbacks.getUserFollowing(login, pages, object : OnDataGetCallback<ArrayList<ItemFollower>> {
            override fun onDataGet(item: ArrayList<ItemFollower>?) {
                item?.let {
                    followingList.addAll(item)
                }
                if (pageStartFrom < pages) {
                    getFollowingList(login, pages, pageStartFrom + 1)
                }
                else {
                    isFollowingAllGet = true
                    compareTwoListAndRetainTheSame()
                }
            }

            override fun onDataGetFailed(errorMessage: String?) {
                showErrorMessage(errorMessage)
            }
        }, loadingCallback)
    }

    private fun getFollowersList(login: String, pages: Int, pageStartFrom: Int) {
        DataCallbacks.getUserFollowers(login, pages, object : OnDataGetCallback<ArrayList<ItemFollower>> {
            override fun onDataGet(item: ArrayList<ItemFollower>?) {
                item?.let {
                    followersList.addAll(item)
                }
                if (pageStartFrom < pages) {
                    getFollowersList(login, pages, pageStartFrom + 1)
                }
                else {
                    isFollowersAllGet = true
                    compareTwoListAndRetainTheSame()
                }
            }

            override fun onDataGetFailed(errorMessage: String?) {
                showErrorMessage(errorMessage)
            }
        }, loadingCallback)
    }

    private fun compareTwoListAndRetainTheSame() {
        if (isCancelled) return

        if (isFollowingAllGet && isFollowersAllGet) {
            val result = followingList.retainAll(followersList)
            Log.i("UserProfilePresenter", "Result: $result Size: ${followingList.size}")
            userProfileCallback.onBidirectionalFollowedGet(followingList)
        }
    }

    private fun showErrorMessage(errorMessage: String?) {
        errorMessage?.run {
            Utility.toastShort(this)
        }
    }

    fun cancelComparing() {
        isCancelled = true
    }
}