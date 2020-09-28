package com.vincent.githubusers.callbacks

import com.vincent.githubusers.model.items.ItemFollower
import com.vincent.githubusers.model.items.ItemUserDetail

/**
 * Created by Vincent on 2020/9/28.
 */
interface UserProfileCallback {

    fun onUserDetailGet(userDetail: ItemUserDetail)

    fun onBidirectionalFollowedGet(followedList: ArrayList<ItemFollower>)
}