package com.vincent.githubusers.callbacks

import android.view.View
import com.vincent.githubusers.model.items.ItemUser

/**
 * Created by Vincent on 2020/9/28.
 */
interface OnFavoriteClickCallback {

    fun onFavoriteClick(userItem: ItemUser, favoriteView: View)

}