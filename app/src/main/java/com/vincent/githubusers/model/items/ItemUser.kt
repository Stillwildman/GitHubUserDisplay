package com.vincent.githubusers.model.items

import android.view.View
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.vincent.githubusers.callbacks.FavoriteListInterface
import com.vincent.githubusers.model.DatabaseParams

/**
 * Created by Vincent on 2020/9/25.
 */
@Entity(tableName = DatabaseParams.TABLE_USERS)
data class ItemUser(
    @ColumnInfo(name = DatabaseParams.COLUMN_AVATAR)
    var avatar_url: String,

    @PrimaryKey
    @ColumnInfo(name = DatabaseParams.COLUMN_ID)
    var id: Int,

    @ColumnInfo(name = DatabaseParams.COLUMN_LOGIN)
    var login: String,

    @Ignore
    var site_admin: Boolean,
) {
    constructor() : this("", 0, "", false)

    fun getSiteAdminVisibility(): Int = if (site_admin) View.VISIBLE else View.GONE

    fun isUserAdded(favoriteListInterface: FavoriteListInterface): Boolean {
        return favoriteListInterface.getCurrentFavoriteUserList().contains(this)
    }
}