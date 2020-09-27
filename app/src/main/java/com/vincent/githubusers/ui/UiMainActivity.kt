package com.vincent.githubusers.ui

import android.view.View
import androidx.appcompat.widget.Toolbar
import com.vincent.githubusers.R
import com.vincent.githubusers.model.Const
import com.vincent.githubusers.ui.bases.BaseActivity
import com.vincent.githubusers.ui.fragments.UiUserListFragment

class UiMainActivity : BaseActivity() {

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun getToolbar(): Toolbar? = findViewById(R.id.toolbar)

    override fun getFragmentContainerId(): Int = R.id.fragment_container

    override fun getLoadingView(): View? = findViewById(R.id.loadingCircle)

    override fun init() {
        openUserListFragment()
    }

    private fun openUserListFragment() {
        openFragment(UiUserListFragment(), false, Const.BACK_USER_LIST)
    }
}