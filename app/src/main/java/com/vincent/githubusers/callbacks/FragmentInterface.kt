package com.vincent.githubusers.callbacks

/**
 * Created by Vincent on 2020/9/24.
 */
interface FragmentInterface {

    /**
     * @return
     * true: Consume the BackPressed event. Which mean the BackPressed will not be called in Activity.
     *
     * false: Will call the default BackPressed event in Activity.
     */
    fun onBackKeyPressed(): Boolean

    fun onMenuOptionClick(itemId: Int)
}