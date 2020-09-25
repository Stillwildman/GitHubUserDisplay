package com.vincent.githubusers.network

import com.vincent.githubusers.model.ApiUrls
import com.vincent.githubusers.model.items.ItemUser
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Vincent on 2020/9/25.
 */
interface ApiInterface {

    @GET(ApiUrls.API_GITHUB_USERS)
    fun getPaginatedUsers(
        @Query("since") since: Int,
        @Query("per_page") perPage: Int
    ): Call<ItemUser>

}