package com.vincent.githubusers.callbacks

interface PagingStatusCallback {

    fun onLoading(isLoading: Boolean)

    fun onFailed(errorMessage: String?)

}