package com.vincent.githubusers.callbacks

/**
 * Created by Vincent on 2020/9/28.
 */
interface OnProcessingCallback {

    fun onProcessing(): Boolean

    fun onDone(isSuccess: Boolean)

}