package com.vincent.githubusers.paging

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.vincent.githubusers.callbacks.PagingStatusCallback
import com.vincent.githubusers.model.items.ItemUser

/**
 * <p>在這裡對PagedList的LiveData做設定，主要是產生 [PagedList.Config] 並設置PageSize & InitialPageKey。</p>
 *
 * 建立LiveData時會給予一個新的 [UserDataSourceFactory]，接著會在UserDataSourceFactory中create [UserDataSource]。
 */
class UserDataRepo(private val statusCallback: PagingStatusCallback?) : BasePagingConfig() {

    val userList: LiveData<PagedList<ItemUser>>
        get() = LivePagedListBuilder(UserDataSourceFactory(statusCallback), config)
                .setInitialLoadKey(getInitialPageKey())
                .build()

    override fun getPerPageSize(): Int {
        return 20
    }

    override fun getInitialPageKey(): Int {
        return 0
    }

}