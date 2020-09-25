package com.vincent.githubusers.callbacks

import android.view.View

/**
 * Created by Vincent on 2020/9/25.
 */
interface OnLoadingCallback {

    fun getLoadingView(): View?

    fun showLoading() {
        getLoadingView()?.visibility = View.VISIBLE
    }

    fun hideLoading() {
        getLoadingView()?.visibility = View.GONE
    }

}