package com.vincent.githubusers.model.items

import android.view.View

/**
 * Created by Vincent on 2020/9/27.
 */
data class ItemUserDetail(
    val avatar_url: String,
    private val bio: String?,
    val blog: String,
    val company: String,
    val created_at: String,
    val email: String,
    val events_url: String,
    val followers: Int,
    val followers_url: String,
    val following: Int,
    val following_url: String,
    val gists_url: String,
    val gravatar_id: String,
    val hireable: Boolean,
    val html_url: String,
    val id: Int,
    val location: String,
    val login: String,
    val name: String,
    val node_id: String,
    val organizations_url: String,
    val public_gists: Int,
    val public_repos: Int,
    val received_events_url: String,
    val repos_url: String,
    val site_admin: Boolean,
    val starred_url: String,
    val subscriptions_url: String,
    val twitter_username: String,
    val type: String,
    val updated_at: String,
    val url: String
) {
    fun getBio(): String {
      return bio ?: ""
    }

    fun getBioVisibility(): Int {
        return if (bio == null) View.GONE else View.VISIBLE
    }

    fun getSiteAdminVisibility(): Int {
        return if (site_admin) View.VISIBLE else View.GONE
    }

    fun hasBidirectionalFollowed(): Boolean {
        return following != 0 && followers != 0
    }

    fun getGroupVisibility(): Int {
        return if (hasBidirectionalFollowed()) View.VISIBLE else View.GONE
    }
}