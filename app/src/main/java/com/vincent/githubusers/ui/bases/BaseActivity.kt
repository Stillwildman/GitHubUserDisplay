package com.vincent.githubusers.ui.bases

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.vincent.githubusers.R
import com.vincent.githubusers.callbacks.FragmentCallback
import com.vincent.githubusers.callbacks.FragmentInterface
import com.vincent.githubusers.utilities.Utility

/**
 * Created by Vincent on 2020/9/24.
 */
abstract class BaseActivity : AppCompatActivity(), FragmentManager.OnBackStackChangedListener, FragmentCallback {

    protected val TAG = javaClass.simpleName

    protected abstract fun getLayoutId(): Int

    protected abstract fun getFragmentContainerId(): Int

    protected abstract fun getLoadingView(): View?

    protected abstract fun init()

    private val fm by lazy { supportFragmentManager }

    private var fragmentInterface: FragmentInterface? = null

    private var exitTime : Long = 0

    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(getLayoutId())

        initFragmentManager()

        init()

        Log.d(TAG, "onCreate!!!")
    }

    override fun setFragmentInterface(fragmentInterface: FragmentInterface) {
        this.fragmentInterface = fragmentInterface
    }

    private fun initFragmentManager() {
        fm.addOnBackStackChangedListener(this)
    }

    private fun isFragmentMoreThanOne(): Boolean = fm.backStackEntryCount > 1

    override fun onBackStackChanged() {
        Log.i(TAG, "onBackStackChanged!! Count: ${fm.backStackEntryCount}")
    }

    protected fun openFragment(instance: Fragment, useReplace: Boolean, backName: String?) {
        fm.findFragmentById(getFragmentContainerId())?.run {
            if (equals(instance)) return
        }

        if (useReplace) {
            fm.beginTransaction().replace(getFragmentContainerId(), instance).addToBackStack(backName).commit()
        }
        else {
            fm.beginTransaction().add(getFragmentContainerId(), instance).addToBackStack(backName).commit()
        }
    }

    override fun onFragmentOpen(instance: Fragment, useReplace: Boolean, backName: String?) {
        openFragment(instance, useReplace, backName)
    }

    override fun onFragmentLoading() {
        showLoadingCircle()
    }

    override fun onFragmentLoadingDone() {
        hideLoadingCircle()
    }

    private fun showLoadingCircle() {
        getLoadingView()?.visibility = View.VISIBLE
    }

    private fun hideLoadingCircle() {
        getLoadingView()?.visibility = View.GONE
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart!!!")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume!!!")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause!!!")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop!!!")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy!!!")
    }

    override fun onBackPressed() {
        Log.d(TAG, "onBackPressed!!!")

        fragmentInterface?.let {
            if (isFragmentMoreThanOne()) {
                if (!it.onBackKeyPressed()) super.onBackPressed()
            }
            else {
                finishAppWithinDoubleTap()
            }
        } ?: finishAppWithinDoubleTap()
    }

    private fun finishAppWithinDoubleTap() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            Utility.toastShort(R.string.confirm_to_exit)
            exitTime = System.currentTimeMillis()
        }
        else {
            finish()
        }
    }
}