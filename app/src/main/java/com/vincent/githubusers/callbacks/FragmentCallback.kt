package com.vincent.githubusers.callbacks

import android.view.View
import androidx.fragment.app.Fragment

/**
 * Created by Vincent on 2020/9/24.
 */
interface FragmentCallback {

    fun setFragmentInterface(fragmentInterface: FragmentInterface)

    fun onFragmentOpen(instance: Fragment, useReplace: Boolean, backName: String?)

    fun onFragmentLoading()

    fun onFragmentLoadingDone()

    fun getToolbarLoadingCircle(): View?
}