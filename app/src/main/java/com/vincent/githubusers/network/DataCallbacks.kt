package com.vincent.githubusers.network

import android.util.Log
import com.vincent.githubusers.callbacks.OnDataGetCallback
import com.vincent.githubusers.callbacks.OnLoadingCallback
import com.vincent.githubusers.model.ApiUrls
import com.vincent.githubusers.model.items.ItemUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.internal.EverythingIsNonNull

/**
 * Created by Vincent on 2020/9/25.
 */
object DataCallbacks {

    private const val TAG = "DataCallbacks"

    private fun getApiInterface(baseUrl: String): ApiInterface {
        return RetrofitAgent.getRetrofit(baseUrl).create(ApiInterface::class.java)
    }

    private fun <Item> enqueue(call: Call<Item>, callback: OnDataGetCallback<Item>, loadingCallback: OnLoadingCallback? = null) {
        Log.i(TAG, "Call URL: " + call.request().url().toString())

        notifyNetworkEventStart(loadingCallback)

        call.enqueue(object : Callback<Item> {
            @EverythingIsNonNull
            override fun onResponse(call: Call<Item>, response: Response<Item>) {
                Log.d(TAG, "Call onResponse!!!\nMessage: ${response.message()} IsSuccessful: ${response.isSuccessful}")

                if (response.isSuccessful) {
                    callback.onDataGet(response.body())
                }
                else {
                    callback.onDataGetFailed(response.message())
                }

                notifyNetworkEventEnds(loadingCallback)
            }

            @EverythingIsNonNull
            override fun onFailure(call: Call<Item>, t: Throwable) {
                Log.e(TAG, "Call onFailure!!!\n${t.message}")
                callback.onDataGetFailed(t.message)

                notifyNetworkEventEnds(loadingCallback)
            }
        })
    }

    fun getUsers(since: Int, perPage: Int, dataGetCallback: OnDataGetCallback<List<ItemUser>>, loadingCallback: OnLoadingCallback? = null) {
        val call = getApiInterface(ApiUrls.API_BASE_GITHUB).getPaginatedUsers(since, perPage)
        enqueue(call, dataGetCallback, loadingCallback)
    }

    private fun notifyNetworkEventStart(loadingCallback: OnLoadingCallback?) {
        loadingCallback?.showLoading()
    }

    private fun notifyNetworkEventEnds(loadingCallback: OnLoadingCallback?) {
        loadingCallback?.hideLoading()
    }
}