package com.vincent.githubusers.paging

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.vincent.githubusers.callbacks.OnDataGetCallback
import com.vincent.githubusers.callbacks.PagingStatusCallback
import com.vincent.githubusers.model.items.ItemUser
import com.vincent.githubusers.network.DataCallbacks

/**
 * UserDataSource
 *
 * 在這裡進行網路活動，並且把資料取得狀態Callback回去。
 *
 * 採用PageKeyedDataSource，當到達每頁筆數的底部時，就給予下一筆的since參數自動取得新的資料。
 */
class UserDataSource(private val statusCallback: PagingStatusCallback?) : PageKeyedDataSource<Int, ItemUser>() {

    private val tag = "UserDataSource"

    /** 第一次載入時，每一頁的筆數由 [UserDataRepo] 設定，since從0開始. */
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, ItemUser>) {
        Log.i(tag, "loadInitial!!! RequestLoadSize: ${params.requestedLoadSize}")

        statusCallback?.onLoading(true)

        DataCallbacks.getUsers(0, params.requestedLoadSize, object : OnDataGetCallback<List<ItemUser>> {
            override fun onDataGet(item: List<ItemUser>?) {
                statusCallback?.onLoading(false)

                item?.let {
                    callback.onResult(it, null, getNextSinceParam(it))
                }
            }

            override fun onDataGetFailed(errorMessage: String?) {
                onFailed(errorMessage)
            }
        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ItemUser>) {
        Log.i(tag, "loadBefore!!!")

        statusCallback?.onLoading(true)

        DataCallbacks.getUsers(params.key, params.requestedLoadSize, object : OnDataGetCallback<List<ItemUser>> {
            override fun onDataGet(item: List<ItemUser>?) {
                statusCallback?.onLoading(false)

                item?.let {
                    callback.onResult(it, null)
                }
            }

            override fun onDataGetFailed(errorMessage: String?) {
                onFailed(errorMessage)
            }
        })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ItemUser>) {
        Log.i(tag, "loadAfter!!! Key: ${params.key} RequestLoadSize: ${params.requestedLoadSize}")

        statusCallback?.onLoading(true)

        DataCallbacks.getUsers(params.key, params.requestedLoadSize, object : OnDataGetCallback<List<ItemUser>> {
            override fun onDataGet(item: List<ItemUser>?) {
                statusCallback?.onLoading(false)

                item?.let {
                    callback.onResult(it, getNextSinceParam(it))
                }
            }

            override fun onDataGetFailed(errorMessage: String?) {
                onFailed(errorMessage)
            }
        })
    }

    private fun getNextSinceParam(userList: List<ItemUser>): Int? {
        return if (userList.isEmpty()) null else userList.last().id
    }

    private fun onFailed(errorMessage: String?) {
        statusCallback?.onLoading(false)
        statusCallback?.onFailed(errorMessage)
    }
}