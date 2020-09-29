package com.vincent.githubusers.network

import com.vincent.githubusers.model.ApiUrls
import com.vincent.githubusers.model.Const
import com.vincent.githubusers.model.items.ItemFollower
import com.vincent.githubusers.model.items.ItemUser
import com.vincent.githubusers.model.items.ItemUserDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Vincent on 2020/9/25.
 */
interface ApiInterface {

    @GET(ApiUrls.API_GITHUB_USERS)
    fun getPaginatedUsers(
        @Query("since") since: Int,
        @Query("per_page") perPage: Int
    ): Call<List<ItemUser>>

    @GET(ApiUrls.API_GITHUB_USERS_DETAIL)
    fun getUserDetail(
        @Path("userName") userName: String
    ): Call<ItemUserDetail>

    @GET(ApiUrls.API_USER_FOLLOWING)
    fun getUserFollowing(
        @Path("userName") userName: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = Const.MAX_SIZE_OF_PER_PAGE
    ): Call<ArrayList<ItemFollower>>

    @GET(ApiUrls.API_USER_FOLLOWERS)
    fun getUserFollowers(
        @Path("userName") userName: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = Const.MAX_SIZE_OF_PER_PAGE
    ): Call<ArrayList<ItemFollower>>
}