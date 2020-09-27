package com.vincent.githubusers.paging

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.vincent.githubusers.callbacks.PagingStatusCallback
import com.vincent.githubusers.model.items.ItemUser

/**
 * <p>�b�o�̹�PagedList��LiveData���]�w�A�D�n�O���� [PagedList.Config] �ó]�mPageSize & InitialPageKey�C</p>
 *
 * �إ�LiveData�ɷ|�����@�ӷs�� [UserDataSourceFactory]�A���۷|�bUserDataSourceFactory��create [UserDataSource]�C
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