package com.vincent.githubusers.network

import android.util.Log
import com.vincent.githubusers.callbacks.OnDataGetCallback
import com.vincent.githubusers.callbacks.OnLoadingCallback
import com.vincent.githubusers.model.ApiUrls
import com.vincent.githubusers.model.items.ItemFollower
import com.vincent.githubusers.model.items.ItemRateLimit
import com.vincent.githubusers.model.items.ItemUser
import com.vincent.githubusers.model.items.ItemUserDetail
import okhttp3.Headers
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.internal.EverythingIsNonNull

/**
 * Created by Vincent on 2020/9/25.
 */
object DataCallbacks {

    private const val TAG = "DataCallbacks"

    private fun getApiInterface(baseUrl: String = ApiUrls.BASE_GITHUB_API): ApiInterface {
        return RetrofitAgent.getRetrofit(baseUrl).create(ApiInterface::class.java)
    }

    private fun <Item> enqueue(call: Call<Item>, callback: OnDataGetCallback<Item>, loadingCallback: OnLoadingCallback? = null) {
        Log.i(TAG, "Call URL: " + call.request().url().toString())
        Log.d(TAG, "Request Header:\n${call.request().headers()}")

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

                getRateLimitFromHeaders(response.headers())

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
        val call = getApiInterface().getPaginatedUsers(since, perPage)
        enqueue(call, dataGetCallback, loadingCallback)
    }

    fun getUserDetail(login: String, dataGetCallback: OnDataGetCallback<ItemUserDetail>, loadingCallback: OnLoadingCallback?) {
        val call = getApiInterface().getUserDetail(login)
        enqueue(call, dataGetCallback, loadingCallback)
    }

    fun getUserFollowing(login: String, page: Int, dataGetCallback: OnDataGetCallback<ArrayList<ItemFollower>>, loadingCallback: OnLoadingCallback?) {
        val call = getApiInterface().getUserFollowing(login, page)
        enqueue(call, dataGetCallback, loadingCallback)
    }

    fun getUserFollowers(login: String, page: Int, dataGetCallback: OnDataGetCallback<ArrayList<ItemFollower>>, loadingCallback: OnLoadingCallback?) {
        val call = getApiInterface().getUserFollowers(login, page)
        enqueue(call, dataGetCallback, loadingCallback)
    }

    private fun getRateLimitFromHeaders(headers: Headers) {
        ItemRateLimit(headers["X-RateLimit-Limit"], headers["X-RateLimit-Remaining"], headers["X-RateLimit-Reset"], headers["X-RateLimit-Used"]).let {
            EventBus.getDefault().post(it)
        }
    }

    private fun notifyNetworkEventStart(loadingCallback: OnLoadingCallback?) {
        loadingCallback?.onLoadingStart()
    }

    private fun notifyNetworkEventEnds(loadingCallback: OnLoadingCallback?) {
        loadingCallback?.onLoadingEnds()
    }
}