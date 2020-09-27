package com.vincent.githubusers.model.items

import android.view.View

/**
 * Created by Vincent on 2020/9/25.
 */
data class ItemUser(
    val avatar_url: String,
    val id: Int,
    val login: String,
    val site_admin: Boolean,
) {
    fun getSiteAdminVisibility(): Int = if (site_admin) View.VISIBLE else View.GONE
}