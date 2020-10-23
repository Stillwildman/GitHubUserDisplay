package com.vincent.githubusers.network

import com.vincent.githubusers.AppController
import com.vincent.githubusers.R
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Vincent on 2020/9/25.
 */
object RetrofitAgent {

    private var retrofit: Retrofit? = null

    fun getRetrofit(baseUrl: String): Retrofit {
        if (retrofit == null) {
            retrofit = newRetrofit(baseUrl)
        }
        else if (retrofit!!.baseUrl().toString() != baseUrl) {
            retrofit = newRetrofit(baseUrl)
        }
        return retrofit!!
    }

    private fun newRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .client(getOkHttpClient())
            .build()
    }

    private fun getOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().run {
            addInterceptor { chain ->
                val request = chain.request().run {
                    newBuilder().also {
                        it.header("accept", "application/vnd.github.v3+json")
                        it.header("User-Agent", AppController.instance.getString(R.string.app_name))
                        it.header("Authorization", AppController.instance.getString(R.string.authorization_token_is, "9ae0659361a08ae373f58378e73521b9ccadacdb"))
                        it.method(method(), body())
                    }.build()
                }
                chain.proceed(request)
            }
            build()
        }
    }
}