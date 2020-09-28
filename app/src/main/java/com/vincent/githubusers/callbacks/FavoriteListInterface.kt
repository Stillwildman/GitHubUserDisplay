package com.vincent.githubusers.callbacks

import com.vincent.githubusers.model.items.ItemUser

/**
 * Created by Vincent on 2020/9/28.
 */
interface FavoriteListInterface {

    fun getCurrentFavoriteUserList(): MutableList<ItemUser>

}