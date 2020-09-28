package com.vincent.githubusers.callbacks

import android.view.View

/**
 * Created by Vincent on 2020/9/25.
 */
interface OnLoadingCallback {

    fun getLoadingView(): View?

    fun onLoadingStart() {
        getLoadingView()?.visibility = View.VISIBLE
    }

    fun onLoadingEnds() {
        getLoadingView()?.visibility = View.GONE
    }

}