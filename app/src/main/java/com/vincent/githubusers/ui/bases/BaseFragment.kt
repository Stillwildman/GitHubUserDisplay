package com.vincent.githubusers.ui.bases

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.vincent.githubusers.callbacks.FragmentCallback
import com.vincent.githubusers.callbacks.FragmentInterface

/**
 * Created by Vincent on 2020/9/24.
 */
abstract class BaseFragment<bindingView : ViewDataBinding> : Fragment(), FragmentInterface {

    protected val TAG = javaClass.simpleName

    protected abstract fun getLayoutId(): Int

    protected abstract fun init()

    private lateinit var mBinding: bindingView

    private lateinit var fragmentCallback : FragmentCallback

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            fragmentCallback = context as FragmentCallback
        }
        catch (e: ClassCastException) {
            e.printStackTrace()
            Log.e(TAG, context.javaClass.simpleName + " must implement " + FragmentCallback::class.java.simpleName)
        }

        Log.d(TAG, "onAttach!!!")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate!!!")
    }

    final override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)

        init()

        Log.d(TAG, "Init!!! (onCreateView)")

        return mBinding.root
    }

    private fun setActivityInterface() {
        fragmentCallback.setFragmentInterface(this)
    }

    protected fun openFragment(instance: Fragment, useReplace: Boolean, backName: String?) {
        fragmentCallback.onFragmentOpen(instance, useReplace, backName)
    }

    protected fun showLoading() {
        fragmentCallback.onFragmentLoading()
    }

    protected fun hideLoading() {
        fragmentCallback.onFragmentLoadingDone()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.setBackgroundColor(Color.WHITE)
        view.isClickable = true

        Log.d(TAG, "onViewCreated!!!")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart!!!")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume!!!")

        setActivityInterface()
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause!!!")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop!!!")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView!!!")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy!!!")
    }
}