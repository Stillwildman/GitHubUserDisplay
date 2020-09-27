package com.vincent.githubusers.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.vincent.githubusers.callbacks.PagingStatusCallback
import com.vincent.githubusers.model.items.ItemUser
import com.vincent.githubusers.paging.UserDataRepo

/**
 * Created by Vincent on 2020/9/26.
 */
class UserListViewModel : ViewModel() {

    private var statusCallback: PagingStatusCallback? = null

    val liveLoadingStatus = MutableLiveData<Boolean>()
    val liveErrorMessage = MutableLiveData<String>()

    fun setStatusCallback(statusCallback: PagingStatusCallback) {
        this.statusCallback = statusCallback
    }

    fun getUserList(): LiveData<PagedList<ItemUser>> {
        return UserDataRepo(statusCallback).userList
    }
}