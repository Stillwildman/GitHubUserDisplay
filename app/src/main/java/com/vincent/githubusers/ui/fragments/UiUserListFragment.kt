package com.vincent.githubusers.ui.fragments

import com.vincent.githubusers.R
import com.vincent.githubusers.databinding.FragmentUserListBinding
import com.vincent.githubusers.ui.bases.BaseFragment

/**
 * Created by Vincent on 2020/9/24.
 */
class UiUserListFragment : BaseFragment<FragmentUserListBinding>() {

    override fun getLayoutId(): Int = R.layout.fragment_user_list

    override fun init() {

    }

    override fun onBackKeyPressed(): Boolean = true
}