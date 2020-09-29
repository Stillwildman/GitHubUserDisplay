package com.vincent.githubusers.ui

import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.vincent.githubusers.AppController
import com.vincent.githubusers.R
import com.vincent.githubusers.model.Const
import com.vincent.githubusers.model.items.ItemRateLimit
import com.vincent.githubusers.ui.bases.BaseActivity
import com.vincent.githubusers.ui.fragments.UiUserListFragment
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class UiMainActivity : BaseActivity() {

    private lateinit var textRateLimit: TextView

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun getToolbar(): Toolbar? = findViewById(R.id.toolbar)

    override fun getFragmentContainerId(): Int = R.id.fragment_container

    override fun getLoadingView(): View? = findViewById(R.id.loadingCircle)

    override fun init() {
        initRateLimitText()
        openUserListFragment()
    }

    private fun initRateLimitText() {
        textRateLimit = findViewById(R.id.text_rateLimit)
        updateRateLimitText(0, 0)
    }

    private fun updateRateLimitText(used: Int, limit: Int) {
        textRateLimit.text = AppController.instance.getString(R.string.rate_limit_formation, used, limit)
    }

    private fun openUserListFragment() {
        openFragment(UiUserListFragment(), false, Const.BACK_USER_LIST)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onRateLimitChanged(rateLimit: ItemRateLimit) {
        updateRateLimitText(rateLimit.getUsed(), rateLimit.getLimit())
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }
}