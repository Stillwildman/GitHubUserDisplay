package com.vincent.githubusers.paging

import androidx.paging.DataSource
import com.vincent.githubusers.callbacks.PagingStatusCallback
import com.vincent.githubusers.model.items.ItemUser

/**
 * DataSourceFactory�A�b�o�̲��� [UserDataSource]
 */
class UserDataSourceFactory internal constructor(private val statusCallback: PagingStatusCallback?) : DataSource.Factory<Int, ItemUser>() {

    override fun create(): DataSource<Int, ItemUser> {
        return UserDataSource(statusCallback)
    }

}