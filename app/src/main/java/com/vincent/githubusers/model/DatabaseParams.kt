package com.vincent.githubusers.model

import android.provider.BaseColumns

/**
 * Created by Vincent on 2020/9/28.
 */
object DatabaseParams : BaseColumns {

    const val DB_VERSION = 1

    const val DB_USER = "UserDatabase"

    const val TABLE_USERS = "Users"

    const val COLUMN_ID = "Id"

    const val COLUMN_LOGIN = "Login"

    const val COLUMN_AVATAR = "AvatarUrl"

    const val COLUMN_IS_SITE_ADMIN = "IsSiteAdmin"
}