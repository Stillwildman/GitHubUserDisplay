package com.vincent.githubusers.callbacks

interface OnDataGetCallback<Item> {

    fun onDataGet(item: Item?)

    fun onDataGetFailed(errorMessage: String?)

}